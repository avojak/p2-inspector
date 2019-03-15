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

import com.avojak.webapp.p2.inspector.server.handler.RepositoryNameHandler;
import com.avojak.webapp.p2.inspector.server.handler.factory.RepositoryNameContextHandlerFactory;

/**
 * Test class for {@link RepositoryNameContextHandlerFactory}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RepositoryNameContextHandlerFactoryTest {

	@Mock
	private RepositoryNameHandler.Factory nameHandlerFactory;

	@Mock
	private RepositoryNameHandler nameHandler;

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
		new RepositoryNameContextHandlerFactory(null);
	}

	/**
	 * Tests
	 * {@link RepositoryNameContextHandlerFactory#create(IMetadataRepositoryManager, IArtifactRepositoryManager)}.
	 */
	@Test
	public void testCreate() {
		Mockito.when(nameHandlerFactory.create(metadataManager, artifactManager)).thenReturn(nameHandler);

		final ContextHandler handler = new RepositoryNameContextHandlerFactory(nameHandlerFactory)
				.create(metadataManager, artifactManager);

		assertEquals("/repository/name", handler.getContextPath());
		assertEquals(nameHandler, handler.getHandler());
	}

}
