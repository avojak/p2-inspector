package com.avojak.webapp.p2.inspector.tests.server.handler;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletException;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.metadata.ICopyright;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.metadata.ILicense;
import org.eclipse.equinox.p2.metadata.Version;
import org.eclipse.equinox.p2.query.IQuery;
import org.eclipse.equinox.p2.query.IQueryResult;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.jetty.server.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.avojak.webapp.p2.inspector.Activator;
import com.avojak.webapp.p2.inspector.model.IUGroup;
import com.avojak.webapp.p2.inspector.model.License;
import com.avojak.webapp.p2.inspector.model.P2Repository;
import com.avojak.webapp.p2.inspector.server.exception.BadRequestException;
import com.avojak.webapp.p2.inspector.server.handler.RepositoryHandler;
import com.google.gson.Gson;

/**
 * Test class for {@link RepositoryHandler}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RepositoryHandlerTest {

	@Mock
	private IMetadataRepositoryManager metadataManager;

	@Mock
	private IMetadataRepository metadataRepository;

	@Mock
	private IArtifactRepositoryManager artifactManager;

	@Mock
	private Gson gson;

	@Mock
	private ILog log;

	@Mock
	private IQueryResult<IInstallableUnit> queryResult;

	@Mock
	private IInstallableUnit iu;

	@Mock
	private ICopyright copyright;

	@Mock
	private ILicense license;

	@Mock
	private Version version;

	@Mock
	private Request request;

	@Mock
	private PrintWriter printWriter;

	private String locale;
	private URI location;
	private RepositoryHandler handler;

	/**
	 * Setup mocks.
	 * 
	 * @throws MalformedURLException
	 *             Unexpected.
	 * @throws URISyntaxException
	 *             Unexpected.
	 * @throws ProvisionException
	 *             Unexpected.
	 * @throws OperationCanceledException
	 *             Unexpected.
	 */
	@Before
	public void setup()
			throws MalformedURLException, URISyntaxException, ProvisionException, OperationCanceledException {
		locale = Locale.getDefault().toLanguageTag();
		location = new URL("http://example.com?url=http://google.com").toURI();
		Mockito.when(request.getParameter("url")).thenReturn(location.toString());
		Mockito.when(request.getParameter("locale")).thenReturn(locale);
		handler = new RepositoryHandler(metadataManager, artifactManager, gson, log);
	}

	/**
	 * Tests that the constructor throws an exception when the given
	 * {@link IMetadataManager} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullMetadataManager() {
		new RepositoryHandler(null, artifactManager, gson, log);
	}

//	/**
//	 * Tests that the constructor throws an exception when the given
//	 * {@link IArtifactManager} is null.
//	 */
//	@Test(expected = NullPointerException.class)
//	public void testConstructor_NullArtifactManager() {
//		new RepositoryHandler(metadataManager, artifactManager, gson, log);
//	}

	/**
	 * Tests that the constructor throws an exception when the given {@link Gson} is
	 * null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullGson() {
		new RepositoryHandler(metadataManager, artifactManager, null, log);
	}

	/**
	 * Tests that the constructor throws an exception when the given {@link ILog} is
	 * null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullLog() {
		new RepositoryHandler(metadataManager, artifactManager, gson, null);
	}

	/**
	 * Tests that {@link RepositoryHandler#handle(Request, PrintWriter)} throws an
	 * exception when the URL request parameter is null.
	 * 
	 * @throws ServletException
	 *             Unexpected.
	 */
	@Test
	public void testHandle_NullUrlParameter() throws ServletException {
		Mockito.when(request.getParameter("url")).thenReturn(null);
		try {
			handler.handle(request, printWriter);
		} catch (final BadRequestException e) {
			assertEquals("url parameter cannot be null or empty", e.getCause().getMessage());
		}
	}

	/**
	 * Tests that {@link RepositoryHandler#handle(Request, PrintWriter)} throws an
	 * exception when the URL request parameter is empty.
	 * 
	 * @throws ServletException
	 *             Unexpected.
	 */
	@Test
	public void testHandle_EmptyUrlParameter() throws ServletException {
		Mockito.when(request.getParameter("url")).thenReturn("");
		try {
			handler.handle(request, printWriter);
		} catch (final BadRequestException e) {
			assertEquals("url parameter cannot be null or empty", e.getCause().getMessage());
		}
	}

	/**
	 * Tests that {@link RepositoryHandler#handle(Request, PrintWriter)} throws an
	 * exception when the URL request parameter is an invalid URL.
	 * 
	 * @throws ServletException
	 *             Unexpected.
	 */
	@Test
	public void testHandle_InvalidUrlParameter() throws ServletException {
		Mockito.when(request.getParameter("url")).thenReturn("invalid");
		try {
			handler.handle(request, printWriter);
		} catch (final BadRequestException e) {
			assertEquals("no protocol: invalid", e.getCause().getCause().getMessage());
		}
	}

	/**
	 * Tests that {@link RepositoryHandler#handle(Request, PrintWriter)} throws an
	 * exception when the repository cannot be created.
	 * 
	 * @throws MalformedURLException
	 *             Unexpected.
	 * @throws URISyntaxException
	 *             Unexpected.
	 * @throws ProvisionException
	 *             Unexpected.
	 * @throws OperationCanceledException
	 *             Unexpected.
	 * @throws BadRequestException
	 *             Unexpected.
	 */
	@Test
	public void testHandle_ProvisionFailed() throws MalformedURLException, URISyntaxException, ProvisionException,
			OperationCanceledException, BadRequestException {
		Mockito.when(metadataManager.loadRepository(Mockito.eq(location), Mockito.isA(NullProgressMonitor.class)))
				.thenThrow(ProvisionException.class);

		try {
			handler.handle(request, printWriter);
		} catch (final ServletException e) {
			Mockito.verify(log).log(Mockito.refEq(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"Failed to load repo: " + location.toString(), e.getCause())));
		}
	}

	/**
	 * Tests that {@link RepositoryHandler#handle(Request, PrintWriter)} throws an
	 * exception if the load operation is canceled.
	 * 
	 * @throws BadRequestException
	 *             Unexpected.
	 * @throws ProvisionException
	 *             Unexpected.
	 * @throws OperationCanceledException
	 *             Unexpected.
	 */
	@Test
	public void testHandle_OperationCanceled()
			throws BadRequestException, ProvisionException, OperationCanceledException {
		Mockito.when(metadataManager.loadRepository(Mockito.eq(location), Mockito.isA(NullProgressMonitor.class)))
				.thenThrow(OperationCanceledException.class);

		try {
			handler.handle(request, printWriter);
		} catch (final ServletException e) {
			Mockito.verify(log).log(Mockito.refEq(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"Failed to load repo: " + location.toString(), e.getCause())));
		}
	}

	/**
	 * Tests {@link RepositoryHandler#handle(Request, PrintWriter)}.
	 * 
	 * @throws ServletException
	 *             Unexpected.
	 * @throws BadRequestException
	 *             Unexpected.
	 * @throws ProvisionException
	 *             Unexpected.
	 * @throws OperationCanceledException
	 *             Unexpected.
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testHandle()
			throws ServletException, BadRequestException, ProvisionException, OperationCanceledException {
		final P2Repository expected = new P2Repository("Mock Repository Name", location, true, 1552760508418L,
				Arrays.asList(new IUGroup("Mock IU Name", "com.example.mock.feature.group", "Mock IU Description",
						"Mock Copyright Body",
						Arrays.asList(new License("Mock License",
								"Mock License" + System.lineSeparator() + "Mock License Body",
								URI.create("www.example.com/mock-license"))),
						"1.0.0")));

		Mockito.when(metadataManager.loadRepository(Mockito.refEq(location), Mockito.refEq(new NullProgressMonitor())))
				.thenReturn(metadataRepository);
		Mockito.when(metadataRepository.getName()).thenReturn("Mock Repository Name");
		Mockito.when(metadataRepository.getProperty(IMetadataRepository.PROP_COMPRESSED)).thenReturn("true");
		Mockito.when(metadataRepository.getProperty(IMetadataRepository.PROP_TIMESTAMP)).thenReturn("1552760508418");
		Mockito.when(metadataRepository.query((IQuery<IInstallableUnit>) Mockito.isA(IQuery.class),
				Mockito.isA(NullProgressMonitor.class))).thenReturn(queryResult);
		final Set<IInstallableUnit> queryResultSet = Collections.singleton(iu);
		Mockito.when(queryResult.toUnmodifiableSet()).thenReturn(queryResultSet);
		Mockito.when(iu.getProperty(IInstallableUnit.PROP_NAME, locale)).thenReturn("Mock IU Name");
		Mockito.when(iu.getId()).thenReturn("com.example.mock.feature.group");
		Mockito.when(iu.getProperty(IInstallableUnit.PROP_DESCRIPTION, locale)).thenReturn("Mock IU Description");
		Mockito.when(iu.getCopyright(locale)).thenReturn(copyright);
		Mockito.when(copyright.getBody()).thenReturn("Mock Copyright Body");
		Mockito.when(iu.getVersion()).thenReturn(version);
		Mockito.when(version.toString()).thenReturn("1.0.0");
		Mockito.when(iu.getLicenses(locale)).thenReturn(Arrays.asList(license));
		Mockito.when(license.getBody()).thenReturn("Mock License" + System.lineSeparator() + "Mock License Body");
		Mockito.when(license.getLocation()).thenReturn(URI.create("www.example.com/mock-license"));
		Mockito.when(gson.toJson(Mockito.eq(expected))).thenReturn("{\"mock\"}");

		handler.handle(request, printWriter);

		Mockito.verify(metadataManager).addRepository(location);
		Mockito.verify(printWriter).println("{\"mock\"}");
	}

}
