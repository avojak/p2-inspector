package com.avojak.webapp.p2.inspector.server.handler.factory;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.jetty.server.handler.ContextHandler;

import com.avojak.webapp.p2.inspector.server.handler.RepositoryDescriptionHandler;

public class RepositoryDescriptionContextHandlerFactory {
	
	private final RepositoryDescriptionHandler.Factory handlerFactory;

	public RepositoryDescriptionContextHandlerFactory(final RepositoryDescriptionHandler.Factory handlerFactory) {
		this.handlerFactory = checkNotNull(handlerFactory, "handlerFactory cannot be null");
	}

	public ContextHandler create(final IMetadataRepositoryManager metadataManager,
			final IArtifactRepositoryManager artifactManager) {
		final ContextHandler context = new ContextHandler("/repository/description");
		context.setHandler(handlerFactory.create(metadataManager, artifactManager));
		return context;
	}

}
