package com.avojak.webapp.p2.inspector.server.handler;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.PrintWriter;

import org.eclipse.core.runtime.ILog;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepository;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;

import com.google.gson.Gson;

/**
 * Implementation of {@link AbstractMetadataRequestHandler} to retrieve the repository
 * name.
 */
public class RepositoryNameHandler extends AbstractMetadataRequestHandler {
	
	/**
	 * Factory class to create instances of {@link RepositoryNameHandler}.
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
		 * Creates a new instance of {@link RepositoryNameHandler}.
		 * 
		 * @param metadataManager
		 *            The {@link IMetadataRepositoryManager}. Cannot be null.
		 * @param artifactManager
		 *            The {@link IArtifactRepositoryManager}. Cannot be null.
		 * @return The new, non-null {@link RepositoryNameHandler}.
		 */
		public RepositoryNameHandler create(final IMetadataRepositoryManager metadataManager,
				final IArtifactRepositoryManager artifactManager) {
			return new RepositoryNameHandler(metadataManager, artifactManager, gson, log);
		}
		
	}

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
	public RepositoryNameHandler(final IMetadataRepositoryManager metadataManager,
			final IArtifactRepositoryManager artifactManager, final Gson gson, final ILog log) {
		super(metadataManager, artifactManager, gson, log);
	}

	@Override
	public void handle(final IMetadataRepository metadataRepository, final IArtifactRepository artifactRepository,
			final PrintWriter out) {
		checkNotNull(metadataManager, "metadataManager cannot be null");
//		checkNotNull(artifactRepository, "artifactRepository cannot be null");
		checkNotNull(out, "out cannot be null");

		out.println(metadataRepository.getName());
	}

}
