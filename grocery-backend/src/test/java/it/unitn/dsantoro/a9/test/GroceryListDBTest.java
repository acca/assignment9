package it.unitn.dsantoro.a9.test;

import static org.junit.Assert.*;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.*;

import org.junit.BeforeClass;
import org.junit.Test;

import it.unitn.dsantoro.a9.model.GroceryList;
import it.unitn.dsantoro.a9.model.Product;

public class GroceryListDBTest {
	
	static GroceryList gList;
	static Product pomodoro;
	
	@Test
	public void addGroceryList(){
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("grocery-backend-test");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		GroceryList gl = new GroceryList();
		gl.setName("spesa poli");
		Product p = new Product();
		p.setName("pane");
		p.setQuantity(3);
		gl.addProduct(p);
		entityManager.persist(gl);
		//entityManager.persist( new Event( "A follow up event", new Date() ) );
		entityManager.getTransaction().commit();
		//entityManager.close();
	}
	
	@Test
	public void delProduct() {
		
	}
	@BeforeClass
    public static void beforeClass() throws NamingException {
        System.out.println("Testing insertion of products into the remote list");
    }
}
