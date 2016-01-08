package it.unitn.dsantoro.a9;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import it.unitn.dsantoro.a9.model.GroceryList;
import it.unitn.dsantoro.a9.model.Product;

/**
 * Session Bean implementation class Cart
 */
@Stateful
public class GroceryListService implements GroceryListServiceRemote {
	@PersistenceContext(unitName = "grocery-backend", type = PersistenceContextType.EXTENDED) EntityManager entityManager;
	
	ArrayList<GroceryList> groceryList;
	
    public GroceryListService() {
    	groceryList = new ArrayList<GroceryList>(); 
    }
	@Override
	public GroceryList addGroceryList(String listName) {
		GroceryList groceryList = new GroceryList();
		groceryList.setName(listName);
		entityManager.persist(groceryList);		
		return groceryList;
	}
	@Override
	public boolean delGroceryList(Long listId) {
		boolean removed = false;
		GroceryList groceryList = findGroceryList(listId);
		if (groceryList != null) {
				entityManager.remove(groceryList);
				removed = true;
		}
		return removed;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Collection<GroceryList> findAllGroceryLists() {
		Query query = entityManager.createQuery("SELECT l FROM GroceryList l");
		return (Collection<GroceryList>) query.getResultList();
	}
	@Override
	public Collection<Product> findAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public GroceryList findGroceryList(Long listId) {
		return entityManager.find(GroceryList.class, listId);		
	}
	
	@PrePassivate
	private void prePassivate(){
	    // Free resources 
	    // ...

	    System.out.println("Passivating EJB.");
	}

	@PostActivate
	private void postActivate(){
	    // Reactivate resources
	    // ...

	    System.out.println("Activating EJB.");
	}
}
