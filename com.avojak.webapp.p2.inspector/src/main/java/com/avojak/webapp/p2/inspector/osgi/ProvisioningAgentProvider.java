package com.avojak.webapp.p2.inspector.osgi;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;

import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.core.IProvisioningAgentProvider;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Class to provide access to the provisioning agent.
 */
public class ProvisioningAgentProvider {

	private final BundleContext bundleContext;

	/**
	 * Constructor.
	 * 
	 * @param bundleContext The {@link BundleContext}. Cannot be null.
	 */
	public ProvisioningAgentProvider(final BundleContext bundleContext) {
		this.bundleContext = checkNotNull(bundleContext, "bundleContext cannot be null");
	}

	/**
	 * Gets the provisioning agent.
	 * 
	 * @param location The location where the agent metadata is stored.
	 * @return The non-null {@link IProvisioningAgent}. May be null.
	 * @throws ProvisionException If agent creation failed.
	 */
	public IProvisioningAgent getAgent(final URI location) throws ProvisionException {
		IProvisioningAgent result = null;
		ServiceReference<?> providerRef = bundleContext.getServiceReference(IProvisioningAgentProvider.SERVICE_NAME);
		if (providerRef == null) {
			throw new RuntimeException("No IProvisioningAgentProvider service reference is available"); //$NON-NLS-1$
		}
		IProvisioningAgentProvider provider = (IProvisioningAgentProvider) bundleContext.getService(providerRef);
		if (provider == null) {
			throw new RuntimeException("No IProvisioningAgentProvider service is available"); //$NON-NLS-1$
		}
		result = provider.createAgent(location);
		bundleContext.ungetService(providerRef);
		return result;
	}

}
