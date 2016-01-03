package it.unitn.dsantoro.a9;

import javax.ejb.Remote;

@Remote
public interface CartRemote {
	void addProduct(String product);
	String listProducts();
}
