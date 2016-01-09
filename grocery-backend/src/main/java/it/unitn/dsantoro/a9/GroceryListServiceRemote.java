package it.unitn.dsantoro.a9;

import java.util.Collection;

import javax.ejb.Remote;

import it.unitn.dsantoro.a9.model.GroceryList;
import it.unitn.dsantoro.a9.model.Product;


@Remote
public interface GroceryListServiceRemote {
	// List
	GroceryList addGroceryList(String listName);
	boolean delGroceryList(Long listId);
	GroceryList findGroceryList(Long listId);	
	Collection<GroceryList> findMyGroceryLists();
	
	// Product
	Collection<Product> findAllProducts(Long listId);
	Product addProduct(Product product, Long listId);
	void delProduct(Long prodId, Long listId);
	Product findProduct(Long prodId, Long listId);
	Product updateProduct(Long listId, Long prodId, String prodName, int prodQuantity, double pricePerUnit);
	void markProductAsBought(Long prodId, Long listId);
	void markProductToBuy(Long prodId, Long listId);		
}
