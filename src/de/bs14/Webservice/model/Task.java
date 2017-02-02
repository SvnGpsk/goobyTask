package de.bs14.Webservice.model;

import java.io.Serializable;

public class Task implements Serializable {

	private static final long serialVersionUID = 1L;
	private String taskname;
	private String taskworker;
	private String taskstate;

	public Task(String taskname, String taskworker,
			String taskstate) {
		this.taskname = taskname;
		this.taskworker = taskworker;
		this.taskstate = taskstate;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getTaskworker() {
		return taskworker;
	}

	public void setTaskworker(String taskworker) {
		this.taskworker = taskworker;
	}

	public String getTaskstate() {
		return taskstate;
	}

	public void setTaskstate(String taskstate) {
		this.taskstate = taskstate;
	}

}
