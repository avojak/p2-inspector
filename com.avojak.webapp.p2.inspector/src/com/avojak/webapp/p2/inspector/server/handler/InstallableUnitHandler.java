package com.avojak.webapp.p2.inspector.server.handler;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.PrintWriter;
import java.util.Set;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepository;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;

import com.google.gson.Gson;

public class InstallableUnitHandler extends AbstractRequestHandler {

	public InstallableUnitHandler(final IMetadataRepositoryManager metadataManager,
			final IArtifactRepositoryManager artifactManager, final Gson gson) {
		super(metadataManager, artifactManager, gson);
	}

	@Override
	protected void handle(final IMetadataRepository metadataRepository, final IArtifactRepository artifactRepository,
			final PrintWriter out) {
		checkNotNull(metadataManager, "metadataManager cannot be null");
//		checkNotNull(artifactRepository, "artifactRepository cannot be null");
		checkNotNull(out, "out cannot be null");

		final Set<IInstallableUnit> result = metadataRepository.query(QueryUtil.ALL_UNITS, new NullProgressMonitor())
				.toUnmodifiableSet();

		out.println(gson.toJson(result));
	}

}
