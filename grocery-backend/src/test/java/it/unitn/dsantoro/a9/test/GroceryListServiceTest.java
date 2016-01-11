package it.unitn.dsantoro.a9.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
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

public class GroceryListServiceTest {
	
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
	
	@Test
	public void updateProduct(){
		System.out.println("Testing the product update");
		String name = "List-updprod";
		GroceryList gl = gList.addGroceryList(name);
		System.out.println("Adding product to list");
		Product p = new Product("pomodori");
		p = gList.addProduct(p, gl.getId());
		gl = gList.findGroceryList(gl.getId());
		Iterator<Product> pi = gl.getProducts().iterator();
		System.out.println("Products in list: " + gl);
		while(pi.hasNext()) {
			System.out.println("\t"+pi.next());
		}
		
		gList.updateProduct(gl.getId(), p.getId(), "salame", 2, 1);
		gl = gList.findGroceryList(gl.getId());
		pi = gl.getProducts().iterator();
		System.out.println("Products in list: " + gl);
		while(pi.hasNext()) {
			System.out.println(pi.next());
		}
		//assertTrue(size > 1);

	}

	@Test
	public void findAllProducts(){
		System.out.println("Testing methods getAllProduct");
		String name = "List-findallprod";
		GroceryList gl = gList.addGroceryList(name);
		System.out.println("Adding product to list");
		gList.addProduct(new Product("pomodori"), gl.getId());
		gList.addProduct(new Product("latte intero"), gl.getId());
		gList.addProduct(new Product("banane"), gl.getId());
		gList.addProduct(new Product("olio"), gl.getId());
		gList.addProduct(new Product("pane"), gl.getId());
		
		gl = gList.findGroceryList(gl.getId());
				
		Iterator<Product> pi = gl.getProducts().iterator();
		System.out.println("Products in list: " + gl);
		while(pi.hasNext()) {
			System.out.println("\t"+pi.next());
		}
		
		int listSize = gList.findAllProducts(gl.getId()).size();
		assertTrue(listSize == 5);
		
		System.out.println("Testing mark a product out of list");
		Collection<Product> cp = gl.getProducts();
		ArrayList<Product> lp = new ArrayList<Product>(cp);
		lp.get(cp.size()-1).setInList(false);
		int inList = gl.getProducts(true).size();
		System.out.println("In list: " + inList);
		int notInList = gl.getProducts(false).size();
		System.out.println("Not in list: " + notInList);
		assertTrue(inList == listSize - 1);
		assertTrue(notInList == 1);
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
