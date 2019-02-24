package com.avojak.webapp.p2.inspector.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.osgi.framework.BundleContext;

import com.avojak.webapp.p2.inspector.Activator;

/**
 * Test class for {@link Activator}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ActivatorTest {

	@Mock
	private BundleContext context;

	/**
	 * Tests the starting and stopping of the {@link Activator}.
	 * 
	 * @throws Exception Unexpected.
	 */
	@Test
	public void testStartAndStop() throws Exception {
		final Activator activator = new Activator();
		activator.stop(context);
		assertNull(Activator.getContext());
		activator.start(context);
		assertEquals(context, Activator.getContext());
		activator.stop(context);
		assertNull(Activator.getContext());
	}

}
