package com.billkoch.example.jaxrs.domain;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseDomainObject {

	private String id;

	public BaseDomainObject() {
		this(UUID.randomUUID().toString());
	}

	public BaseDomainObject(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
