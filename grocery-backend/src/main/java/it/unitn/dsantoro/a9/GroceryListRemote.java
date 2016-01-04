package it.unitn.dsantoro.a9;

import java.util.Collection;

import javax.ejb.Remote;

@Remote
public interface GroceryListRemote {
	void addProduct(Product product);
	void removeProduct(String id);
	Collection list();
	Collection listInChart();
	Collection listAll();
}
