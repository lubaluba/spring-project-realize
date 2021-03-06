package org.litespring.test.v5;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Test;
import org.litespring.aop.config.MethodLocatingFactory;
import org.litespring.beans.factory.impl.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.tx.TransactionManager;

public class MethodLocatingFactoryTest {
	@Test
	public void testGetMethod() throws Exception{
		
		DefaultBeanFactory beanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		Resource resource = new ClassPathResource("petstore-v5.xml");
		reader.loadBeanDefinitions(resource);
		
		MethodLocatingFactory methodLocatingFactory = new MethodLocatingFactory();
		methodLocatingFactory.setTargetBeanName("tx");
		methodLocatingFactory.setMethodName("start");
		methodLocatingFactory.setBeanFactory(beanFactory);
		
		Method m = methodLocatingFactory.getObject();
		
		assertTrue(TransactionManager.class.equals(m.getDeclaringClass()));
		assertTrue(m.equals(TransactionManager.class.getMethod("start")));
		
	}
}
