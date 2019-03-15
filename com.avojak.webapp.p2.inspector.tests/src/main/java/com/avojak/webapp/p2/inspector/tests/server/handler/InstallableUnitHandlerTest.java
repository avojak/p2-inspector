package com.avojak.webapp.p2.inspector.tests.server.handler;

import static org.junit.Assert.assertNotNull;

import java.io.PrintWriter;

import org.eclipse.core.runtime.ILog;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepository;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.junit.Test;
import org.mockito.Mock;

import com.avojak.webapp.p2.inspector.server.handler.InstallableUnitHandler;
import com.google.gson.Gson;

/**
 * Test class for {@link InstallableUnitHandler}.
 */
public class InstallableUnitHandlerTest {

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

	@Mock
	private IInstallableUnit iu;

	@Test(expected = NullPointerException.class)
	public void testFactoryConstructor_NullGson() {
		new InstallableUnitHandler.Factory(null, log);
	}

	@Test(expected = NullPointerException.class)
	public void testFactoryConstructor_NullLog() {
		new InstallableUnitHandler.Factory(gson, null);
	}

	@Test
	public void testFactoryCreate() {
		assertNotNull(new InstallableUnitHandler.Factory(gson, log).create(metadataManager, artifactManager));
	}

	@Test(expected = NullPointerException.class)
	public void testHandle_NullMetadataManager() {
		new InstallableUnitHandler(metadataManager, artifactManager, gson, log).handle(null, artifactRepository,
				printWriter);
	}

	@Test(expected = NullPointerException.class)
	public void testHandle_NullPrintWriter() {
		new InstallableUnitHandler(metadataManager, artifactManager, gson, log).handle(metadataRepository,
				artifactRepository, null);
	}

	@Test
	public void testHandle() {
		new InstallableUnitHandler(metadataManager, artifactManager, gson, log).handle(metadataRepository,
				artifactRepository, printWriter);
		// TODO
	}

}
