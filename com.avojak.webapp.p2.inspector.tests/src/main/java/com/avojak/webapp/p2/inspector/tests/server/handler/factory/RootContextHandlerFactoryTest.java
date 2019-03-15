package com.avojak.webapp.p2.inspector.tests.server.handler.factory;

import static org.junit.Assert.assertEquals;

import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.avojak.webapp.p2.inspector.server.handler.RootHandler;
import com.avojak.webapp.p2.inspector.server.handler.factory.RootContextHandlerFactory;

/**
 * Test class for {@link RootContextHandlerFactory}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RootContextHandlerFactoryTest {

	@Mock
	private RootHandler.Factory rootHandlerFactory;

	@Mock
	private RootHandler rootHandler;

	/**
	 * Tests that the constructor throws an exception when the given
	 * {@link RootHandler.Factory} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullHandlerFactory() {
		new RootContextHandlerFactory(null);
	}

	/**
	 * Tests
	 * {@link RootContextHandlerFactory#create(IMetadataRepositoryManager, IArtifactRepositoryManager)}.
	 */
	@Test
	public void testCreate() {
		Mockito.when(rootHandlerFactory.create()).thenReturn(rootHandler);

		final ContextHandler handler = new RootContextHandlerFactory(rootHandlerFactory).create();

		assertEquals("/", handler.getContextPath());
		assertEquals(rootHandler, handler.getHandler());
	}

}
