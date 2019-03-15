package com.avojak.webapp.p2.inspector.server.handler;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.PrintWriter;

import javax.servlet.ServletException;

import org.eclipse.core.runtime.ILog;
import org.eclipse.jetty.server.Request;

/**
 * Implementation of {@link AbstractRequestHandler} to handle requests to the
 * root endpoint.
 */
public class RootHandler extends AbstractRequestHandler {

	/**
	 * Factory class to create instances of {@link RootHandler}.
	 */
	public static class Factory {

		private final ILog log;

		/**
		 * Constructor.
		 * 
		 * @param log
		 *            The {@link ILog}. Cannot be null.
		 */
		public Factory(final ILog log) {
			this.log = checkNotNull(log, "log cannot be null");
		}

		/**
		 * Creates a new instance of {@link RootHandler}.
		 * 
		 * @return The new, non-null {@link RootHandler}.
		 */
		public RootHandler create() {
			return new RootHandler(log);
		}

	}

	/**
	 * Constructor.
	 * 
	 * @param log
	 *            The {@link ILog}. Cannot be null.
	 */
	public RootHandler(final ILog log) {
		super(log);
	}

	@Override
	public void handle(final Request baseRequest, final PrintWriter out) throws ServletException {
		out.println("OK");
	}

}
