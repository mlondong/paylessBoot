package com.payless.demo.dto;

import java.io.Serializable;

public abstract class AbstractDTO implements Serializable {

	private static final long serialVersionUID = -1244319357169588405L;
	private long id;

	public AbstractDTO() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public abstract Object toModel();

}

