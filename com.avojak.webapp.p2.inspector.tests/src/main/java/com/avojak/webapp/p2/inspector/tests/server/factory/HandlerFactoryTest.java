package com.avojak.webapp.p2.inspector.tests.server.factory;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.runtime.ILog;
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
import com.avojak.webapp.p2.inspector.server.handler.factory.RepositoryContextHandlerFactory;
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
	private RepositoryContextHandlerFactory repositoryContextHandlerFactory;

	@Mock
	private ContextHandler rootContextHandler, repositoryContextHandler;

	@Mock
	private ILog log;

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
		Mockito.when(repositoryContextHandlerFactory.create(metadataManager, null))
				.thenReturn(repositoryContextHandler);

		Mockito.when(rootContextHandler.getChildHandlers()).thenReturn(new Handler[] {});
		Mockito.when(repositoryContextHandler.getChildHandlers()).thenReturn(new Handler[] {});

		factory = new HandlerFactory(agentProvider, rootContextHandlerFactory, repositoryContextHandlerFactory, log);
	}

	/**
	 * Tests that the constructor throws an exception when the agent provider is
	 * null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullAgentProvider() {
		new HandlerFactory(null, rootContextHandlerFactory, repositoryContextHandlerFactory, log);
	}

	/**
	 * Tests that the constructor throws an exception when the root context handler
	 * factory is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullRootContextHandlerFactory() {
		new HandlerFactory(agentProvider, null, repositoryContextHandlerFactory, log);
	}

	/**
	 * Tests that the constructor throws an exception when the repository context
	 * handler factory is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullRepositoryContextHandlerFactory() {
		new HandlerFactory(agentProvider, rootContextHandlerFactory, null, log);
	}

	/**
	 * Tests that the constructor throws an exception when the log instance is null.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructor_NullLog() {
		new HandlerFactory(agentProvider, rootContextHandlerFactory, repositoryContextHandlerFactory, null);
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
		assertEquals(2, handlers.length);
		assertEquals(rootContextHandler, handlers[0]);
		assertEquals(repositoryContextHandler, handlers[1]);
	}

}
