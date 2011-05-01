package com.billkoch.example.jaxrs.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.core.messagebody.ReaderUtility;
import org.jboss.resteasy.core.messagebody.WriterUtility;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.spring.SpringBeanProcessor;
import org.jboss.resteasy.plugins.spring.SpringResourceFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.billkoch.example.jaxrs.domain.Customer;

public class CustomerResourceTest {

	private static Dispatcher dispatcher;

	@BeforeClass
	public static void initializeRestEasy() {
		dispatcher = MockDispatcherFactory.createDispatcher();
		SpringBeanProcessor restEasySpringBeanProcessor = new SpringBeanProcessor(dispatcher);

		ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("testApplicationContext.xml");
		springContext.addBeanFactoryPostProcessor(restEasySpringBeanProcessor);

		SpringResourceFactory springResourceFactory = new SpringResourceFactory("customerResource", springContext, CustomerResource.class);
		dispatcher.getRegistry().addResourceFactory(springResourceFactory);
	}

	@Test
	public void getIdWithAcceptHeaderXMLReturnsXML() throws Exception {
		MockHttpRequest request = MockHttpRequest.get("/customer/123");
		request.accept(MediaType.APPLICATION_XML);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));

		Customer customer = ReaderUtility.read(Customer.class, MediaType.APPLICATION_XML, response.getContentAsString());
		assertThat(customer.getId(), is("123"));
	}

	@Test
	public void getIdWithAcceptHeaderJSONReturnsJSON() throws Exception {
		MockHttpRequest request = MockHttpRequest.get("/customer/123");
		request.accept(MediaType.APPLICATION_JSON);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertThat(response.getStatus(), is(HttpServletResponse.SC_OK));

		Customer customer = ReaderUtility.read(Customer.class, MediaType.APPLICATION_JSON, response.getContentAsString());
		assertThat(customer.getId(), is("123"));
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

		assertThat(response.getStatus(), is(HttpServletResponse.SC_CREATED));
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

		assertThat(response.getStatus(), is(HttpServletResponse.SC_CREATED));

		String newCustomerURI = response.getOutputHeaders().get("Location").get(0).toString();

		assertThat(newCustomerURI, startsWith("/customer/"));
	}

	@Test
	public void postWithJsonMessageTheServiceWontUnderstandRespondsWithHttpBadRequestResponse() throws Exception {
		MockHttpRequest request = MockHttpRequest.post("/customer");
		request.content("{this is JSON that the service won't understand.}".getBytes());

		request.accept(MediaType.APPLICATION_JSON);
		request.contentType(MediaType.APPLICATION_JSON);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertThat(response.getStatus(), is(HttpServletResponse.SC_BAD_REQUEST));
	}
}
