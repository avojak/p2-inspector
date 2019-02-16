package com.avojak.webapp.p2.inspector.server.exception;

/**
 * Exception to indicate a bad request to the server.
 */
public class BadRequestException extends Exception {

	private static final long serialVersionUID = -6738508978203977978L;

	/**
	 * Constructor.
	 * 
	 * @param cause The cause {@link Throwable}.
	 */
	public BadRequestException(final Throwable cause) {
		super(cause);
	}

}
