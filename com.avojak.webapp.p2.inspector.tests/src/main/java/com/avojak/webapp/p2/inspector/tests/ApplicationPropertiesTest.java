package com.avojak.webapp.p2.inspector.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.avojak.webapp.p2.inspector.ApplicationProperties;

/**
 * Test class for {@link ApplicationProperties}.
 */
public class ApplicationPropertiesTest {

	/**
	 * Tests that the properties are properly loaded.
	 */
	@Test
	public void test() {
		final ApplicationProperties properties = ApplicationProperties.getProperties();
		assertEquals(8081, properties.getDefaultPort());
		assertEquals(100, properties.getMaxThreads());
		assertEquals(30000L, properties.getIdleTimeout());
	}

}
