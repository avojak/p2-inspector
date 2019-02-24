package com.avojak.webapp.p2.inspector.tests;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.avojak.webapp.p2.inspector.Activator;
import com.avojak.webapp.p2.inspector.ApplicationProperties;

/**
 * Test class for {@link ApplicationProperties}.
 */
public class ApplicationPropertiesTest {

	/**
	 * Setup the bundle context.
	 * 
	 * @throws Exception Unexpected.
	 */
	@BeforeClass
	public static void setupBundleContext() throws Exception {
		final Activator activator = new Activator();
		final BundleContext bundleContext = Mockito.mock(BundleContext.class);
		final Bundle bundle = Mockito.mock(Bundle.class);
		Mockito.when(bundleContext.getBundle()).thenReturn(bundle);
		Mockito.when(bundle.getResource("application.properties"))
				.thenReturn(Activator.class.getClassLoader().getResource("application.properties"));
		activator.start(bundleContext);
	}

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
