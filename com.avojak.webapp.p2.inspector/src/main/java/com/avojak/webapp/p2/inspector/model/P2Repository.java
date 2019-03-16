package com.avojak.webapp.p2.inspector.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import java.net.URI;
import java.util.Collection;

/**
 * Models the P2 repository details.
 */
public class P2Repository {

	private final String name;
	private final URI location;
	private final boolean isCompressed;
	private final long lastModified;
	private final Collection<IUGroup> groups;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            The repository name. Cannot be null or empty.
	 * @param location
	 *            The repository location. Cannot be null.
	 * @param isCompressed
	 *            Whether or not the repository is compressed.
	 * @param lastModified
	 *            The timestamp of when the repository was last modified.
	 * @param groups
	 *            The collection of installable unit groups. Cannot be null.
	 */
	public P2Repository(final String name, final URI location, final boolean isCompressed, final long lastModified,
			final Collection<IUGroup> groups) {
		this.name = checkNotNull(name, "name cannot be null");
		checkArgument(!name.trim().isEmpty(), "name cannot be empty");
		this.location = checkNotNull(location, "location cannot be null");
		this.isCompressed = isCompressed;
		this.lastModified = lastModified;
		this.groups = checkNotNull(groups, "groups cannot be null");
	}

	/**
	 * Gets the repository name.
	 * 
	 * @return The non-null, non-empty name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the repository location.
	 * 
	 * @return The non-null {@link URI}.
	 */
	public URI getLocation() {
		return location;
	}

	/**
	 * Returns whether or not the repository is compressed.
	 * 
	 * @return {@code true} if the repository is compressed, otherwise
	 *         {@code false}.
	 */
	public boolean isCompressed() {
		return isCompressed;
	}

	/**
	 * Gets the timestamp of when the repository was last modified.
	 * 
	 * @return The timestamp.
	 */
	public long getLastModified() {
		return lastModified;
	}

	/**
	 * Gets the collection of installable unit groups.
	 * 
	 * @return The non-null, possibly empty {@link Collection}.
	 */
	public Collection<IUGroup> getGroups() {
		return groups;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + (isCompressed ? 1231 : 1237);
		result = prime * result + (int) (lastModified ^ (lastModified >>> 32));
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		P2Repository other = (P2Repository) obj;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (isCompressed != other.isCompressed)
			return false;
		if (lastModified != other.lastModified)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
