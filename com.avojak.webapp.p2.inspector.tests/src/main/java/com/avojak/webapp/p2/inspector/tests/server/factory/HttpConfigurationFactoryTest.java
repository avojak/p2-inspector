package com.avojak.webapp.p2.inspector.tests.server.factory;

import static org.junit.Assert.assertFalse;

import org.eclipse.jetty.server.HttpConfiguration;
import org.junit.Test;

import com.avojak.webapp.p2.inspector.server.factory.HttpConfigurationFactory;

/**
 * Test class for {@link HttpConfigurationFactory}.
 */
public class HttpConfigurationFactoryTest {

	/**
	 * Tests {@link HttpConfigurationFactory#create()}.
	 */
	@Test
	public void testCreate() {
		final HttpConfiguration config = new HttpConfigurationFactory().create();
		assertFalse(config.getSendServerVersion());
		assertFalse(config.getSendDateHeader());
	}

}
