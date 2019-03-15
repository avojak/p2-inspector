package com.avojak.webapp.p2.inspector.server.factory;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.jetty.server.Server;

/**
 * Factory class to create and configure the underlying server.
 */
public class P2InspectorServerFactory {

	private final ServerFactory serverFactory;
	private final ThreadPoolFactory threadPoolFactory;
	private final ConnectorFactory connectorFactory;
	private final HandlerFactory handlerFactory;

	/**
	 * Constructor.
	 * 
	 * @param threadPoolFactory
	 *            The {@link ThreadPoolFactory}. Cannot be null.
	 * @param connectorFactory
	 *            The {@link ConnectorFactory}. Cannot be null.
	 * @param handlerFactory
	 *            The {@link HandlerFactory}. Cannot be null.
	 */
	public P2InspectorServerFactory(final ServerFactory serverFactory, final ThreadPoolFactory threadPoolFactory,
			final ConnectorFactory connectorFactory, final HandlerFactory handlerFactory) {
		this.serverFactory = checkNotNull(serverFactory, "serverFactory cannot be null");
		this.threadPoolFactory = checkNotNull(threadPoolFactory, "threadPoolFactory cannot be null");
		this.connectorFactory = checkNotNull(connectorFactory, "connectorFactory cannot be null");
		this.handlerFactory = checkNotNull(handlerFactory, "handlerFactory cannot be null");
	}

	/**
	 * Creates the server.
	 * 
	 * @return The non-null {@link Server}.
	 */
	public Server create() {
		final Server server = serverFactory.create(threadPoolFactory.create());
		server.addConnector(connectorFactory.create(server));
		server.setHandler(handlerFactory.create());
		return server;
	}

}
