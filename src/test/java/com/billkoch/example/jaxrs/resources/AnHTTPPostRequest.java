package com.billkoch.example.jaxrs.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;

import java.net.URI;
import java.net.URISyntaxException;

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

		MockHttpResponse response = this.invokeHTTPPost(customerAsXML, MediaType.APPLICATION_XML);

		assertThat(response.getStatus(), is(HttpServletResponse.SC_CREATED));

		URI locationHeader = (URI) response.getOutputHeaders().getFirst("Location");
		assertThat(locationHeader.toString(), startsWith("/customer/"));
	}

	@Test
	public void toRootCustomerURIWithXMLAcceptHeaderAndPayloadInXMLFormatThatCannotBeParsedShouldReturnAnHTTPFourHundredResponse() throws Exception {
		MockHttpResponse response = this.invokeHTTPPost("<message>This is XML the service won't understand.</message>", MediaType.APPLICATION_XML);

		assertThat(response.getStatus(), is(HttpServletResponse.SC_BAD_REQUEST));

		URI locationHeader = (URI) response.getOutputHeaders().getFirst("Location");
		assertThat(locationHeader, is(nullValue()));
	}

	@Test
	public void toRootCustomerURIWithJSONAcceptHeaderAndPayloadInJSONFormatCreatesANewCustomer() throws Exception {
		Customer customer = new Customer("Doe", "Jane");
		String customerAsJSON = WriterUtility.asString(customer, MediaType.APPLICATION_JSON);

		MockHttpResponse response = this.invokeHTTPPost(customerAsJSON, MediaType.APPLICATION_JSON);

		assertThat(response.getStatus(), is(HttpServletResponse.SC_CREATED));

		URI locationHeader = (URI) response.getOutputHeaders().getFirst("Location");
		assertThat(locationHeader.toString(), startsWith("/customer/"));
	}

	@Test
	public void toRootCustomerURIWithJSONAcceptHeaderAndPayloadInJSONFormatThatCannotBeParsedShouldReturnAnHTTPFourHundredResponse() throws Exception {
		MockHttpResponse response = this.invokeHTTPPost("{this is JSON that the service won't understand.}", MediaType.APPLICATION_JSON);

		assertThat(response.getStatus(), is(HttpServletResponse.SC_BAD_REQUEST));

		URI locationHeader = (URI) response.getOutputHeaders().getFirst("Location");
		assertThat(locationHeader, is(nullValue()));
	}

	private MockHttpResponse invokeHTTPPost(String requestBody, String acceptHeaderAndContentType) throws URISyntaxException {
		MockHttpRequest request = MockHttpRequest.post("/customer");
		request.content(requestBody.getBytes());

		request.accept(acceptHeaderAndContentType);
		request.contentType(acceptHeaderAndContentType);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		return response;
	}
}
