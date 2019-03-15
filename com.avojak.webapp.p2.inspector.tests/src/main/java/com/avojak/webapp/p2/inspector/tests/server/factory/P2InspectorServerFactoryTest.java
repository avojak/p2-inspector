package com.avojak.webapp.p2.inspector.tests.server.factory;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.avojak.webapp.p2.inspector.server.factory.ConnectorFactory;
import com.avojak.webapp.p2.inspector.server.factory.HandlerFactory;
import com.avojak.webapp.p2.inspector.server.factory.P2InspectorServerFactory;
import com.avojak.webapp.p2.inspector.server.factory.ServerFactory;
import com.avojak.webapp.p2.inspector.server.factory.ThreadPoolFactory;

/**
 * Test class for {@link P2InspectorServerFactory}.
 */
@RunWith(MockitoJUnitRunner.class)
public class P2InspectorServerFactoryTest {

	@Mock
	private ServerFactory serverFactory;

	@Mock
	private Server server;

	@Mock
	private ThreadPoolFactory threadPoolFactory;

	@Mock
	private QueuedThreadPool threadPool;

	@Mock
	private ConnectorFactory connectorFactory;

	@Mock
	private Connector connector;

	@Mock
	private HandlerFactory handlerFactory;

	@Mock
	private Handler handler;

	private P2InspectorServerFactory factory;

	/**
	 * Setup mocks.
	 */
	@Before
	public void setup() {
		factory = new P2InspectorServerFactory(serverFactory, threadPoolFactory, connectorFactory, handlerFactory);
		Mockito.when(threadPoolFactory.create()).thenReturn(threadPool);
		Mockito.when(serverFactory.create(threadPool)).thenReturn(server);
		Mockito.when(connectorFactory.create(server)).thenReturn(connector);
		Mockito.when(handlerFactory.create()).thenReturn(handler);
	}

	/**
	 * Tests that the constructor throws an exception when the given
	 * {@link ServerFactory} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullServerFactory() {
		new P2InspectorServerFactory(null, threadPoolFactory, connectorFactory, handlerFactory);
	}

	/**
	 * Tests that the constructor throws an exception when the given
	 * {@link ThreadPoolFactory} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullThreadPoolFactory() {
		new P2InspectorServerFactory(serverFactory, null, connectorFactory, handlerFactory);
	}

	/**
	 * Tests that the constructor throws an exception when the given
	 * {@link ConnectorFactory} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullConnectorFactory() {
		new P2InspectorServerFactory(serverFactory, threadPoolFactory, null, handlerFactory);
	}

	/**
	 * Tests that the constructor throws an exception when the given
	 * {@link HandlerFactory} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullHandlerFactory() {
		new P2InspectorServerFactory(serverFactory, threadPoolFactory, connectorFactory, null);
	}

	/**
	 * Tests {@link P2InspectorServerFactory#create()}.
	 */
	@Test
	public void testCreate() {
		factory.create();
		Mockito.verify(server).addConnector(connector);
		Mockito.verify(server).setHandler(handler);
		Mockito.verifyNoMoreInteractions(server);
	}

}
