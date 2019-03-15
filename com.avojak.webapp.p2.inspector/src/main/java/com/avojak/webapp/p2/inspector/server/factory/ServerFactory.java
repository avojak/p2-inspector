package com.avojak.webapp.p2.inspector.server.factory;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.ThreadPool;

/**
 * Factory class to create basic instances of {@link Server}.
 */
public class ServerFactory {

	/**
	 * Creates a new {@link Server}.
	 * 
	 * @param threadPool
	 *            The {@link ThreadPool}. Cannot be null.
	 * @return The non-null {@link Server}.
	 */
	public Server create(final ThreadPool threadPool) {
		checkNotNull(threadPool, "threadPool cannot be null");
		return new Server(threadPool);
	}

}
