package com.avojak.webapp.p2.inspector.server.handler;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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

import com.avojak.webapp.p2.inspector.Activator;
import com.avojak.webapp.p2.inspector.server.exception.BadRequestException;
import com.google.gson.Gson;

/**
 * Base request handler for metadata requests.
 */
public abstract class AbstractMetadataRequestHandler extends AbstractRequestHandler {

	protected final IMetadataRepositoryManager metadataManager;
//	protected final IArtifactRepositoryManager artifactManager;
	protected final Gson gson;

	/**
	 * Constructor.
	 *
	 * @param metadataManager The {@link IMetadataRepositoryManager}. Cannot be
	 *                        null.
	 * @param artifactManager The {@link IArtifactRepositoryManager}. Cannot be
	 *                        null.
	 * @param gson            The {@link Gson}. Cannot be null.
	 */
	protected AbstractMetadataRequestHandler(final IMetadataRepositoryManager metadataManager,
			final IArtifactRepositoryManager artifactManager, final Gson gson, final ILog log) {
		super(log);
		this.metadataManager = checkNotNull(metadataManager, "metadataManager cannot be null");
//		this.artifactManager = checkNotNull(artifactManager, "artifactManager cannot be null");
		this.gson = checkNotNull(gson, "gson cannot be null");
	}

	@Override
	public void handle(final Request baseRequest, final PrintWriter out) throws ServletException, BadRequestException {

		final URI repo = getRepositoryURI(baseRequest);

		metadataManager.addRepository(repo);
//		artifactManager.addRepository(repo);

		final IMetadataRepository metadataRepository;
//		final IArtifactRepository artifactRepository;
		try {
			metadataRepository = metadataManager.loadRepository(repo, new NullProgressMonitor());
//			artifactRepository = artifactManager.loadRepository(repo, new NullProgressMonitor());
		} catch (final ProvisionException | OperationCanceledException e) {
			log.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to load repository: " + repo.toString(), e));
			throw new ServletException(e);
		}

		handle(metadataRepository, null, out);

	}

	/**
	 * Handles the request.
	 *
	 * @param metadataRepository The {@link IMetadataRepository} for the requested
	 *                           repository. Cannot be null.
	 * @param artifactRepository The {@link IArtifactRepository} for the requested
	 *                           repository. Cannot be null.
	 * @param out                The HTTP response {@link PrintWriter}. Cannot be
	 *                           null.
	 */
	protected abstract void handle(final IMetadataRepository metadataRepository,
			final IArtifactRepository artifactRepository, final PrintWriter out);

	/**
	 * Gets the repository URI from the {@code url} request parameter.
	 *
	 * @param baseRequest The {@link Request}. Cannot be null.
	 * @return The non-null {@link URI}.
	 * @throws IOException      If an exception occurs while reading the request.
	 * @throws ServletException If an exception occurs while reading the request.
	 */
	private URI getRepositoryURI(final Request baseRequest) throws BadRequestException {
		checkNotNull(baseRequest, "baseRequest cannot be null");
		final String urlParameter = baseRequest.getParameter("url");
		if (urlParameter == null || urlParameter.isEmpty()) {
			throw new BadRequestException(new IllegalArgumentException("url parameter cannot be null or empty"));
		}
		try {
			return new URL(URLDecoder.decode(urlParameter, StandardCharsets.UTF_8.name())).toURI();
		} catch (final URISyntaxException | MalformedURLException | UnsupportedEncodingException e) {
			throw new BadRequestException(new IllegalArgumentException(e));
		}
	}

}
