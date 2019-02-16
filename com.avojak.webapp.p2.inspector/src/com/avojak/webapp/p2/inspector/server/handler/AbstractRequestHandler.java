package com.avojak.webapp.p2.inspector.server.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.avojak.webapp.p2.inspector.server.exception.BadRequestException;
import com.google.common.net.MediaType;

/**
 * Base request handler.
 */
public abstract class AbstractRequestHandler extends AbstractHandler {

	@Override
	public void handle(final String target, final Request baseRequest, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException, ServletException {
		try {
			handle(baseRequest, response.getWriter());
			response.setContentType(MediaType.JSON_UTF_8.toString());
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (final BadRequestException e) {
			// TODO: Log this
			e.printStackTrace(response.getWriter());
			response.setContentType(MediaType.HTML_UTF_8.toString());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		baseRequest.setHandled(true);
	}

	/**
	 * Handles the request.
	 *
	 * @param baseRequest The {@link Request}. Cannot be null.
	 * @param out         The HTTP response {@link PrintWriter}. Cannot be null.
	 * @throws ServletException
	 * @throws BadRequestException
	 */
	protected abstract void handle(final Request baseRequest, final PrintWriter out)
			throws ServletException, BadRequestException;

}
