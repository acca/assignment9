package it.unitn.dsantoro.a9.test;

import static org.junit.Assert.*;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.Test;

import it.unitn.dsantoro.a9.GroceryListRemote;
import it.unitn.dsantoro.a9.Product;

public class GroceryListServiceTest {
	
	static GroceryListRemote gList;
	static Product pomodoro;
	
	@Test
	public void addProductToList(){			
		gList.addProduct(pomodoro);
		assertTrue(gList.list().size()==1);
	}
	
	@Test
	public void removeProductFromList(){
		gList.removeProduct("1");
		assertTrue(gList.list().size()==0);
	}
	
	@BeforeClass
    public static void beforeClass() throws NamingException {
        System.out.println("Testing insertion of products into the remote list");
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.remote.client.InitialContextFactory");
        jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");        
        jndiProps.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8081");
        //This property is important for remote resolving
        jndiProps.put("jboss.naming.client.ejb.context", true);
        //This propert is not important for remote resolving
        jndiProps.put("org.jboss.ejb.client.scoped.context", true);
    
        // username
        jndiProps.put(Context.SECURITY_PRINCIPAL, "user");
        // password
        jndiProps.put(Context.SECURITY_CREDENTIALS, "pw");
        
        InitialContext initialContext = new InitialContext(jndiProps);                 
        gList = (GroceryListRemote) initialContext.lookup("java:grocery-backend/GroceryList!it.unitn.dsantoro.a9.GroceryListRemote");
        
        pomodoro = new Product();
        pomodoro.setId("1");
        pomodoro.setName("pomodori");
    }

//    @AfterClass
//    public static void afterClass() {
//        em.close();
//        emf.close();
//    }
//
//    @Before
//    public void before() {
//        tx = em.getTransaction();
//    }
}
