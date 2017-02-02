package de.bs14.Webservice.model;

import java.io.Serializable;

public class Group implements Serializable{

	private static final long serialVersionUID = 1L;
	private String groupid;
	private String groupname;

	public Group(String groupid, String groupname) {
		this.groupid = groupid;
		this.groupname = groupname;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
}
