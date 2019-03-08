package com.avojak.webapp.p2.inspector.tests.server.factory;

import static org.junit.Assert.assertEquals;

import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.avojak.webapp.p2.inspector.osgi.ProvisioningAgentProvider;
import com.avojak.webapp.p2.inspector.server.factory.HandlerFactory;
import com.avojak.webapp.p2.inspector.server.handler.factory.InstallableUnitContextHandlerFactory;
import com.avojak.webapp.p2.inspector.server.handler.factory.RepositoryDescriptionContextHandlerFactory;
import com.avojak.webapp.p2.inspector.server.handler.factory.RepositoryNameContextHandlerFactory;
import com.avojak.webapp.p2.inspector.server.handler.factory.RootContextHandlerFactory;

/**
 * Test class for {@link HandlerFactory}.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandlerFactoryTest {

	@Mock
	private ProvisioningAgentProvider agentProvider;

	@Mock
	private IProvisioningAgent agent;

	@Mock
	private IMetadataRepositoryManager metadataManager;

	@Mock
	private RootContextHandlerFactory rootContextHandlerFactory;

	@Mock
	private RepositoryNameContextHandlerFactory repositoryNameContextHandlerFactory;

	@Mock
	private RepositoryDescriptionContextHandlerFactory repositoryDescriptionContextHandlerFactory;

	@Mock
	private InstallableUnitContextHandlerFactory installableUnitContextHandlerFactory;

	@Mock
	private ContextHandler rootContextHandler, repositoryNameContextHandler, repositoryDescriptionContextHandler,
			installableUnitContextHandler;

	private HandlerFactory factory;

	/**
	 * Setup the mocks.
	 * 
	 * @throws ProvisionException
	 *             Unexpected.
	 */
	@Before
	public void setup() throws ProvisionException {
		Mockito.when(agentProvider.getAgent(null)).thenReturn(agent);
		Mockito.when(agent.getService(IMetadataRepositoryManager.SERVICE_NAME)).thenReturn(metadataManager);

		Mockito.when(rootContextHandlerFactory.create()).thenReturn(rootContextHandler);
		Mockito.when(repositoryNameContextHandlerFactory.create(metadataManager, null))
				.thenReturn(repositoryNameContextHandler);
		Mockito.when(repositoryDescriptionContextHandlerFactory.create(metadataManager, null))
				.thenReturn(repositoryDescriptionContextHandler);
		Mockito.when(installableUnitContextHandlerFactory.create(metadataManager, null))
				.thenReturn(installableUnitContextHandler);

		Mockito.when(rootContextHandler.getChildHandlers()).thenReturn(new Handler[] {});
		Mockito.when(repositoryNameContextHandler.getChildHandlers()).thenReturn(new Handler[] {});
		Mockito.when(repositoryDescriptionContextHandler.getChildHandlers()).thenReturn(new Handler[] {});
		Mockito.when(installableUnitContextHandler.getChildHandlers()).thenReturn(new Handler[] {});

		factory = new HandlerFactory(agentProvider, rootContextHandlerFactory, repositoryNameContextHandlerFactory,
				repositoryDescriptionContextHandlerFactory, installableUnitContextHandlerFactory);
	}

	/**
	 * Tests that the constructor throws an exception when the agent provider is
	 * null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullAgentProvider() {
		new HandlerFactory(null, rootContextHandlerFactory, repositoryNameContextHandlerFactory,
				repositoryDescriptionContextHandlerFactory, installableUnitContextHandlerFactory);
	}

	/**
	 * Tests that the constructor throws an exception when the root context handler
	 * factory is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullRootContextHandlerFactory() {
		new HandlerFactory(agentProvider, null, repositoryNameContextHandlerFactory,
				repositoryDescriptionContextHandlerFactory, installableUnitContextHandlerFactory);
	}

	/**
	 * Tests that the constructor throws an exception when the repository name
	 * context handler factory is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullRepositoryNameContextHandlerFactory() {
		new HandlerFactory(agentProvider, rootContextHandlerFactory, null, repositoryDescriptionContextHandlerFactory,
				installableUnitContextHandlerFactory);
	}

	/**
	 * Tests that the constructor throws an exception when the repository
	 * description context handler factory is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullRepositoryDescriptionContextHandlerFactory() {
		new HandlerFactory(agentProvider, rootContextHandlerFactory, repositoryNameContextHandlerFactory, null,
				installableUnitContextHandlerFactory);
	}

	/**
	 * Tests that the constructor throws an exception when the installable unit
	 * context handler factory is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullInstallableUnitContextHandlerFactory() {
		new HandlerFactory(agentProvider, rootContextHandlerFactory, repositoryNameContextHandlerFactory,
				repositoryDescriptionContextHandlerFactory, null);
	}

	/**
	 * Tests that {@link HandlerFactory#create()} throws an exception when the agent
	 * provider fails to provision the agent.
	 * 
	 * @throws ProvisionException
	 *             Unexpected.
	 */
	@Test(expected = RuntimeException.class)
	public void testCreate_FailedToCreateAgent() throws ProvisionException {
		Mockito.when(agentProvider.getAgent(null)).thenThrow(ProvisionException.class);
		factory.create();
	}

	/**
	 * Tests {@link HandlerFactory#create()}.
	 */
	@Test
	public void testCreate() {
		final ContextHandlerCollection handler = (ContextHandlerCollection) factory.create();
		final Handler[] handlers = handler.getHandlers();
		assertEquals(4, handlers.length);
		assertEquals(rootContextHandler, handlers[0]);
		assertEquals(repositoryNameContextHandler, handlers[1]);
		assertEquals(repositoryDescriptionContextHandler, handlers[2]);
		assertEquals(installableUnitContextHandler, handlers[3]);
	}

}
