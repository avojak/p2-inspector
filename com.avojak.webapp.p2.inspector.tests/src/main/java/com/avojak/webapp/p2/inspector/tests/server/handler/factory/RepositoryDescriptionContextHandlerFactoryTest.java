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

import com.avojak.webapp.p2.inspector.server.handler.RepositoryDescriptionHandler;
import com.avojak.webapp.p2.inspector.server.handler.factory.RepositoryDescriptionContextHandlerFactory;

/**
 * Test class for {@link RepositoryDescriptionContextHandlerFactory}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RepositoryDescriptionContextHandlerFactoryTest {

	@Mock
	private RepositoryDescriptionHandler.Factory descriptionHandlerFactory;

	@Mock
	private RepositoryDescriptionHandler descriptionHandler;

	@Mock
	private IMetadataRepositoryManager metadataManager;

	@Mock
	private IArtifactRepositoryManager artifactManager;

	/**
	 * Tests that the constructor throws an exception when the given
	 * {@link RepositoryDescriptionHandler.Factory} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullHandlerFactory() {
		new RepositoryDescriptionContextHandlerFactory(null);
	}

	/**
	 * Tests
	 * {@link RepositoryDescriptionContextHandlerFactory#create(IMetadataRepositoryManager, IArtifactRepositoryManager)}.
	 */
	@Test
	public void testCreate() {
		Mockito.when(descriptionHandlerFactory.create(metadataManager, artifactManager)).thenReturn(descriptionHandler);

		final ContextHandler handler = new RepositoryDescriptionContextHandlerFactory(descriptionHandlerFactory)
				.create(metadataManager, artifactManager);

		assertEquals("/repository/description", handler.getContextPath());
		assertEquals(descriptionHandler, handler.getHandler());
	}

}
