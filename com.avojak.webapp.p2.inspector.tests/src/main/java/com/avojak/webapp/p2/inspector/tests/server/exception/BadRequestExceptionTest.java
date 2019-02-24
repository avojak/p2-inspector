package com.avojak.webapp.p2.inspector.tests.server.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.avojak.webapp.p2.inspector.server.exception.BadRequestException;

/**
 * Test class for {@link BadRequestException}.
 */
public class BadRequestExceptionTest {
	
	/**
	 * Tests that the constructor sets the cause.
	 */
	@Test
	public void test() {
		final Throwable cause = new IllegalArgumentException("oh no");
		final BadRequestException exception = new BadRequestException(cause);
		assertEquals(cause, exception.getCause());
	}

}
