package com.avojak.webapp.p2.inspector;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jetty.server.Server;

import com.avojak.webapp.p2.inspector.osgi.ProvisioningAgentProvider;
import com.avojak.webapp.p2.inspector.server.ConnectorFactory;
import com.avojak.webapp.p2.inspector.server.HandlerFactory;
import com.avojak.webapp.p2.inspector.server.HttpConfigurationFactory;
import com.avojak.webapp.p2.inspector.server.P2InspectorServerFactory;
import com.avojak.webapp.p2.inspector.server.ThreadPoolFactory;
import com.google.common.base.Optional;
import com.google.gson.GsonBuilder;

/**
 * The P2 Inspector {@link IApplication} implementation.
 */
public class Application implements IApplication {

	private static final String PORT_ENV_VAR = "PORT";
	private static final int DEFAULT_PORT = 8081;
	private static final int MAX_THREADS = 100;
	private static final long IDLE_TIMEOUT = 30000L;

	private final P2InspectorServerFactory serverFactory;

	/**
	 * Default constructor.
	 */
	public Application() {
		this(new P2InspectorServerFactory(new ThreadPoolFactory(), new ConnectorFactory(new HttpConfigurationFactory()),
				new HandlerFactory(new ProvisioningAgentProvider(Activator.getContext()),
						new GsonBuilder().setPrettyPrinting().create())));
	}

	/**
	 * Constructor for testing purposes to enable dependency injection.
	 */
	protected Application(final P2InspectorServerFactory serverFactory) {
		this.serverFactory = serverFactory;
	}

	@Override
	public Object start(final IApplicationContext applicationContext) throws Exception {
		final Server server = serverFactory.create(getPort());
		server.start();
		server.join();

		return IApplication.EXIT_OK;
	}

	private int getPort() {
		final Optional<String> portEnvironmentVariable = Optional.fromNullable(System.getenv(PORT_ENV_VAR));
		return portEnvironmentVariable.isPresent() ? Integer.valueOf(portEnvironmentVariable.get()) : DEFAULT_PORT;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

}
