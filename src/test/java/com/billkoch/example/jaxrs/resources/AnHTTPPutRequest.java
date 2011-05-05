package com.billkoch.example.jaxrs.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.core.messagebody.ReaderUtility;
import org.jboss.resteasy.core.messagebody.WriterUtility;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;

import com.billkoch.example.jaxrs.domain.Account;
import com.billkoch.example.jaxrs.domain.Customer;

public class AnHTTPPutRequest extends BaseResourceTest {

	@Test
	public void toASpecificCustomerURIAndACustomerInXMLFormatAsTheRequestBodyUpdatesACustomer() throws Exception {
		MockHttpRequest request = MockHttpRequest.put("/customer/123456789");

		Customer customerToUpdate = new Customer("123456789", "Doe", "Jane", new ArrayList<Account>());
		String customerAsXML = WriterUtility.asString(customerToUpdate, MediaType.APPLICATION_XML);
		request.content(customerAsXML.getBytes());
		request.accept(MediaType.APPLICATION_XML);
		request.contentType(MediaType.APPLICATION_XML);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertThat(response.getStatus(), is(HttpServletResponse.SC_OK));

		Customer updatedCustomer = ReaderUtility.read(Customer.class, MediaType.APPLICATION_XML, response.getContentAsString());

		assertThat(customerToUpdate, is(updatedCustomer));
	}

	@Test
	public void toASpecificCustomerURIAndACustomerInJSONFormatAsTheRequestBodyUpdatesACustomer() throws Exception {
		MockHttpRequest request = MockHttpRequest.put("/customer/123456789");

		Customer customerToUpdate = new Customer("123456789", "Doe", "Jane", new ArrayList<Account>());
		String customerAsXML = WriterUtility.asString(customerToUpdate, MediaType.APPLICATION_JSON);
		request.content(customerAsXML.getBytes());
		request.accept(MediaType.APPLICATION_JSON);
		request.contentType(MediaType.APPLICATION_JSON);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertThat(response.getStatus(), is(HttpServletResponse.SC_OK));

		Customer updatedCustomer = ReaderUtility.read(Customer.class, MediaType.APPLICATION_JSON, response.getContentAsString());

		assertThat(customerToUpdate, is(updatedCustomer));
	}
}
