package com.avojak.webapp.p2.inspector.tests.osgi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.core.IProvisioningAgentProvider;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.avojak.webapp.p2.inspector.osgi.ProvisioningAgentProvider;

/**
 * Test class for {@link ProvisioningAgentProvider}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProvisioningAgentProviderTest {

	@Mock
	private BundleContext bundleContext;

	@Mock
	private ServiceReference<IProvisioningAgentProvider> providerRef;

	@Mock
	private IProvisioningAgentProvider provider;

	@Mock
	private IProvisioningAgent agent;

	/**
	 * Setup the mocks.
	 * 
	 * @throws ProvisionException Unexpected.
	 */
	@Before
	public void setup() throws ProvisionException {
		Mockito.when(bundleContext.getServiceReference(IProvisioningAgentProvider.SERVICE_NAME))
				.thenAnswer(new Answer<ServiceReference<IProvisioningAgentProvider>>() {
					@Override
					public ServiceReference<IProvisioningAgentProvider> answer(final InvocationOnMock invocation)
							throws Throwable {
						return providerRef;
					}
				});
		Mockito.when(bundleContext.getService(providerRef)).thenReturn(provider);
		Mockito.when(provider.createAgent(null)).thenReturn(agent);
	}

	/**
	 * Tests that the constructor throws an exception when the given
	 * {@link BundleContext} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullBundleContext() {
		new ProvisioningAgentProvider(null);
	}

	/**
	 * Tests that {@link ProvisioningAgentProvider#getAgent(java.net.URI)} throws an
	 * exception when no {@link IProvisioningAgentProvider} service reference is
	 * available.
	 * 
	 * @throws ProvisionException Unexpected.
	 */
	@Test
	public void testGetAgent_NullProviderRef() throws ProvisionException {
		Mockito.when(bundleContext.getServiceReference(IProvisioningAgentProvider.SERVICE_NAME)).thenReturn(null);
		try {
			new ProvisioningAgentProvider(bundleContext).getAgent(null);
			fail();
		} catch (final RuntimeException e) {
			assertEquals("No IProvisioningAgentProvider service reference is available", e.getMessage());
		}
	}

	/**
	 * Tests that {@link ProvisioningAgentProvider#getAgent(java.net.URI)} throws an
	 * exception when no {@link IProvisioningAgentProvider} service is available.
	 * 
	 * @throws ProvisionException Unexpected.
	 */
	@Test
	public void testGetAgent_NullProvider() throws ProvisionException {
		Mockito.when(bundleContext.getService(providerRef)).thenReturn(null);
		try {
			new ProvisioningAgentProvider(bundleContext).getAgent(null);
			fail();
		} catch (final RuntimeException e) {
			assertEquals("No IProvisioningAgentProvider service is available", e.getMessage());
		}
	}

	/**
	 * Tests {@link ProvisioningAgentProvider#getAgent(java.net.URI)}.
	 * 
	 * @throws ProvisionException Unexpected.
	 */
	@Test
	public void testGetAgent() throws ProvisionException {
		final IProvisioningAgent expected = new ProvisioningAgentProvider(bundleContext).getAgent(null);
		assertEquals(expected, agent);
		Mockito.verify(bundleContext).ungetService(providerRef);
	}

}
