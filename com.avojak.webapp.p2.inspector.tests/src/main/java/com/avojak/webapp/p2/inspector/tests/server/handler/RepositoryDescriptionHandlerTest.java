package com.avojak.webapp.p2.inspector.tests.server.handler;

import static org.junit.Assert.assertNotNull;

import java.io.PrintWriter;

import org.eclipse.core.runtime.ILog;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepository;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.avojak.webapp.p2.inspector.server.handler.RepositoryDescriptionHandler;
import com.google.gson.Gson;

/**
 * Test class for {@link RepositoryDescriptionHandler}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RepositoryDescriptionHandlerTest {

	@Mock
	private Gson gson;

	@Mock
	private ILog log;

	@Mock
	private IMetadataRepositoryManager metadataManager;

	@Mock
	private IMetadataRepository metadataRepository;

	@Mock
	private IArtifactRepositoryManager artifactManager;

	@Mock
	private IArtifactRepository artifactRepository;

	@Mock
	private PrintWriter printWriter;

	/**
	 * Tests that the factory constructor throws an exception when the given
	 * {@link Gson} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testFactoryConstructor_NullGson() {
		new RepositoryDescriptionHandler.Factory(null, log);
	}

	/**
	 * Tests that the factory constructor throws an exception when the given
	 * {@link ILog} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testFactoryConstructor_NullLog() {
		new RepositoryDescriptionHandler.Factory(gson, null);
	}

	/**
	 * Tests that the factory creates a non-null instance of
	 * {@link RepositoryDescriptionHandler}.
	 */
	@Test
	public void testFactoryCreate() {
		assertNotNull(new RepositoryDescriptionHandler.Factory(gson, log).create(metadataManager, artifactManager));
	}

	/**
	 * Tests that
	 * {@link RepositoryDescriptionHandler#handle(IMetadataRepository, IArtifactRepository, PrintWriter)}
	 * throws an exception when the given {@link IMetadataRepositoryManager} is
	 * null.
	 */
	@Test(expected = NullPointerException.class)
	public void testHandle_NullMetadataManager() {
		new RepositoryDescriptionHandler(metadataManager, artifactManager, gson, log).handle(null, artifactRepository,
				printWriter);
	}

	/**
	 * Tests that
	 * {@link RepositoryDescriptionHandler#handle(IMetadataRepository, IArtifactRepository, PrintWriter)}
	 * throws an exception when the given {@link PrintWriter} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testHandle_NullPrintWriter() {
		new RepositoryDescriptionHandler(metadataManager, artifactManager, gson, log).handle(metadataRepository,
				artifactRepository, null);
	}

	/**
	 * Tests
	 * {@link RepositoryDescriptionHandler#handle(IMetadataRepository, IArtifactRepository, PrintWriter)}.
	 */
	@Test
	public void testHandle() {
		Mockito.when(metadataRepository.getDescription()).thenReturn("Mock description");
		
		new RepositoryDescriptionHandler(metadataManager, artifactManager, gson, log).handle(metadataRepository,
				artifactRepository, printWriter);
		
		Mockito.verify(printWriter).println("Mock description");
	}

}
