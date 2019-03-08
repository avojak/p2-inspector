package com.avojak.webapp.p2.inspector;

import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jetty.server.Server;

import com.avojak.webapp.p2.inspector.osgi.ProvisioningAgentProvider;
import com.avojak.webapp.p2.inspector.server.factory.ConnectorFactory;
import com.avojak.webapp.p2.inspector.server.factory.HandlerFactory;
import com.avojak.webapp.p2.inspector.server.factory.HttpConfigurationFactory;
import com.avojak.webapp.p2.inspector.server.factory.P2InspectorServerFactory;
import com.avojak.webapp.p2.inspector.server.factory.ThreadPoolFactory;
import com.avojak.webapp.p2.inspector.server.handler.InstallableUnitHandler;
import com.avojak.webapp.p2.inspector.server.handler.RepositoryDescriptionHandler;
import com.avojak.webapp.p2.inspector.server.handler.RepositoryNameHandler;
import com.avojak.webapp.p2.inspector.server.handler.RootHandler;
import com.avojak.webapp.p2.inspector.server.handler.factory.InstallableUnitContextHandlerFactory;
import com.avojak.webapp.p2.inspector.server.handler.factory.RepositoryDescriptionContextHandlerFactory;
import com.avojak.webapp.p2.inspector.server.handler.factory.RepositoryNameContextHandlerFactory;
import com.avojak.webapp.p2.inspector.server.handler.factory.RootContextHandlerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The P2 Inspector {@link IApplication} implementation.
 */
public class Application implements IApplication {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final ApplicationProperties PROPERTIES = ApplicationProperties.getProperties();

	private final P2InspectorServerFactory serverFactory;

	/**
	 * Default constructor.
	 */
	public Application() {
		//@formatter:off
		this(new P2InspectorServerFactory(new ThreadPoolFactory(PROPERTIES),
				new ConnectorFactory(new HttpConfigurationFactory(), PROPERTIES),
				new HandlerFactory(new ProvisioningAgentProvider(Activator.getContext()),
						new RootContextHandlerFactory(new RootHandler.Factory(
								Platform.getLog(Activator.getContext().getBundle()))),
						new RepositoryNameContextHandlerFactory(new RepositoryNameHandler.Factory(GSON, 
								Platform.getLog(Activator.getContext().getBundle()))),
						new RepositoryDescriptionContextHandlerFactory(new RepositoryDescriptionHandler.Factory(GSON, 
								Platform.getLog(Activator.getContext().getBundle()))),
						new InstallableUnitContextHandlerFactory(new InstallableUnitHandler.Factory(GSON, 
								Platform.getLog(Activator.getContext().getBundle()))))));
		//@formatter:on
	}

	/**
	 * Constructor for testing purposes to enable dependency injection.
	 */
	public Application(final P2InspectorServerFactory serverFactory) {
		this.serverFactory = serverFactory;
	}

	@Override
	public Object start(final IApplicationContext applicationContext) throws Exception {
		final Server server = serverFactory.create();
		server.start();
		server.join();

		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// Nothing to do
	}

}
