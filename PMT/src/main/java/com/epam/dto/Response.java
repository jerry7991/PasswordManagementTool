package com.epam.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response {
	private boolean status;

	private Object msg;

	public Response(boolean status, Object msg) {
		this.status = status;
		this.msg = msg;
	}

}
