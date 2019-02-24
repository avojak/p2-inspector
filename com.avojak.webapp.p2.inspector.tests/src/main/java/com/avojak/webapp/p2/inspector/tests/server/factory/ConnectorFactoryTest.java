package com.avojak.webapp.p2.inspector.tests.server.factory;

import static org.junit.Assert.assertEquals;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

	@Before
	public void setup() {
		connectorFactory = new ConnectorFactory(httpConfigurationFactory, properties);
//		Mockito.when(httpConfigurationFactory.create()).thenReturn(httpConfiguration);
//		Mockito.when(properties.getDefaultPort()).thenReturn(port);
//		Mockito.when(properties.getIdleTimeout()).thenReturn(idleTimeout);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor_NullHttpConfigurationFactory() {
		new ConnectorFactory(null, properties);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor_NullApplicationProperties() {
		new ConnectorFactory(httpConfigurationFactory, null);
	}

	@Test(expected = NullPointerException.class)
	public void testCreate_NullServer() {
		connectorFactory.create(null);
	}

	@Test
	public void testCreate() {
		// TODO: need to mock the connection factory :(
//		final ServerConnector expected = new ServerConnector(server, new HttpConnectionFactory(httpConfiguration));
//		expected.setPort(port);
//		expected.setIdleTimeout(idleTimeout);
//		
//		assertEquals(expected, connectorFactory.create(server));
	}

}
