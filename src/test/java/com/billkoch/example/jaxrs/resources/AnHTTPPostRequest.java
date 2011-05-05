package com.billkoch.example.jaxrs.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.core.messagebody.WriterUtility;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;

import com.billkoch.example.jaxrs.domain.Customer;

public class AnHTTPPostRequest extends BaseResourceTest {

	@Test
	public void toRootCustomerURIWithXMLAcceptHeaderAndPayloadInXMLFormatCreatesANewCustomer() throws Exception {
		Customer customer = new Customer("Doe", "Jane");
		String customerAsXML = WriterUtility.asString(customer, MediaType.APPLICATION_XML);

		MockHttpRequest request = MockHttpRequest.post("/customer");
		request.content(customerAsXML.getBytes());

		request.accept(MediaType.APPLICATION_XML);
		request.contentType(MediaType.APPLICATION_XML);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertThat(response.getStatus(), is(HttpServletResponse.SC_CREATED));

		URI locationHeader = (URI) response.getOutputHeaders().getFirst("Location");
		assertThat(locationHeader.toString(), startsWith("/customer/"));
	}

	@Test
	public void toRootCustomerURIWithXMLAcceptHeaderAndPayloadInXMLFormatThatCannotBeParsedShouldReturnAnHTTPFourHundredResponse() throws Exception {
		MockHttpRequest request = MockHttpRequest.post("/customer");
		request.content("<message>This is XML the service won't understand.</message>".getBytes());

		request.accept(MediaType.APPLICATION_JSON);
		request.contentType(MediaType.APPLICATION_JSON);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertThat(response.getStatus(), is(HttpServletResponse.SC_BAD_REQUEST));

		URI locationHeader = (URI) response.getOutputHeaders().getFirst("Location");
		assertThat(locationHeader, is(nullValue()));
	}

	@Test
	public void toRootCustomerURIWithJSONAcceptHeaderAndPayloadInJSONFormatCreatesANewCustomer() throws Exception {
		Customer customer = new Customer("Doe", "Jane");
		String customerAsJSON = WriterUtility.asString(customer, MediaType.APPLICATION_JSON);

		MockHttpRequest request = MockHttpRequest.post("/customer");
		request.content(customerAsJSON.getBytes());

		request.accept(MediaType.APPLICATION_JSON);
		request.contentType(MediaType.APPLICATION_JSON);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertThat(response.getStatus(), is(HttpServletResponse.SC_CREATED));

		String newCustomerURI = response.getOutputHeaders().get("Location").get(0).toString();

		assertThat(newCustomerURI, startsWith("/customer/"));

		URI locationHeader = (URI) response.getOutputHeaders().getFirst("Location");
		assertThat(locationHeader.toString(), startsWith("/customer/"));
	}

	@Test
	public void toRootCustomerURIWithJSONAcceptHeaderAndPayloadInJSONFormatThatCannotBeParsedShouldReturnAnHTTPFourHundredResponse() throws Exception {
		MockHttpRequest request = MockHttpRequest.post("/customer");
		request.content("{this is JSON that the service won't understand.}".getBytes());

		request.accept(MediaType.APPLICATION_JSON);
		request.contentType(MediaType.APPLICATION_JSON);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertThat(response.getStatus(), is(HttpServletResponse.SC_BAD_REQUEST));

		URI locationHeader = (URI) response.getOutputHeaders().getFirst("Location");
		assertThat(locationHeader, is(nullValue()));
	}
}
