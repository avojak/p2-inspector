package com.avojak.webapp.p2.inspector.tests.osgi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.BundleContext;

import com.avojak.webapp.p2.inspector.osgi.ProvisioningAgentProvider;

/**
 * Test class for {@link ProvisioningAgentProvider}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProvisioningAgentProviderTest {

	@Mock
	private BundleContext bundleContext;
	
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullBundleContext() {
		new ProvisioningAgentProvider(null);
	}
	
	@Test
	public void testGetAgent_NullProviderRef() {

	}
	
	@Test
	public void testGetAgent_NullProvider() {
		
	}
	
	@Test
	public void testGetAgent() {
		
	}
	
}
