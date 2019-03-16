package com.avojak.webapp.p2.inspector.server.handler.factory;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.jetty.server.handler.ContextHandler;

import com.avojak.webapp.p2.inspector.server.handler.RepositoryHandler;

public class RepositoryContextHandlerFactory {

	private final RepositoryHandler.Factory handlerFactory;

	/**
	 * Constructor.
	 * 
	 * @param handlerFactory
	 *            The {@link RepositoryHandler.Factory}. Cannot be null.
	 */
	public RepositoryContextHandlerFactory(final RepositoryHandler.Factory handlerFactory) {
		this.handlerFactory = checkNotNull(handlerFactory, "handlerFactory cannot be null");
	}

	/**
	 * Creates and configures the new context handler.
	 * 
	 * @param metadataManager
	 *            The {@link IMetadataRepositoryManager}. Cannot be null.
	 * @param artifactManager
	 *            The {@link IArtifactRepositoryManager}. Cannot be null.
	 * @return The new, non-null {@link ContextHandler}.
	 */
	public ContextHandler create(final IMetadataRepositoryManager metadataManager,
			final IArtifactRepositoryManager artifactManager) {
		final ContextHandler context = new ContextHandler("/repository");
		context.setHandler(handlerFactory.create(metadataManager, artifactManager));
		return context;
	}

}
