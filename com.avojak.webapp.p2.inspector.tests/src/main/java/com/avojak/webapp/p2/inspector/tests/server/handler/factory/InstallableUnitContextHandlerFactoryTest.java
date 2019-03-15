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

import com.avojak.webapp.p2.inspector.server.handler.InstallableUnitHandler;
import com.avojak.webapp.p2.inspector.server.handler.factory.InstallableUnitContextHandlerFactory;

/**
 * Test class for {@link InstallableUnitContextHandlerFactory}.
 */
@RunWith(MockitoJUnitRunner.class)
public class InstallableUnitContextHandlerFactoryTest {

	@Mock
	private InstallableUnitHandler.Factory iuHandlerFactory;

	@Mock
	private InstallableUnitHandler iuHandler;

	@Mock
	private IMetadataRepositoryManager metadataManager;

	@Mock
	private IArtifactRepositoryManager artifactManager;

	/**
	 * Tests that the constructor throws an exception when the given
	 * {@link InstallableUnitHandler.Factory} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullHandlerFactory() {
		new InstallableUnitContextHandlerFactory(null);
	}

	/**
	 * Tests
	 * {@link InstallableUnitContextHandlerFactory#create(IMetadataRepositoryManager, IArtifactRepositoryManager)}.
	 */
	@Test
	public void testCreate() {
		Mockito.when(iuHandlerFactory.create(metadataManager, artifactManager)).thenReturn(iuHandler);

		final ContextHandler handler = new InstallableUnitContextHandlerFactory(iuHandlerFactory)
				.create(metadataManager, artifactManager);

		assertEquals("/repository/iu", handler.getContextPath());
		assertEquals(iuHandler, handler.getHandler());
	}

}
