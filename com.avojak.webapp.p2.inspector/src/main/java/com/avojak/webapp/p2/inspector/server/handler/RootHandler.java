package com.avojak.webapp.p2.inspector.server.handler;

import java.io.PrintWriter;

import javax.servlet.ServletException;

import org.eclipse.core.runtime.ILog;
import org.eclipse.jetty.server.Request;

public class RootHandler extends AbstractRequestHandler {
	
	public static class Factory {
		
		private final ILog log;
		
		public Factory(final ILog log) {
			this.log = log;
		}
		
		public RootHandler create() {
			return new RootHandler(log);
		}
		
	}
	
	public RootHandler(final ILog log) {
		super(log);
	}

	@Override
	protected void handle(final Request baseRequest, final PrintWriter out) throws ServletException {
		out.println("OK");
	}

}
