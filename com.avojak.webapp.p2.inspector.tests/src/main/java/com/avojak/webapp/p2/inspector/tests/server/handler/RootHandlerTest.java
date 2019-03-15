package com.avojak.webapp.p2.inspector.tests.server.handler;

import static org.junit.Assert.assertNotNull;

import java.io.PrintWriter;

import javax.servlet.ServletException;

import org.eclipse.core.runtime.ILog;
import org.eclipse.jetty.server.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.avojak.webapp.p2.inspector.server.handler.RootHandler;

/**
 * Test class for {@link RootHandler}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RootHandlerTest {

	@Mock
	private ILog log;

	@Mock
	private Request request;

	@Mock
	private PrintWriter printWriter;

	/**
	 * Tests that the factory constructor throws an exception when the given
	 * {@link ILog} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testFactoryConstructor_NullLog() {
		new RootHandler.Factory(null);
	}

	/**
	 * Tests that the factory creates a non-null instance of {@link RootHandler}.
	 */
	@Test
	public void testFactoryCreate() {
		assertNotNull(new RootHandler.Factory(log).create());
	}

	/**
	 * Tests {@link RootHandler#handle(Request, PrintWriter)}.
	 * 
	 * @throws ServletException
	 *             Unexpected.
	 */
	@Test
	public void testHandle() throws ServletException {
		new RootHandler(log).handle(request, printWriter);
		Mockito.verify(printWriter).println("OK");
	}

}
