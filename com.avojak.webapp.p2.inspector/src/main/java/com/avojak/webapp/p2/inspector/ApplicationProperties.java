package com.avojak.webapp.p2.inspector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Models the application properties.
 */
public class ApplicationProperties {

	private static ApplicationProperties INSTANCE;

	private final int defaultPort;
	private final int maxThreads;
	private final long idleTimeout;

	/**
	 * Constructor.
	 * 
	 * @param defaultPort The default port to use if not provided.
	 * @param maxThreads  The maximum number of threads in the connection thread
	 *                    pool.
	 * @param idleTimeout The connector idle timeout.
	 */
	private ApplicationProperties(final int defaultPort, final int maxThreads, final long idleTimeout) {
		this.defaultPort = defaultPort;
		this.maxThreads = maxThreads;
		this.idleTimeout = idleTimeout;
	}

	/**
	 * Get the properties instance.
	 * 
	 * @return The non-null {@link ApplicationProperties}.
	 */
	public static ApplicationProperties getProperties() {
		if (INSTANCE == null) {
			final Properties properties = new Properties();
			final URL resource = Activator.getContext().getBundle().getResource("application.properties");
			if (resource == null) {
				throw new RuntimeException(
						new FileNotFoundException("Resource file [application.properties] could not be found"));
			}
			try {
				properties.load(resource.openStream());
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
			INSTANCE = new ApplicationProperties(Integer.valueOf(properties.getProperty("server.default.port")),
					Integer.valueOf(properties.getProperty("server.max.threads")),
					Long.valueOf(properties.getProperty("server.idle.timeout")));
		}
		return INSTANCE;
	}

	/**
	 * Gets the default port number.
	 * 
	 * @return The default port number.
	 */
	public int getDefaultPort() {
		return defaultPort;
	}

	/**
	 * Gets the maximum number of threads in the connection pool.
	 * 
	 * @return The maximum number of threads in the connection pool.
	 */
	public int getMaxThreads() {
		return maxThreads;
	}

	/**
	 * Gets the connector idle timeout.
	 * 
	 * @return The connector idle timeout.
	 */
	public long getIdleTimeout() {
		return idleTimeout;
	}

}
