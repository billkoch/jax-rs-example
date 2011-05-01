package com.billkoch.example.jaxrs.domain.service;

import com.billkoch.example.jaxrs.domain.Customer;

public interface CustomerService {

	Customer findById(String id);

	String createCustomer(Customer customer);
}
