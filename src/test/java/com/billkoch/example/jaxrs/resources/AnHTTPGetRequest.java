package com.billkoch.example.jaxrs.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.core.messagebody.ReaderUtility;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;

import com.billkoch.example.jaxrs.domain.Customer;

public class AnHTTPGetRequest extends BaseResourceTest {

	@Test
	public void withAcceptHeaderSetToXMLShouldReturnASpecificCustomerInXMLFormat() throws Exception {
		MockHttpRequest request = MockHttpRequest.get("/customer/123");
		request.accept(MediaType.APPLICATION_XML);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));

		Customer customer = ReaderUtility.read(Customer.class, MediaType.APPLICATION_XML, response.getContentAsString());
		assertThat(customer.getId(), is("123"));
	}

	@Test
	public void withAcceptHeaderSetToJSONShouldReturnASpecificCustomerInJSONFormat() throws Exception {
		MockHttpRequest request = MockHttpRequest.get("/customer/123");
		request.accept(MediaType.APPLICATION_JSON);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertThat(response.getStatus(), is(HttpServletResponse.SC_OK));

		Customer customer = ReaderUtility.read(Customer.class, MediaType.APPLICATION_JSON, response.getContentAsString());
		assertThat(customer.getId(), is("123"));
	}
}
