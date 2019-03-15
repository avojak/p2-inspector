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

import com.avojak.webapp.p2.inspector.server.handler.RepositoryNameHandler;
import com.google.gson.Gson;

/**
 * Test class for {@link RepositoryNameHandler}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RepositoryNameHandlerTest {

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
		new RepositoryNameHandler.Factory(null, log);
	}

	/**
	 * Tests that the factory constructor throws an exception when the given
	 * {@link ILog} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testFactoryConstructor_NullLog() {
		new RepositoryNameHandler.Factory(gson, null);
	}

	/**
	 * Tests that the factory creates a non-null instance of
	 * {@link RepositoryNameHandler}.
	 */
	@Test
	public void testFactoryCreate() {
		assertNotNull(new RepositoryNameHandler.Factory(gson, log).create(metadataManager, artifactManager));
	}

	/**
	 * Tests that
	 * {@link RepositoryNameHandler#handle(IMetadataRepository, IArtifactRepository, PrintWriter)}
	 * throws an exception when the given {@link IMetadataRepositoryManager} is
	 * null.
	 */
	@Test(expected = NullPointerException.class)
	public void testHandle_NullMetadataManager() {
		new RepositoryNameHandler(metadataManager, artifactManager, gson, log).handle(null, artifactRepository,
				printWriter);
	}

	/**
	 * Tests that
	 * {@link RepositoryNameHandler#handle(IMetadataRepository, IArtifactRepository, PrintWriter)}
	 * throws an exception when the given {@link PrintWriter} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testHandle_NullPrintWriter() {
		new RepositoryNameHandler(metadataManager, artifactManager, gson, log).handle(metadataRepository,
				artifactRepository, null);
	}

	/**
	 * Tests
	 * {@link RepositoryNameHandler#handle(IMetadataRepository, IArtifactRepository, PrintWriter)}.
	 */
	@Test
	public void testHandle() {
		Mockito.when(metadataRepository.getName()).thenReturn("Mock name");

		new RepositoryNameHandler(metadataManager, artifactManager, gson, log).handle(metadataRepository,
				artifactRepository, printWriter);

		Mockito.verify(printWriter).println("Mock name");
	}

}
