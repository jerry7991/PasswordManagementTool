package com.epam.model;

public class Response {
	private boolean status;
	private Object msg;

	public Response() {
		status = true;
	}

	public Response(boolean status, Object msg) {
		this.status = status;
		this.msg = msg;
	}

	public void setStatus(boolean isSuccess) {
		this.status = isSuccess;
	}

	public boolean getStatus() {
		return status;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}
}
