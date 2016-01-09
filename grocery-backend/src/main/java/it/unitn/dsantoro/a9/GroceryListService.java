package it.unitn.dsantoro.a9;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
	
	ArrayList<GroceryList> groceryLists;
	
    public GroceryListService() {
    	groceryLists = new ArrayList<GroceryList>(); 
    }
	@Override
	public GroceryList addGroceryList(String listName) {
		GroceryList groceryList = new GroceryList();
		groceryList.setName(listName);
		groceryLists.add(groceryList);
		entityManager.persist(groceryList);
		return groceryList;
	}
	@Override
	public boolean delGroceryList(Long listId) {
		boolean removed = false;
		GroceryList groceryList = findGroceryList(listId);
		if (groceryList != null) {
				delListFromSession(listId);
				entityManager.remove(groceryList);
				removed = true;
		}
		return removed;
	}
	@Override
	public Collection<GroceryList> findMyGroceryLists() {
		return groceryLists;
	}
	@SuppressWarnings("unchecked")	
	private Collection<GroceryList> findAllGroceryLists() {
		Query query = entityManager.createQuery("SELECT l FROM GroceryList l");
		return (Collection<GroceryList>) query.getResultList();
	}
	@Override
	public GroceryList findGroceryList(Long listId) {
		return entityManager.find(GroceryList.class, listId);		
	}
	
	@Override
	public Collection<Product> findAllProducts(Long listId) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	@Override
	public Product addProduct(Product product, Long listId) {
		Product p = null;
		GroceryList gList  = findGroceryList(listId);
		if ( (gList != null) && (!product.getName().equals("")) ) {
			p = product;
		}
		gList.addProduct(product);
		entityManager.persist(gList);
		return p;
	}
	@Override
	public boolean delProduct(Long prodId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Product findProduct(Long prodId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Product updateProduct(Long prodId, String prodName, int prodQuantity, double pricePerUnit) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean delProductFromCart(Long prodId) {
		// TODO Auto-generated method stub
		return false;
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
	
	private void delListFromSession(Long listId) {		
		Iterator<GroceryList> i = groceryLists.iterator(); 
		while (i.hasNext()){
			if (i.next().getId() == listId) {
				i.remove();
			}
		}
	}
}
