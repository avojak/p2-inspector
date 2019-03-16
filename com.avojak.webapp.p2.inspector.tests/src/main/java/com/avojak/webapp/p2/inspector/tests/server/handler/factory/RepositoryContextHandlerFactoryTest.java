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

import com.avojak.webapp.p2.inspector.server.handler.RepositoryHandler;
import com.avojak.webapp.p2.inspector.server.handler.factory.RepositoryContextHandlerFactory;

/**
 * Test class for {@link RepositoryContextHandlerFactory}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RepositoryContextHandlerFactoryTest {

	@Mock
	private RepositoryHandler.Factory handlerFactory;

	@Mock
	private RepositoryHandler handler;

	@Mock
	private IMetadataRepositoryManager metadataManager;

	@Mock
	private IArtifactRepositoryManager artifactManager;

	/**
	 * Tests that the constructor throws an exception when the given
	 * {@link RepositoryNameHandler.Factory} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullHandlerFactory() {
		new RepositoryContextHandlerFactory(null);
	}

	/**
	 * Tests
	 * {@link RepositoryContextHandlerFactory#create(IMetadataRepositoryManager, IArtifactRepositoryManager)}.
	 */
	@Test
	public void testCreate() {
		Mockito.when(handlerFactory.create(metadataManager, artifactManager)).thenReturn(handler);

		final ContextHandler contextHandler = new RepositoryContextHandlerFactory(handlerFactory)
				.create(metadataManager, artifactManager);

		assertEquals("/repository", contextHandler.getContextPath());
		assertEquals(handler, contextHandler.getHandler());
	}

}
