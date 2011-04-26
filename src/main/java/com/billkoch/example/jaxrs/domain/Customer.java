package com.billkoch.example.jaxrs.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer extends BaseDomainObject {

	private String lastName;
	
	private String firstName;
	
	private List<Account> accounts;
	
	public Customer() {
		this("", "");
	}

	public Customer(String lastName, String firstName) {
		this(lastName, firstName, new ArrayList<Account>());
	}
	
	public Customer(String lastName, String firstName, List<Account> accounts) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.accounts = accounts;
	}
	
	public Customer(String id, String lastName, String firstName, List<Account> accounts) {
		super(id);
		this.lastName = lastName;
		this.firstName = firstName;
		this.accounts = accounts;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj == this) {
			result = true;
			
		} else if(obj instanceof Customer) {
			Customer that = (Customer) obj;
			result = EqualsBuilder.reflectionEquals(this, that);
		}
		return result;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
