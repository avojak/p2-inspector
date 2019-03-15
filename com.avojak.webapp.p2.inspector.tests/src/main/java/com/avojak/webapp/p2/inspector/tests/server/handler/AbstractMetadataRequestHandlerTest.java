package com.avojak.webapp.p2.inspector.tests.server.handler;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.ServletException;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepository;
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
import com.avojak.webapp.p2.inspector.server.exception.BadRequestException;
import com.avojak.webapp.p2.inspector.server.handler.AbstractMetadataRequestHandler;
import com.google.gson.Gson;

/**
 * Test class for {@link AbstractMetadataRequestHandler}.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractMetadataRequestHandlerTest {

	/**
	 * Test implementation of {@link AbstractMetadataRequestHandler}.
	 */
	private static class TestAbstractMetadataRequestHandler extends AbstractMetadataRequestHandler {

		protected TestAbstractMetadataRequestHandler(IMetadataRepositoryManager metadataManager,
				IArtifactRepositoryManager artifactManager, Gson gson, ILog log) {
			super(metadataManager, artifactManager, gson, log);
		}

		@Override
		protected void handle(IMetadataRepository metadataRepository, IArtifactRepository artifactRepository,
				PrintWriter out) {
			// Do nothing
		}

	}

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
	private Request request;

	@Mock
	private PrintWriter printWriter;

	private URI location;
	private AbstractMetadataRequestHandler handler;

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
		location = new URL("http://example.com?url=http://google.com").toURI();
		Mockito.when(request.getParameter("url")).thenReturn(location.toString());
		handler = new TestAbstractMetadataRequestHandler(metadataManager, artifactManager, gson, log);
	}

	/**
	 * Tests that the constructor throws an exception when the given
	 * {@link IMetadataManager} is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullMetadataManager() {
		new TestAbstractMetadataRequestHandler(null, artifactManager, gson, log);
	}

//	/**
//	 * Tests that the constructor throws an exception when the given
//	 * {@link IArtifactManager} is null.
//	 */
//	@Test(expected = NullPointerException.class)
//	public void testConstructor_NullArtifactManager() {
//		new TestAbstractMetadataRequestHandler(metadataManager, artifactManager, gson, log);
//	}

	/**
	 * Tests that the constructor throws an exception when the given {@link Gson} is
	 * null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullGson() {
		new TestAbstractMetadataRequestHandler(metadataManager, artifactManager, null, log);
	}

	/**
	 * Tests that the constructor throws an exception when the given {@link ILog} is
	 * null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullLog() {
		new TestAbstractMetadataRequestHandler(metadataManager, artifactManager, gson, null);
	}

	/**
	 * Tests that
	 * {@link AbstractMetadataRequestHandler#handle(Request, PrintWriter)} throws an
	 * exception when the given {@link Request} is null.
	 * 
	 * @throws ServletException
	 *             Unexpected.
	 * @throws BadRequestException
	 *             Unexpected.
	 */
	@Test(expected = NullPointerException.class)
	public void testHandle_NullBaseRequest() throws ServletException, BadRequestException {
		handler.handle(null, printWriter);
	}

	/**
	 * Tests that
	 * {@link AbstractMetadataRequestHandler#handle(Request, PrintWriter)} throws an
	 * exception when the given {@link PrintWriter} is null.
	 * 
	 * @throws ServletException
	 *             Unexpected.
	 * @throws BadRequestException
	 *             Unexpected.
	 */
	@Test(expected = NullPointerException.class)
	public void testHandle_NullPrintWriter() throws ServletException, BadRequestException {
		handler.handle(request, null);
	}

	/**
	 * Tests that
	 * {@link AbstractMetadataRequestHandler#handle(Request, PrintWriter)} throws an
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
	 * Tests that
	 * {@link AbstractMetadataRequestHandler#handle(Request, PrintWriter)} throws an
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
	 * Tests that
	 * {@link AbstractMetadataRequestHandler#handle(Request, PrintWriter)} throws an
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
	 * Tests that
	 * {@link AbstractMetadataRequestHandler#handle(Request, PrintWriter)} throws an
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
					"Failed to load repository: " + location.toString(), e.getCause())));
		}
	}

	/**
	 * Tests that
	 * {@link AbstractMetadataRequestHandler#handle(Request, PrintWriter)} throws an
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
					"Failed to load repository: " + location.toString(), e.getCause())));
		}
	}

}
