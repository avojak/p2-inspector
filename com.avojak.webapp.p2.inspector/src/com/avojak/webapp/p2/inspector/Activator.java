package com.avojak.webapp.p2.inspector;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		Activator.context = context;
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		Activator.context = null;
	}

}
