package com.avojak.webapp.p2.inspector.server.factory;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import com.avojak.webapp.p2.inspector.Activator;
import com.avojak.webapp.p2.inspector.osgi.ProvisioningAgentProvider;
import com.avojak.webapp.p2.inspector.server.handler.factory.RepositoryContextHandlerFactory;
import com.avojak.webapp.p2.inspector.server.handler.factory.RootContextHandlerFactory;

/**
 * Factory class to create and configure the connection handler.
 */
public class HandlerFactory {

	private final ProvisioningAgentProvider agentProvider;
	private final RootContextHandlerFactory rootContextHandlerFactory;
	private final RepositoryContextHandlerFactory repositoryContextHandlerFactory;
	private final ILog log;

	/**
	 * Constructor.
	 * 
	 * @param agentProvider
	 *            The {@link ProvisioningAgentProvider}. Cannot be null.
	 * @param rootContextHandlerFactory
	 *            The {@link RootContextHandlerFactory}. Cannot be null.
	 * @param repositoryContextHandlerFactory
	 *            The {@link RepositoryContextHandlerFactory}. Cannot be null.
	 * @param log
	 *            The {@link ILog}. Cannot be null.
	 */
	public HandlerFactory(final ProvisioningAgentProvider agentProvider,
			final RootContextHandlerFactory rootContextHandlerFactory,
			final RepositoryContextHandlerFactory repositoryContextHandlerFactory, final ILog log) {
		this.agentProvider = checkNotNull(agentProvider, "agentProvider cannot be null");
		this.rootContextHandlerFactory = checkNotNull(rootContextHandlerFactory,
				"rootContextHandlerFactory cannot be null");
		this.repositoryContextHandlerFactory = checkNotNull(repositoryContextHandlerFactory,
				"repositoryContextHandlerFactory cannot be null");
		this.log = checkNotNull(log, "log cannot be null");
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
		final IArtifactRepositoryManager artifactManager;
		try {
			artifactManager = (IArtifactRepositoryManager) agentProvider.getAgent(null)
					.getService(IArtifactRepositoryManager.SERVICE_NAME);
		} catch (final ProvisionException e) {
			throw new RuntimeException(e);
		}

		if (metadataManager == null || artifactManager == null) {
			log.log(new Status(Status.WARNING, Activator.PLUGIN_ID, "metadataManager or artifactManager is null"));
		}

		final ContextHandler rootContext = rootContextHandlerFactory.create();
		final ContextHandler repositoryContext = repositoryContextHandlerFactory.create(metadataManager,
				artifactManager);

		final Handler[] handlers = new Handler[] { rootContext, repositoryContext };

		final ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(handlers);
		return contexts;
	}

}
