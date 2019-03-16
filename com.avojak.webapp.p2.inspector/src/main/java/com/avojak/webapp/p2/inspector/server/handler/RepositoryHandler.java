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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletException;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.metadata.ILicense;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.jetty.server.Request;

import com.avojak.webapp.p2.inspector.Activator;
import com.avojak.webapp.p2.inspector.model.IUGroup;
import com.avojak.webapp.p2.inspector.model.License;
import com.avojak.webapp.p2.inspector.model.P2Repository;
import com.avojak.webapp.p2.inspector.server.exception.BadRequestException;
import com.google.gson.Gson;

/**
 * Implementation of {@link AbstractRequestHandler} to retrieve repository
 * details.
 */
public class RepositoryHandler extends AbstractRequestHandler {

	/**
	 * Factory class to create instances of {@link RepositoryHandler}.
	 */
	public static class Factory {

		private final Gson gson;
		private final ILog log;

		/**
		 * Constructor.
		 * 
		 * @param gson
		 *            The {@link Gson}. Cannot be null.
		 * @param log
		 *            The {@link ILog}. Cannot be null.
		 */
		public Factory(final Gson gson, final ILog log) {
			this.gson = checkNotNull(gson, "gson cannot be null");
			this.log = checkNotNull(log, "log cannot be null");
		}

		/**
		 * Creates a new instance of {@link RepositoryHandler}.
		 * 
		 * @param metadataManager
		 *            The {@link IMetadataRepositoryManager}. Cannot be null.
		 * @param artifactManager
		 *            The {@link IArtifactRepositoryManager}. Cannot be null.
		 * @return The new, non-null {@link RepositoryHandler}.
		 */
		public RepositoryHandler create(final IMetadataRepositoryManager metadataManager,
				final IArtifactRepositoryManager artifactManager) {
			return new RepositoryHandler(metadataManager, artifactManager, gson, log);
		}

	}

	private final IMetadataRepositoryManager metadataManager;
//	private final IArtifactRepositoryManager artifactManager;
	private final Gson gson;

	/**
	 * Constructor.
	 * 
	 * @param metadataManager
	 *            The {@link IMetadataRepositoryManager}. Cannot be null.
	 * @param artifactManager
	 *            The {@link IArtifactRepositoryManager}. Cannot be null.
	 * @param gson
	 *            The {@link Gson}. Cannot be null.
	 * @param log
	 *            The {@link ILog}. Cannot be null.
	 */
	public RepositoryHandler(final IMetadataRepositoryManager metadataManager,
			final IArtifactRepositoryManager artifactManager, final Gson gson, final ILog log) {
		super(log);
		this.metadataManager = checkNotNull(metadataManager, "metadataManager cannot be null");
//		this.artifactManager = checkNotNull(artifactManager, "artifactManager cannot be null");
		this.gson = checkNotNull(gson, "gson cannot be null");
	}

	@Override
	public void handle(final Request baseRequest, final PrintWriter out) throws ServletException, BadRequestException {
		checkNotNull(baseRequest, "baseRequest cannot be null");
		checkNotNull(out, "out cannot be null");

		// Parse the request parameters
		final URI location = getRepositoryURI(baseRequest);
		final String locale = getLocale(baseRequest).toLanguageTag();

		// Load the repository
		metadataManager.addRepository(location);
//		artifactManager.addRepository(location);

		final IMetadataRepository metadataRepository;
//		final IArtifactRepository artifactRepository;
		try {
			metadataRepository = metadataManager.loadRepository(location, new NullProgressMonitor());
//			artifactRepository = artifactManager.loadRepository(location, new NullProgressMonitor());
		} catch (final ProvisionException | OperationCanceledException e) {
			log.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to load repo: " + location.toString(), e));
			throw new ServletException(e);
		}

		final String repositoryName = metadataRepository.getName();
		final String isCompressed = metadataRepository.getProperty(IMetadataRepository.PROP_COMPRESSED);
		final String lastModified = metadataRepository.getProperty(IMetadataRepository.PROP_TIMESTAMP);

		// Query for the IU groups
		final Set<IInstallableUnit> queryResult = metadataRepository
				.query(QueryUtil.createIUGroupQuery(), new NullProgressMonitor()).toUnmodifiableSet();

		// Convert the query result into a P2Repository model
		final List<IUGroup> groups = new ArrayList<>();
		for (final IInstallableUnit iu : queryResult) {
			final String name = iu.getProperty(IInstallableUnit.PROP_NAME, locale);
			final String id = iu.getId();
			final String description = iu.getProperty(IInstallableUnit.PROP_DESCRIPTION, locale);
			final String copyright = iu.getCopyright(locale).getBody();
			final String version = iu.getVersion().toString();
			final List<License> licenses = new ArrayList<>();
			for (final ILicense license : iu.getLicenses(locale)) {
				final String licenseName = license.getBody().split(System.lineSeparator())[0];
				licenses.add(new License(licenseName, license.getBody(), license.getLocation()));
			}
			groups.add(new IUGroup(name, id, description, copyright, licenses, version));
		}
		final P2Repository p2Repository = new P2Repository(repositoryName, location, Boolean.valueOf(isCompressed),
				Long.valueOf(lastModified), groups);

		out.println(gson.toJson(p2Repository));
	}

	private URI getRepositoryURI(final Request request) throws BadRequestException {
		final String urlParameter = request.getParameter("url");
		if (urlParameter == null || urlParameter.isEmpty()) {
			throw new BadRequestException(new IllegalArgumentException("url parameter cannot be null or empty"));
		}
		try {
			return new URL(URLDecoder.decode(urlParameter, StandardCharsets.UTF_8.name())).toURI();
		} catch (final URISyntaxException | MalformedURLException | UnsupportedEncodingException e) {
			throw new BadRequestException(new IllegalArgumentException(e));
		}
	}

	private Locale getLocale(final Request request) {
		final String localeParameter = request.getParameter("locale");
		if (localeParameter == null || localeParameter.isEmpty()) {
			return Locale.getDefault();
		}
		return Locale.forLanguageTag(localeParameter);
	}

}
