package com.avojak.webapp.p2.inspector.server.handler.factory;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.jetty.server.handler.ContextHandler;

import com.avojak.webapp.p2.inspector.server.handler.RootHandler;

public class RootContextHandlerFactory {

	private final RootHandler.Factory handlerFactory;

	public RootContextHandlerFactory(final RootHandler.Factory handlerFactory) {
		this.handlerFactory = checkNotNull(handlerFactory, "handlerFactory cannot be null");
	}

	public ContextHandler create() {
		final ContextHandler context = new ContextHandler("/");
		context.setHandler(handlerFactory.create());
		return context;
	}

}
