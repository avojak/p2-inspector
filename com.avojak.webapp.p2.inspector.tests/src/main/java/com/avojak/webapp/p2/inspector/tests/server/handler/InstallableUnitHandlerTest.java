package com.avojak.webapp.p2.inspector.tests.server.handler;

import static org.junit.Assert.assertNotNull;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.Set;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.query.IQueryResult;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepository;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.avojak.webapp.p2.inspector.server.handler.InstallableUnitHandler;
import com.google.gson.Gson;

/**
 * Test class for {@link InstallableUnitHandler}.
 */
@RunWith(MockitoJUnitRunner.class)
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
	private IQueryResult<IInstallableUnit> result;

	@Mock
	private IInstallableUnit iu;

	/**
	 * Tests that the factory constructor throws an exception when the given
	 * {@link Gson} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testFactoryConstructor_NullGson() {
		new InstallableUnitHandler.Factory(null, log);
	}

	/**
	 * Tests that the factory constructor throws an exception when the given
	 * {@link ILog} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testFactoryConstructor_NullLog() {
		new InstallableUnitHandler.Factory(gson, null);
	}

	/**
	 * Tests that the factory creates a non-null instance of
	 * {@link InstallableUnitHandler}.
	 */
	@Test
	public void testFactoryCreate() {
		assertNotNull(new InstallableUnitHandler.Factory(gson, log).create(metadataManager, artifactManager));
	}

	/**
	 * Tests that
	 * {@link InstallableUnitHandler#handle(IMetadataRepository, IArtifactRepository, PrintWriter)}
	 * throws an exception when the given {@link IMetadataRepositoryManager} is
	 * null.
	 */
	@Test(expected = NullPointerException.class)
	public void testHandle_NullMetadataManager() {
		new InstallableUnitHandler(metadataManager, artifactManager, gson, log).handle(null, artifactRepository,
				printWriter);
	}

	/**
	 * Tests that
	 * {@link InstallableUnitHandler#handle(IMetadataRepository, IArtifactRepository, PrintWriter)}
	 * throws an exception when the given {@link PrintWriter} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testHandle_NullPrintWriter() {
		new InstallableUnitHandler(metadataManager, artifactManager, gson, log).handle(metadataRepository,
				artifactRepository, null);
	}

	/**
	 * Tests
	 * {@link InstallableUnitHandler#handle(IMetadataRepository, IArtifactRepository, PrintWriter)}.
	 */
	@Test
	public void testHandle() {
		Mockito.when(metadataRepository.query(Mockito.eq(QueryUtil.ALL_UNITS), Mockito.isA(NullProgressMonitor.class)))
				.thenReturn(result);
		final Set<IInstallableUnit> resultSet = Collections.singleton(iu);
		Mockito.when(result.toUnmodifiableSet()).thenReturn(resultSet);
		Mockito.when(gson.toJson(resultSet)).thenReturn("mock");

		new InstallableUnitHandler(metadataManager, artifactManager, gson, log).handle(metadataRepository,
				artifactRepository, printWriter);

		Mockito.verify(printWriter).println("mock");
	}

}
