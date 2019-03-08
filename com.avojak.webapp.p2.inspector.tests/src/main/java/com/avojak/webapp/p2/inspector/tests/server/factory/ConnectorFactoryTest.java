package com.avojak.webapp.p2.inspector.tests.server.factory;

import static org.junit.Assert.assertEquals;

import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.avojak.webapp.p2.inspector.ApplicationProperties;
import com.avojak.webapp.p2.inspector.server.factory.ConnectorFactory;
import com.avojak.webapp.p2.inspector.server.factory.HttpConfigurationFactory;

/**
 * Test class for {@link ConnectorFactory}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConnectorFactoryTest {

	@Mock
	private HttpConfigurationFactory httpConfigurationFactory;

	@Mock
	private HttpConfiguration httpConfiguration;

	@Mock
	private ApplicationProperties properties;

	@Mock
	private Server server;

	private final int port = 8091;
	private final long idleTimeout = 30000L;

	private ConnectorFactory connectorFactory;

	/**
	 * Setup mocks.
	 */
	@Before
	public void setup() {
		connectorFactory = new ConnectorFactory(httpConfigurationFactory, properties);
		Mockito.when(httpConfigurationFactory.create()).thenReturn(httpConfiguration);
		Mockito.when(properties.getDefaultPort()).thenReturn(port);
		Mockito.when(properties.getIdleTimeout()).thenReturn(idleTimeout);
		Mockito.when(server.getThreadPool()).thenReturn(Mockito.mock(ThreadPool.class));
	}

	/**
	 * Tests that the constructor throws an exception when the given
	 * {@link HttpConfigurationFactory} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullHttpConfigurationFactory() {
		new ConnectorFactory(null, properties);
	}

	/**
	 * Tests that the constructor throws an exception when the given
	 * {@link ApplicationProperties} object is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullApplicationProperties() {
		new ConnectorFactory(httpConfigurationFactory, null);
	}

	/**
	 * Tests that {@link ConnectorFactory#create(Server)} throws an exception when
	 * the given {@link Server} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testCreate_NullServer() {
		connectorFactory.create(null);
	}

	/**
	 * Tests {@link ConnectorFactory#create(Server)}.
	 */
	@Test
	public void testCreate() {
		final ServerConnector connector = (ServerConnector) connectorFactory.create(server);
		assertEquals(1, connector.getConnectionFactories().size());
		assertEquals(30000, connector.getIdleTimeout());
		assertEquals(port, connector.getPort());
	}

}
