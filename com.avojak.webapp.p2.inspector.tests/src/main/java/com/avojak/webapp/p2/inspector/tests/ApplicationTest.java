package com.avojak.webapp.p2.inspector.tests;

import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jetty.server.Server;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.avojak.webapp.p2.inspector.Application;
import com.avojak.webapp.p2.inspector.server.factory.P2InspectorServerFactory;

/**
 * Test class for {@link Application}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

	@Mock
	private P2InspectorServerFactory serverFactory;

	@Mock
	private IApplicationContext applicationContext;

	@Mock
	private Server server;

	/**
	 * Setup mocks.
	 * 
	 * @throws Exception Unexpected.
	 */
	@Before
	public void setup() throws Exception {
		Mockito.when(serverFactory.create()).thenReturn(server);
	}

	/**
	 * Tests {@link Application#start(IApplicationContext)}.
	 * 
	 * @throws Exception Unexpected.
	 */
	@Test
	public void testStart() throws Exception {
		new Application(serverFactory).start(applicationContext);
		final InOrder inOrder = Mockito.inOrder(server);
		inOrder.verify(server).start();
		inOrder.verify(server).join();
	}

}
