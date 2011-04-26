package com.billkoch.example.jaxrs.domain;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Account extends BaseDomainObject {

	private BigDecimal balance;

	public Account() {
		this(BigDecimal.ZERO);
	}

	public Account(String id) {
		this(id, BigDecimal.ZERO);
	}

	public Account(BigDecimal balance) {
		super();
		this.balance = balance;
	}

	public Account(String id, BigDecimal balance) {
		super(id);
		this.balance = balance;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj == this) {
			result = true;

		} else if (obj instanceof Account) {
			Account that = (Account) obj;
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
