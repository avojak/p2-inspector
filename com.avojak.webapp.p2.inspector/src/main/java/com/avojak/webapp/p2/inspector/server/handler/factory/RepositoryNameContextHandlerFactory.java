package com.avojak.webapp.p2.inspector.server.handler.factory;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.jetty.server.handler.ContextHandler;

import com.avojak.webapp.p2.inspector.server.handler.RepositoryNameHandler;

public class RepositoryNameContextHandlerFactory {
	
	private final RepositoryNameHandler.Factory handlerFactory;

	public RepositoryNameContextHandlerFactory(final RepositoryNameHandler.Factory handlerFactory) {
		this.handlerFactory = checkNotNull(handlerFactory, "handlerFactory cannot be null");
	}

	public ContextHandler create(final IMetadataRepositoryManager metadataManager,
			final IArtifactRepositoryManager artifactManager) {
		final ContextHandler context = new ContextHandler("/repository/name");
		context.setHandler(handlerFactory.create(metadataManager, artifactManager));
		return context;
	}

}
