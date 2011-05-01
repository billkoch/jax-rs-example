package com.billkoch.example.jaxrs.domain.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.billkoch.example.jaxrs.domain.Account;
import com.billkoch.example.jaxrs.domain.Customer;
import com.google.common.collect.Lists;

@Component
public class CustomerServiceImpl implements CustomerService {

	@Override
	public Customer findById(String id) {
		// Normally we'd call out to the database here, but for the purpose of
		// this example, it's not necessary.
		List<Account> accounts = Lists.newArrayList(new Account("456"));
		return new Customer(id, "Doe", "Jane", accounts);
	}
}
