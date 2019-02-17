package com.avojak.webapp.p2.inspector.server.factory;

import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;

/**
 * Factory class to create and configure the server thread pool.
 */
public class ThreadPoolFactory {

	/**
	 * Creates the thread pool.
	 * 
	 * @param maxThreads The maximum number of threads.
	 * @return The non-null {@link ThreadPool}.
	 */
	public ThreadPool create(final int maxThreads) {
		final QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setMaxThreads(maxThreads);
		return threadPool;
	}

}
