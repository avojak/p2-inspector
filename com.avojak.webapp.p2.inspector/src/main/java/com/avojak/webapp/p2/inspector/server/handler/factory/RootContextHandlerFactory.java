package com.avojak.webapp.p2.inspector.server.handler.factory;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.jetty.server.handler.ContextHandler;

import com.avojak.webapp.p2.inspector.server.handler.RootHandler;

/**
 * Factory class to create and configure the root {@link ContextHandler}.
 */
public class RootContextHandlerFactory {

	private final RootHandler.Factory handlerFactory;

	/**
	 * Constructor.
	 * 
	 * @param handlerFactory
	 *            The {@link RootHandler.Factory}. Cannot be null.
	 */
	public RootContextHandlerFactory(final RootHandler.Factory handlerFactory) {
		this.handlerFactory = checkNotNull(handlerFactory, "handlerFactory cannot be null");
	}

	/**
	 * Creates and configures the new context handler.
	 * 
	 * @return The new, non-null {@link ContextHandler}.
	 */
	public ContextHandler create() {
		final ContextHandler context = new ContextHandler("/");
		context.setHandler(handlerFactory.create());
		return context;
	}

}
