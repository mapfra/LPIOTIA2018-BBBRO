package org.eclipse.om2m.commons.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.eclipse.om2m.commons.constants.ShortName;

@MappedSuperclass
public class AnnouncedResourceEntity extends ResourceEntity {

	@Column(name = ShortName.EXPIRATION_TIME)
	protected String expirationTime;
	@Column(name = ShortName.LINK)
	protected String link;
	
	/**
	 * @return the expirationTime
	 */
	public String getExpirationTime() {
		return expirationTime;
	}

	/**
	 * @param expirationTime
	 *            the expirationTime to set
	 */
	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
}
