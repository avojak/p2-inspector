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
 * description.
 */
public class RepositoryDescriptionHandler extends AbstractMetadataRequestHandler {
	
	public static class Factory {
		
		private final Gson gson;
		private final ILog log;
		
		public Factory(final Gson gson, final ILog log) {
			this.gson = gson;
			this.log = log;
		}
		
		public RepositoryDescriptionHandler create(final IMetadataRepositoryManager metadataManager,
				final IArtifactRepositoryManager artifactManager) {
			return new RepositoryDescriptionHandler(metadataManager, artifactManager, gson, log);
		}
		
	}

	public RepositoryDescriptionHandler(final IMetadataRepositoryManager metadataManager,
			final IArtifactRepositoryManager artifactManager, final Gson gson, final ILog log) {
		super(metadataManager, artifactManager, gson, log);
	}

	@Override
	protected void handle(final IMetadataRepository metadataRepository, final IArtifactRepository artifactRepository,
			final PrintWriter out) {
		checkNotNull(metadataManager, "metadataManager cannot be null");
//		checkNotNull(artifactRepository, "artifactRepository cannot be null");
		checkNotNull(out, "out cannot be null");

		out.println(metadataRepository.getDescription());
	}

}
