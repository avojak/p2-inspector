package com.avojak.webapp.p2.inspector.tests.server.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.core.runtime.ILog;
import org.eclipse.jetty.server.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.avojak.webapp.p2.inspector.server.exception.BadRequestException;
import com.avojak.webapp.p2.inspector.server.handler.AbstractRequestHandler;

/**
 * Test class for {@link AbstractRequestHandler}.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractRequestHandlerTest {

	/**
	 * Test implementation of {@link AbstractRequestHandler}.
	 */
	private static class TestAbstractRequestHandler extends AbstractRequestHandler {

		protected TestAbstractRequestHandler(ILog log) {
			super(log);
		}

		@Override
		protected void handle(Request baseRequest, PrintWriter out) throws ServletException, BadRequestException {
			// Do nothing
		}

	}

	@Mock
	private ILog log;

	@Mock
	private Request request;

	@Mock
	private HttpServletRequest servletRequest;

	@Mock
	private HttpServletResponse servletResponse;

	@Mock
	private PrintWriter printWriter;

	private AbstractRequestHandler handler;

	/**
	 * Setup mocks.
	 * 
	 * @throws IOException
	 *             Unexpected.
	 */
	@Before
	public void setup() throws IOException {
		Mockito.when(servletResponse.getWriter()).thenReturn(printWriter);
		handler = new TestAbstractRequestHandler(log);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor_NullLog() {
		new TestAbstractRequestHandler(null);
	}

	/**
	 * Tests
	 * {@link AbstractRequestHandler#handle(String, Request, HttpServletRequest, HttpServletResponse)}.
	 * 
	 * @throws ServletException
	 *             Unexpected.
	 * @throws IOException
	 *             Unexpected.
	 */
	@Test
	public void testHandle_BadRequest() throws IOException, ServletException {
		new TestAbstractRequestHandler(log) {
			@Override
			public void handle(final Request baseRequest, final PrintWriter out)
					throws ServletException, BadRequestException {
				throw new BadRequestException(null);
			}
		}.handle("/foo", request, servletRequest, servletResponse);

		Mockito.verify(servletResponse, Mockito.times(2)).getWriter();
		Mockito.verify(servletResponse).setContentType("text/html; charset=utf-8");
		Mockito.verify(servletResponse).setStatus(400);
		Mockito.verify(request).setHandled(true);
	}

	/**
	 * Tests
	 * {@link AbstractRequestHandler#handle(String, Request, HttpServletRequest, HttpServletResponse)}.
	 * 
	 * @throws ServletException
	 *             Unexpected.
	 * @throws IOException
	 *             Unexpected.
	 */
	@Test
	public void testHandle() throws IOException, ServletException {
		handler.handle("/foo", request, servletRequest, servletResponse);

		Mockito.verify(servletResponse).getWriter();
		Mockito.verify(servletResponse).setContentType("application/json; charset=utf-8");
		Mockito.verify(servletResponse).setStatus(200);
		Mockito.verify(request).setHandled(true);
	}

}
