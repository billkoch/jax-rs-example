package com.billkoch.example.jaxrs.resources;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.core.messagebody.ReaderUtility;
import org.jboss.resteasy.core.messagebody.WriterUtility;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import com.billkoch.example.jaxrs.domain.Customer;

public class CustomerResourceTest {

	private static Dispatcher dispatcher;

	@BeforeClass
	public static void initializeRestEasy() {
		dispatcher = MockDispatcherFactory.createDispatcher();
		dispatcher.getRegistry().addResourceFactory(new POJOResourceFactory(CustomerResource.class));
	}

	@Test
	public void getIdWithAcceptHeaderXMLReturnsXML() throws Exception {
		MockHttpRequest request = MockHttpRequest.get("/customer/123");
		request.accept(MediaType.APPLICATION_XML);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		Customer customer = ReaderUtility.read(Customer.class, MediaType.APPLICATION_XML, response.getContentAsString());
		assertEquals("123", customer.getId());
	}

	@Test
	public void getIdWithAcceptHeaderJSONReturnsJSON() throws Exception {
		MockHttpRequest request = MockHttpRequest.get("/customer/123");
		request.accept(MediaType.APPLICATION_JSON);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertEquals(HttpServletResponse.SC_OK, response.getStatus());

		Customer customer = ReaderUtility.read(Customer.class, MediaType.APPLICATION_JSON, response.getContentAsString());
		assertEquals("123", customer.getId());
	}

	@Test
	public void postCreatesANewCustomerFromXML() throws Exception {
		Customer customer = new Customer("Doe", "Jane");
		String customerAsXML = WriterUtility.asString(customer, MediaType.APPLICATION_XML);

		MockHttpRequest request = MockHttpRequest.post("/customer");
		request.content(customerAsXML.getBytes());

		request.accept(MediaType.APPLICATION_XML);
		request.contentType(MediaType.APPLICATION_XML);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
	}

	@Test
	public void postCreatesANewCustomerFromJSON() throws Exception {
		Customer customer = new Customer("Doe", "Jane");
		String customerAsJSON = WriterUtility.asString(customer, MediaType.APPLICATION_JSON);

		MockHttpRequest request = MockHttpRequest.post("/customer");
		request.content(customerAsJSON.getBytes());

		request.accept(MediaType.APPLICATION_JSON);
		request.contentType(MediaType.APPLICATION_JSON);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());

		String newCustomerURI = response.getOutputHeaders().get("Location").get(0).toString();
		assertEquals("/customer/" + customer.getId(), newCustomerURI);
	}
}
