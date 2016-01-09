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
	Collection<GroceryList> findAllGroceryLists();	
	
	// Product
	Collection<Product> findAllProducts(Long listId);
	Product addProduct(Product product, Long listId);
	boolean delProduct(Long prodId);
	Product findProduct(Long prodId);
	Product updateProduct(Long prodId, String prodName, int prodQuantity, double pricePerUnit);
	boolean delProductFromCart(Long prodId);
}
