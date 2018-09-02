package com.wesayweb.request.model;

import java.io.Serializable;

public class UserTraitRequest implements Serializable {

	private Long traitId;
	private int hideStatus;

	public UserTraitRequest(Long traitId, int hideStatus) {
		super();
		this.traitId = traitId;
		this.hideStatus = hideStatus;
	}

	public Long getTraitId() {
		return traitId;
	}

	public void setTraitId(Long traitId) {
		this.traitId = traitId;
	}

	public int  getHideStatus() {
		return hideStatus;
	}

	public void setHideStatus(int hideStatus) {
		this.hideStatus = hideStatus;
	}

}