package com.avojak.webapp.p2.inspector.server.factory;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import com.avojak.webapp.p2.inspector.osgi.ProvisioningAgentProvider;
import com.avojak.webapp.p2.inspector.server.handler.factory.InstallableUnitContextHandlerFactory;
import com.avojak.webapp.p2.inspector.server.handler.factory.RepositoryDescriptionContextHandlerFactory;
import com.avojak.webapp.p2.inspector.server.handler.factory.RepositoryNameContextHandlerFactory;
import com.avojak.webapp.p2.inspector.server.handler.factory.RootContextHandlerFactory;

/**
 * Factory class to create and configure the connection handler.
 */
public class HandlerFactory {

	private final ProvisioningAgentProvider agentProvider;
	private final RootContextHandlerFactory rootContextHandlerFactory;
	private final RepositoryNameContextHandlerFactory repositoryNameContextHandlerFactory;
	private final RepositoryDescriptionContextHandlerFactory repositoryDescriptionContextHandlerFactory;
	private final InstallableUnitContextHandlerFactory installableUnitContextHandlerFactory;

	/**
	 * Constructor.
	 * 
	 * @param agentProvider
	 *            The {@link ProvisioningAgentProvider}. Cannot be null.
	 * @param rootContextHandlerFactory
	 *            The {@link RootContextHandlerFactory}. Cannot be null.
	 * @param repositoryNameContextHandlerFactory
	 *            The {@link RepositoryNameContextHandlerFactory}. Cannot be null.
	 * @param repositoryDescriptionContextHandlerFactory
	 *            The {@link RepositoryDescriptionContextHandlerFactory}. Cannot be
	 *            null.
	 * @param installableUnitContextHandlerFactory
	 *            The {@link InstallableUnitContextHandlerFactory}. Cannot be null.
	 */
	public HandlerFactory(final ProvisioningAgentProvider agentProvider,
			final RootContextHandlerFactory rootContextHandlerFactory,
			final RepositoryNameContextHandlerFactory repositoryNameContextHandlerFactory,
			final RepositoryDescriptionContextHandlerFactory repositoryDescriptionContextHandlerFactory,
			final InstallableUnitContextHandlerFactory installableUnitContextHandlerFactory) {
		this.agentProvider = checkNotNull(agentProvider, "agentProvider cannot be null");
		this.rootContextHandlerFactory = checkNotNull(rootContextHandlerFactory,
				"rootContextHandlerFactory cannot be null");
		this.repositoryNameContextHandlerFactory = checkNotNull(repositoryNameContextHandlerFactory,
				"repositoryNameContextHandlerFactory cannot be null");
		this.repositoryDescriptionContextHandlerFactory = checkNotNull(repositoryDescriptionContextHandlerFactory,
				"repositoryDescriptionContextHandlerFactory cannot be null");
		this.installableUnitContextHandlerFactory = checkNotNull(installableUnitContextHandlerFactory,
				"installableUnitContextHandlerFactory cannot be null");
	}

	/**
	 * Creates the handler.
	 * 
	 * @return The non-null {@link Handler}.
	 */
	public Handler create() {
		final IMetadataRepositoryManager metadataManager;
		try {
			metadataManager = (IMetadataRepositoryManager) agentProvider.getAgent(null)
					.getService(IMetadataRepositoryManager.SERVICE_NAME);
		} catch (final ProvisionException e) {
			throw new RuntimeException(e);
		}
		final IArtifactRepositoryManager artifactManager = null;

		final ContextHandler rootContext = rootContextHandlerFactory.create();
		final ContextHandler nameContext = repositoryNameContextHandlerFactory.create(metadataManager, artifactManager);
		final ContextHandler descriptionContext = repositoryDescriptionContextHandlerFactory.create(metadataManager,
				artifactManager);
		final ContextHandler installableUnitContext = installableUnitContextHandlerFactory.create(metadataManager,
				artifactManager);

		final Handler[] handlers = new Handler[] { rootContext, nameContext, descriptionContext,
				installableUnitContext };

		final ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(handlers);
		return contexts;
	}

}
