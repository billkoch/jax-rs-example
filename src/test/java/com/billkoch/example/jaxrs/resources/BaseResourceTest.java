package com.billkoch.example.jaxrs.resources;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.plugins.spring.SpringBeanProcessor;
import org.jboss.resteasy.plugins.spring.SpringResourceFactory;
import org.junit.BeforeClass;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseResourceTest {

	protected static Dispatcher dispatcher;

	@BeforeClass
	public static void initializeRestEasy() {
		dispatcher = MockDispatcherFactory.createDispatcher();

		// A draw-back to demonstrating Spring integration with RestEasy is that
		// my unit tests aren't true _unit_ tests: because I'm leveraging
		// Spring's autowiring capabilities, the "real" implementation of my
		// service layer is being injected into my JAX-RS resource classes.
		SpringBeanProcessor restEasySpringBeanProcessor = new SpringBeanProcessor(dispatcher);

		ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("jaxrsTestApplicationContext.xml");
		springContext.addBeanFactoryPostProcessor(restEasySpringBeanProcessor);

		SpringResourceFactory springResourceFactory = new SpringResourceFactory("customerResource", springContext, CustomerResource.class);
		dispatcher.getRegistry().addResourceFactory(springResourceFactory);
	}
}
