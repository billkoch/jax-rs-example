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
		SpringBeanProcessor restEasySpringBeanProcessor = new SpringBeanProcessor(dispatcher);

		ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("testApplicationContext.xml");
		springContext.addBeanFactoryPostProcessor(restEasySpringBeanProcessor);

		SpringResourceFactory springResourceFactory = new SpringResourceFactory("customerResource", springContext, CustomerResource.class);
		dispatcher.getRegistry().addResourceFactory(springResourceFactory);
	}
}
