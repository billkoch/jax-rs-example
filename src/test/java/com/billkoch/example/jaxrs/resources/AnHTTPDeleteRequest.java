package com.billkoch.example.jaxrs.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;

public class AnHTTPDeleteRequest extends BaseResourceTest {

	@Test
	public void toASpecificCustomerURIWithXMLAcceptHeaderShouldReturnAnHTTPTwoHundredResponseCode() throws Exception {
		MockHttpRequest request = MockHttpRequest.delete("/customer/123456789");
		request.accept(MediaType.APPLICATION_XML);

		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertThat(response.getStatus(), is(HttpServletResponse.SC_OK));
	}

	@Test
	public void toASpecificCustomerURIWithJSONAcceptHeaderShouldReturnAnHTTPTwoHundredResponseCode() throws Exception {
		MockHttpRequest request = MockHttpRequest.delete("/customer/123456789");
		request.accept(MediaType.APPLICATION_JSON);

		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertThat(response.getStatus(), is(HttpServletResponse.SC_OK));
	}
}
