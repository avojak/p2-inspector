package com.avojak.webapp.p2.inspector.server.factory;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

import com.avojak.webapp.p2.inspector.ApplicationProperties;
import com.google.common.base.Optional;

/**
 * Factory class to create and configure the server connector.
 */
public class ConnectorFactory {

	private final HttpConfigurationFactory httpConfigurationFactory;
	private final ApplicationProperties properties;

	/**
	 * Constructor.
	 * 
	 * @param httpConfigurationFactory The {@link HttpConfigurationFactory}. Cannot
	 *                                 be null.
	 * @param properties               The {@link ApplicationProperties}. Cannot be
	 *                                 null.
	 */
	public ConnectorFactory(final HttpConfigurationFactory httpConfigurationFactory,
			final ApplicationProperties properties) {
		this.httpConfigurationFactory = checkNotNull(httpConfigurationFactory,
				"httpConfigurationFactory cannot be null");
		this.properties = checkNotNull(properties, "properties cannot be null");
	}

	/**
	 * Creates the connector.
	 * 
	 * @param server The {@link Server} for which the connector is being created.
	 *               Cannot be null.
	 * @return The non-null {@link Connector}.
	 */
	public Connector create(final Server server) {
		final HttpConfiguration httpConfig = httpConfigurationFactory.create();
		final ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(httpConfig));
		http.setPort(getPort());
		http.setIdleTimeout(properties.getIdleTimeout());
		return http;
	}

	private int getPort() {
		final Optional<String> portEnvironmentVariable = Optional.fromNullable(System.getenv("PORT"));
		return portEnvironmentVariable.isPresent() ? Integer.valueOf(portEnvironmentVariable.get())
				: properties.getDefaultPort();
	}

}
