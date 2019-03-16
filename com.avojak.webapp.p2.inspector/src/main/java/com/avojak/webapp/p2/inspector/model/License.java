package com.avojak.webapp.p2.inspector.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;

/**
 * Models a license.
 */
public class License {

	private final String name;
	private final String body;
	private final URI location;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            The name. Cannot be null or empty.
	 * @param body
	 *            The body. Cannot be null or empty.
	 * @param location
	 *            The location.
	 */
	public License(final String name, final String body, final URI location) {
		this.name = checkNotNull(name, "name cannot be null");
		checkArgument(!name.trim().isEmpty(), "name cannot be empty");
		this.body = checkNotNull(body, "body cannot be null");
		checkArgument(!body.trim().isEmpty(), "body cannot be empty");
		this.location = location;
	}

	/**
	 * Gets the name of the license.
	 * 
	 * @return The non-null, non-empty name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the body of the license.
	 * 
	 * @return The non-null, non-empty body.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Gets the location of the license.
	 * 
	 * @return The location {@link URI}.
	 */
	public URI getLocation() {
		return location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
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
		License other = (License) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
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
