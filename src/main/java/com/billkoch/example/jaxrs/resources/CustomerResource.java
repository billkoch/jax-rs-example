package com.billkoch.example.jaxrs.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.billkoch.example.jaxrs.domain.Account;
import com.billkoch.example.jaxrs.domain.Customer;
import com.google.common.collect.Lists;

@Path("/customer")
public class CustomerResource {

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getCustomerById(@PathParam("id") String id) {
		List<Account> accounts = Lists.newArrayList(new Account());
		Customer customer = new Customer(id, "Doe", "Jane", accounts);
		return Response.ok().entity(customer).build();
	}

	@POST
	@Path("/")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createNewCustomer(Customer customer) {
		System.out.println(customer);
		return Response.created(UriBuilder.fromPath("/customer/{id}").build(customer.getId())).build();
	}
}
