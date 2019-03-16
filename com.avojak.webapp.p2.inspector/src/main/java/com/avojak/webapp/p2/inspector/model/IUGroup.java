package com.avojak.webapp.p2.inspector.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;

/**
 * Models an installable unit group.
 */
public class IUGroup {

	private final String name;
	private final String id;
	private final String description;
	private final String copyright;
	private final Collection<License> licenses;
	private final String version;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            The group name. Cannot be null or empty.
	 * @param id
	 *            The id. Cannot be null or empty.
	 * @param description
	 *            The description. Cannot be null.
	 * @param copyright
	 *            The copyright. Cannot be null.
	 * @param licenses
	 *            The collection of licenses. Cannot be null.
	 * @param version
	 *            The version. Cannot be null or empty.
	 */
	public IUGroup(final String name, final String id, final String description, final String copyright,
			final Collection<License> licenses, final String version) {
		this.name = checkNotNull(name, "name cannot be null");
		checkArgument(!name.trim().isEmpty(), "name cannot be empty");
		this.id = checkNotNull(id, "id cannot be null");
		checkArgument(!id.trim().isEmpty(), "id cannot be empty");
		this.description = checkNotNull(description, "description cannot be null");
		this.copyright = checkNotNull(copyright, "copyright cannot be null");
		this.licenses = checkNotNull(licenses, "licenses cannot be null");
		this.version = checkNotNull(version, "version cannot be null");
		checkArgument(!version.trim().isEmpty(), "version cannot be empty");
	}

	/**
	 * Gets the group name.
	 * 
	 * @return The non-null, non-empty group name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the group ID.
	 * 
	 * @return The non-null, non-empty group ID.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the description.
	 * 
	 * @return The non-null, possibly empty description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the copyright.
	 * 
	 * @return The non-null, possibly empty copyright.
	 */
	public String getCopyright() {
		return copyright;
	}

	/**
	 * The collection of licenses.
	 * 
	 * @return The non-null, possibly empty collection of {@link License} objects.
	 */
	public Collection<License> getLicenses() {
		return licenses;
	}

	/**
	 * Gets the version.
	 * 
	 * @return The non-null, non-empty version.
	 */
	public String getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((copyright == null) ? 0 : copyright.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((licenses == null) ? 0 : licenses.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		IUGroup other = (IUGroup) obj;
		if (copyright == null) {
			if (other.copyright != null)
				return false;
		} else if (!copyright.equals(other.copyright))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (licenses == null) {
			if (other.licenses != null)
				return false;
		} else if (!licenses.equals(other.licenses))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}
