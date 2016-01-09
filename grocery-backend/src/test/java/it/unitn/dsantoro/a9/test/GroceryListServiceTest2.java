package it.unitn.dsantoro.a9.test;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import it.unitn.dsantoro.a9.GroceryListServiceRemote;
import it.unitn.dsantoro.a9.model.GroceryList;
import it.unitn.dsantoro.a9.model.Product;

public class GroceryListServiceTest2 {
	
	static GroceryListServiceRemote gList;
	static InitialContext initialContext; 
	
	@Test
	public void addFewLists(){
		System.out.println("Creating two list: List1 and List2");
		GroceryList gl1 = gList.addGroceryList("List1-add");
		assertTrue(gl1 != null);
		GroceryList gl2 = gList.addGroceryList("List2-add");
		assertTrue(gl2 != null);
	}
	
	@Test
	public void findList(){
		String name = "List-find";
		Long id = gList.addGroceryList(name).getId();
		System.out.println("Finding list with name " + name);
		String resName = gList.findGroceryList(id).getName();
		assertTrue(resName.equals(name));
	}
	
	@Test
	public void delList(){		
		String name = "List-del";
		Long id = gList.addGroceryList(name).getId();
		System.out.printf("Deleting list with name" + name);
		boolean result = gList.delGroceryList(id);
		assertTrue(result);
	}
	
	@Test
	public void findAllLists(){
		String name = "List-find";
		gList.addGroceryList(name);			
		System.out.println("Finding all lists");
		Collection<GroceryList> allList = gList.findMyGroceryLists();		
		int size = allList.size();		
		assertTrue(size > 1);
		Iterator<GroceryList> l = allList.iterator();
		System.out.println("All list for this client: ");
		while (l.hasNext()) {
			System.out.println("\t"+l.next());
		}
	}
	
	@Test
	public void addProductToList(){
		String name = "List-addprod";
		GroceryList gl = gList.addGroceryList(name);
		System.out.println("Adding product to list");
		Product p = new Product("pomodori");
		gList.addProduct(p, gl.getId());		
		//assertTrue(size > 1);
	}
	
	@BeforeClass
    public static void beforeClass() throws NamingException {
        System.out.println("Testing methods on the remote list");
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
        
        initialContext = new InitialContext(jndiProps);
        gList = (GroceryListServiceRemote) initialContext.lookup("java:grocery-backend/GroceryListService!it.unitn.dsantoro.a9.GroceryListServiceRemote");
        
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
