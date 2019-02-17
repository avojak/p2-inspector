package com.avojak.webapp.p2.inspector.server.factory;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.core.runtime.ILog;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import com.avojak.webapp.p2.inspector.osgi.ProvisioningAgentProvider;
import com.avojak.webapp.p2.inspector.server.handler.InstallableUnitHandler;
import com.avojak.webapp.p2.inspector.server.handler.RepositoryDescriptionHandler;
import com.avojak.webapp.p2.inspector.server.handler.RepositoryNameHandler;
import com.avojak.webapp.p2.inspector.server.handler.RootHandler;
import com.google.gson.Gson;

/**
 * Factory class to create and configure the connection handler.
 */
public class HandlerFactory {

	private final ProvisioningAgentProvider agentProvider;
	private final Gson gson;
	private final ILog log;
	
	/**
	 * Constructor.
	 * 
	 * @param agentProvider The {@link ProvisioningAgentProvider}. Cannot be null.
	 * @param gson          The {@link Gson}. Cannot be null.
	 */
	public HandlerFactory(final ProvisioningAgentProvider agentProvider, final Gson gson, final ILog log) {
		this.agentProvider = checkNotNull(agentProvider, "agentProvider cannot be null");
		this.gson = checkNotNull(gson, "gson cannot be null");
		this.log = checkNotNull(log, "log cannot be null");
	}

	/**
	 * Creates the handler.
	 * 
	 * @return The non-null {@link Handler}.
	 */
	public Handler create() {
		final IMetadataRepositoryManager metadataManager;
		try {
			metadataManager = (IMetadataRepositoryManager) agentProvider.getAgent(null)
					.getService(IMetadataRepositoryManager.SERVICE_NAME);
		} catch (final ProvisionException e) {
			throw new RuntimeException(e);
		}
		final IArtifactRepositoryManager artifactManager = null;

		final ContextHandler rootContext = new ContextHandler("/");
		rootContext.setHandler(new RootHandler(log));

		final ContextHandler nameContext = new ContextHandler("/repository/name");
		nameContext.setHandler(new RepositoryNameHandler(metadataManager, artifactManager, gson, log));

		final ContextHandler descriptionContext = new ContextHandler("/repository/description");
		descriptionContext.setHandler(new RepositoryDescriptionHandler(metadataManager, artifactManager, gson, log));

		final ContextHandler installableUnitContext = new ContextHandler("/repository/iu");
		installableUnitContext.setHandler(new InstallableUnitHandler(metadataManager, artifactManager, gson, log));

		final Handler[] handlers = new Handler[] { rootContext, nameContext, descriptionContext,
				installableUnitContext };

		final ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(handlers);
		return contexts;
	}

}
