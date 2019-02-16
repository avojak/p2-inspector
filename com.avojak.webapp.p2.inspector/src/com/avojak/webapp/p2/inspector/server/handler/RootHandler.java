package com.avojak.webapp.p2.inspector.server.handler;

import java.io.PrintWriter;

import javax.servlet.ServletException;

import org.eclipse.jetty.server.Request;

public class RootHandler extends AbstractRequestHandler {

	@Override
	protected void handle(final Request baseRequest, final PrintWriter out) throws ServletException {
		out.println("OK");
	}

}
