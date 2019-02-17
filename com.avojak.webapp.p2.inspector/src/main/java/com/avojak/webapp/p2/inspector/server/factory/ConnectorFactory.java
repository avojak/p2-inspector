package com.avojak.webapp.p2.inspector.server.factory;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

/**
 * Factory class to create and configure the server connector.
 */
public class ConnectorFactory {

	private final HttpConfigurationFactory httpConfigurationFactory;

	/**
	 * Constructor.
	 * 
	 * @param httpConfigurationFactory The {@link HttpConfigurationFactory}. Cannot
	 *                                 be null.
	 */
	public ConnectorFactory(final HttpConfigurationFactory httpConfigurationFactory) {
		this.httpConfigurationFactory = checkNotNull(httpConfigurationFactory,
				"httpConfigurationFactory cannot be null");
	}

	/**
	 * Creates the connector.
	 * 
	 * @param server      The {@link Server} for which the connector is being
	 *                    created. Cannot be null.
	 * @param port        The port number.
	 * @param idleTimeout The maximum idle time for a connection.
	 * @return The non-null {@link Connector}.
	 */
	public Connector create(final Server server, final int port, final long idleTimeout) {
		final HttpConfiguration httpConfig = httpConfigurationFactory.create();

		final ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(httpConfig));
		http.setPort(port);
		http.setIdleTimeout(idleTimeout);

		return http;
	}

}
