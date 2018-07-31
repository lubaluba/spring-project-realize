package org.litespring.test.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.impl.ClassPathXmlApplicationContext;
import org.litespring.dao.AccountDao;
import org.litespring.dao.ItemDao;
import org.litespring.service.v2.PetStoreService;

public class ApplicationContextTestV2 {
	@Test
	public void testGetBeanProperty() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		
		assertNotNull(petStore.getAccountDao());
		assertNotNull(petStore.getItemDao());
		assertNotNull(petStore.getOwner());
		assertNotNull(petStore.getVersion());
		
		assertTrue(petStore.getAccountDao() instanceof AccountDao);
		assertTrue(petStore.getItemDao() instanceof ItemDao);
		assertEquals("zlm", petStore.getOwner());
		assertEquals(2, petStore.getVersion());
	}
}
