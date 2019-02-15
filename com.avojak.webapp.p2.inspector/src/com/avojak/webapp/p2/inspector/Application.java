package com.avojak.webapp.p2.inspector;

import java.net.URI;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.core.IProvisioningAgentProvider;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.osgi.framework.ServiceReference;

import com.avojak.webapp.p2.inspector.server.handler.InstallableUnitHandler;
import com.avojak.webapp.p2.inspector.server.handler.RepositoryDescriptionHandler;
import com.avojak.webapp.p2.inspector.server.handler.RepositoryNameHandler;
import com.avojak.webapp.p2.inspector.servlet.RootHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Application implements IApplication {

	@Override
	public Object start(final IApplicationContext applicationContext) throws Exception {
		final IProvisioningAgent agent = setupAgent(null);
		final IMetadataRepositoryManager metadataManager = (IMetadataRepositoryManager) agent
				.getService(IMetadataRepositoryManager.SERVICE_NAME);
//		final IArtifactRepositoryManager artifactManager = (IArtifactRepositoryManager) agent
//				.getService(IArtifactRepositoryManager.SERVICE_NAME);

//		final QueuedThreadPool threadPool = new QueuedThreadPool();
//		threadPool.setMaxThreads(500);

		final Server server = new Server(Integer.valueOf(System.getenv("PORT")));

//		final HttpConfiguration httpConfig = new HttpConfiguration();
//		httpConfig.setSecurePort(8443);
//		httpConfig.setSecureScheme("https");

//		final ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(httpConfig));
//		http.setPort(Integer.valueOf(System.getenv("PORT")));
//		http.setIdleTimeout(30000L);
//		http.setHost("0.0.0.0");
//		server.addConnector(http);

		final Gson gson = new GsonBuilder().setPrettyPrinting().create();

		final ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(createHandlers(metadataManager, null, gson));
		server.setHandler(contexts);

		server.start();
		server.join();

		return IApplication.EXIT_OK;
	}

	private IProvisioningAgent setupAgent(final URI location) throws ProvisionException {
		IProvisioningAgent result = null;
		ServiceReference<?> providerRef = Activator.getContext()
				.getServiceReference(IProvisioningAgentProvider.SERVICE_NAME);
		if (providerRef == null) {
			throw new RuntimeException("No provisioning agent provider is available"); //$NON-NLS-1$
		}
		IProvisioningAgentProvider provider = (IProvisioningAgentProvider) Activator.getContext()
				.getService(providerRef);
		if (provider == null) {
			throw new RuntimeException("No provisioning agent provider is available"); //$NON-NLS-1$
		}
		// obtain agent for currently running system
		result = provider.createAgent(location);
		Activator.getContext().ungetService(providerRef);
		return result;
	}

	private Handler[] createHandlers(final IMetadataRepositoryManager metadataManager,
			final IArtifactRepositoryManager artifactManager, final Gson gson) {
		final ContextHandler rootContext = new ContextHandler("/");
		rootContext.setHandler(new RootHandler(metadataManager, artifactManager, gson));

		final ContextHandler nameContext = new ContextHandler("/repository/name");
		nameContext.setHandler(new RepositoryNameHandler(metadataManager, artifactManager, gson));

		final ContextHandler descriptionContext = new ContextHandler("/repository/description");
		descriptionContext.setHandler(new RepositoryDescriptionHandler(metadataManager, artifactManager, gson));

		final ContextHandler installableUnitContext = new ContextHandler("/repository/iu");
		installableUnitContext.setHandler(new InstallableUnitHandler(metadataManager, artifactManager, gson));

		return new Handler[] { rootContext, nameContext, descriptionContext, installableUnitContext };
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

}
