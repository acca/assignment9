//package it.unitn.dsantoro.a9.test;
//
//import static org.junit.Assert.assertTrue;
//
//import java.util.Properties;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import it.unitn.dsantoro.a9.GroceryListServiceRemote;
//import it.unitn.dsantoro.a9.Product;
//
//public class ClientTest {
//
//	static GroceryListServiceRemote gList;	
//	
//	
//	public void addProductToList(){
//		Product pomodoro = new Product();
//        pomodoro.setId("1");
//        pomodoro.setName("pomodoro");
//		gList.addProduct(pomodoro);
//		assertTrue(gList.list().size()==1);
//	}
//	
//	
//	public void removeProductFromList(){
//		gList.removeProduct("1");
//		assertTrue(gList.list().size()==0);
//	}
//	
//	
//    public static void beforeClass() throws NamingException {
//    
//    }
//	
//	public static void main(String[] args) throws NamingException {
//	    System.out.println("Testing insertion of products into the remote list");
//        Properties jndiProps = new Properties();
//        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.remote.client.InitialContextFactory");
//        jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");        
//        jndiProps.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8081");
//        //This property is important for remote resolving
//        jndiProps.put("jboss.naming.client.ejb.context", true);
//        //This propert is not important for remote resolving
//        jndiProps.put("org.jboss.ejb.client.scoped.context", true);
//    
//        // username
//        jndiProps.put(Context.SECURITY_PRINCIPAL, "user");
//        // password
//        jndiProps.put(Context.SECURITY_CREDENTIALS, "pw");
//        
//        InitialContext initialContext = new InitialContext(jndiProps);
//        gList = (GroceryListServiceRemote) initialContext.lookup("java:grocery-backend/GroceryList!it.unitn.dsantoro.a9.GroceryListRemote");        
//	}
//}
