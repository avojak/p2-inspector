package com.avojak.webapp.p2.inspector.tests.server.factory;

import static org.junit.Assert.assertEquals;

import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.avojak.webapp.p2.inspector.ApplicationProperties;
import com.avojak.webapp.p2.inspector.server.factory.ThreadPoolFactory;

/**
 * Test class for {@link ThreadPoolFactory}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ThreadPoolFactoryTest {

	@Mock
	private ApplicationProperties properties;

	@Test(expected = NullPointerException.class)
	public void testConstructor_NullProperties() {
		new ThreadPoolFactory(null);
	}

	@Test
	public void testCreate() {
		final int maxThreads = 10;
		Mockito.when(properties.getMaxThreads()).thenReturn(maxThreads);

		final QueuedThreadPool threadPool = new ThreadPoolFactory(properties).create();
		assertEquals(maxThreads, threadPool.getMaxThreads());
	}

}
