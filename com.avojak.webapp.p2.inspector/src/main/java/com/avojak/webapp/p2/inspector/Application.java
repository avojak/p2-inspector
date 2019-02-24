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
import com.google.gson.GsonBuilder;

/**
 * The P2 Inspector {@link IApplication} implementation.
 */
public class Application implements IApplication {

	private final P2InspectorServerFactory serverFactory;

	/**
	 * Default constructor.
	 */
	public Application() {
		this(new P2InspectorServerFactory(new ThreadPoolFactory(ApplicationProperties.getProperties()),
				new ConnectorFactory(new HttpConfigurationFactory(), ApplicationProperties.getProperties()),
				new HandlerFactory(new ProvisioningAgentProvider(Activator.getContext()),
						new GsonBuilder().setPrettyPrinting().create(),
						Platform.getLog(Activator.getContext().getBundle()))));
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
