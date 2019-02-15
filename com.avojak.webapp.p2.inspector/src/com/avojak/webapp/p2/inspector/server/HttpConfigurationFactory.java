package com.avojak.webapp.p2.inspector.server;

import org.eclipse.jetty.server.HttpConfiguration;

/**
 * Factory class to create and configure the HTTP configuration.
 */
public class HttpConfigurationFactory {

	/**
	 * Creates the HTTP configuration.
	 * 
	 * @return The non-null {@link HttpConfiguration}.
	 */
	public HttpConfiguration create() {
		final HttpConfiguration httpConfig = new HttpConfiguration();
		httpConfig.setSendServerVersion(false);
		httpConfig.setSendDateHeader(false);
		return httpConfig;
	}

}
