package com.avojak.webapp.p2.inspector.servlet;

import java.io.PrintWriter;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepository;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;

import com.avojak.webapp.p2.inspector.server.handler.AbstractRequestHandler;
import com.google.gson.Gson;

public class RootHandler extends AbstractRequestHandler {

	public RootHandler(final IMetadataRepositoryManager metadataManager,
			final IArtifactRepositoryManager artifactManager, final Gson gson) {
		super(metadataManager, artifactManager, gson);
	}

	@Override
	protected void handle(final IMetadataRepository metadataRepository, final IArtifactRepository artifactRepository,
			final PrintWriter out) {
		// TODO Auto-generated method stub
	}

}
