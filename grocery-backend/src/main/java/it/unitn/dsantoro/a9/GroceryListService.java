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
		GroceryList gl = entityManager.find(GroceryList.class, listId);
		return gl.getProducts();
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
	public void delProduct(Long prodId, Long listId) {		
		GroceryList gl = entityManager.find(GroceryList.class, listId);
		gl.delProduct(prodId);
		entityManager.persist(gl);		
	}
	@Override
	public Product findProduct(Long prodId, Long listId) {
		GroceryList gl = entityManager.find(GroceryList.class, listId);
		return gl.getProduct(prodId);
	}
	@Override
	public Product updateProduct(Long listId, Long prodId, String prodName, int prodQuantity, double pricePerUnit) {		
		GroceryList gl = entityManager.find(GroceryList.class, listId);
		Product p = gl.getProduct(prodId);		
		p.setName(prodName);
		p.setQuantity(prodQuantity);
		p.setPricePerUnit(pricePerUnit);
		entityManager.persist(gl);
		return new Product();
	}
	
	@Override
	public void markProductAsBought(Long prodId, Long listId) {
		GroceryList gl = entityManager.find(GroceryList.class, listId);
		gl.getProduct(prodId).setInList(false);
		entityManager.persist(gl);
	}
	
	@Override
	public void markProductToBuy(Long prodId, Long listId) {
		GroceryList gl = entityManager.find(GroceryList.class, listId);
		gl.getProduct(prodId).setInList(true);
		entityManager.persist(gl);
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
	@Override
	public Product updateProduct(Product product) {
		GroceryList gl = entityManager.find(GroceryList.class, product.getGroceryList().getId());
		Product p = gl.getProduct(product.getId());
		p.setName(product.getName());
		p.setQuantity(product.getQuantity());
		p.setPricePerUnit(product.getPricePerUnit());
		entityManager.persist(gl);
		return new Product();
	}
}
