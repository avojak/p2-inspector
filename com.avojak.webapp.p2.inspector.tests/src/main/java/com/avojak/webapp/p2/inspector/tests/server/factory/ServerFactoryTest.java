package com.avojak.webapp.p2.inspector.tests.server.factory;

import static org.junit.Assert.assertEquals;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.avojak.webapp.p2.inspector.server.factory.ServerFactory;

/**
 * Test class for {@link ServerFactory}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ServerFactoryTest {

	@Mock
	private ThreadPool threadPool;

	/**
	 * Test that {@link ServerFactory#create()} throws an exception when the
	 * {@link ThreadPool} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testCreate_NullThreadPool() {
		new ServerFactory().create(null);
	}

	/**
	 * Test {@link ServerFactory#create()}.
	 */
	@Test
	public void testCreate() {
		final Server server = new ServerFactory().create(threadPool);
		assertEquals(threadPool, server.getThreadPool());
	}

}
