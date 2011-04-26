package com.billkoch.example.jaxrs.domain.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.billkoch.example.jaxrs.domain.Account;
import com.billkoch.example.jaxrs.domain.Customer;
import com.google.common.collect.Lists;

public class CustomerServiceTest {

	@Test
	public void retrieveCustomerByIdReturnsStubbedData() {
		CustomerService uut = new CustomerServiceImpl();
		Customer customer = uut.findById("123");

		Customer expectedStubbedCustomer = new Customer("123", "Doe", "Jane", Lists.newArrayList(new Account("456")));

		assertThat(expectedStubbedCustomer, is(customer));
	}
}
