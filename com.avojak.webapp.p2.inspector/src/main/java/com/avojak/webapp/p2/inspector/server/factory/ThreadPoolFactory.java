package com.avojak.webapp.p2.inspector.server.factory;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;

import com.avojak.webapp.p2.inspector.ApplicationProperties;

/**
 * Factory class to create and configure the server thread pool.
 */
public class ThreadPoolFactory {

	private final ApplicationProperties properties;

	/**
	 * Constructor.
	 * 
	 * @param properties The {@link ApplicationProperties}. Cannot be null.
	 */
	public ThreadPoolFactory(final ApplicationProperties properties) {
		this.properties = checkNotNull(properties, "properties cannot be null");
	}

	/**
	 * Creates the thread pool.
	 * 
	 * @return The non-null {@link ThreadPool}.
	 */
	public QueuedThreadPool create() {
		final QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setMaxThreads(properties.getMaxThreads());
		return threadPool;
	}

}
