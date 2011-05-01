package com.billkoch.example.jaxrs.domain.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

import com.billkoch.example.jaxrs.domain.Account;
import com.billkoch.example.jaxrs.domain.Customer;
import com.google.common.collect.Lists;

public class CustomerServiceTest {

	private CustomerService uut;

	@Before
	public void setup() {
		this.uut = new CustomerServiceImpl();
	}

	@Test
	public void retrieveCustomerByIdReturnsStubbedData() {
		Customer customer = this.uut.findById("123");

		Customer expectedStubbedCustomer = new Customer("123", "Doe", "Jane", Lists.newArrayList(new Account("456")));

		assertThat(expectedStubbedCustomer, is(customer));
	}

	@Test
	public void creatingANewCustomerShouldGenerateAnId() {
		Customer aNewCustomer = new Customer("Doe", "Jane");

		String newCustomerId = this.uut.createCustomer(aNewCustomer);

		assertThat(newCustomerId, is(notNullValue()));
	}
}
