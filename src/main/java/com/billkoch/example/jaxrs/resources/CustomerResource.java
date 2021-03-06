package com.billkoch.example.jaxrs.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.billkoch.example.jaxrs.domain.Customer;
import com.billkoch.example.jaxrs.domain.service.CustomerService;

@Controller
@Path("/customer")
public class CustomerResource {

	private static final Logger log = LoggerFactory.getLogger(CustomerResource.class);

	@Autowired
	private CustomerService customerService;

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getCustomerById(@PathParam("id") String id) {
		Customer customer = this.customerService.findById(id);

		log.debug("Returning customer:  {}", customer);
		return Response.ok().entity(customer).build();
	}

	@POST
	@Path("/")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createNewCustomer(Customer customer) {
		String newCustomerId = this.customerService.createCustomer(customer);

		log.debug("New Customer ID:  '{}'", newCustomerId);
		return Response.created(UriBuilder.fromPath("/customer/{id}").build(newCustomerId)).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateExistingCustomer(Customer customer) {
		log.debug("Updated Customer:  {}", customer);
		return Response.ok().entity(customer).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteExistingCustomer(@PathParam("id") String id) {
		log.debug("Deleting Customer with ID:  {}", id);
		return Response.ok().build();
	}
}
