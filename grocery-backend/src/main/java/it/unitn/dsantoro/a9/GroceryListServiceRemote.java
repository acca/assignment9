package it.unitn.dsantoro.a9;

import java.util.Collection;

import javax.ejb.Remote;

import it.unitn.dsantoro.a9.model.GroceryList;
import it.unitn.dsantoro.a9.model.Product;


@Remote
public interface GroceryListServiceRemote {
//	void addProduct(Product product);
//	void removeProduct(String id);	
	GroceryList addGroceryList(String listName);
	boolean delGroceryList(Long listId);
	GroceryList findGroceryList(Long listId);
	Collection<GroceryList> findAllGroceryLists();
	Collection<Product> findAllProducts();	
}
