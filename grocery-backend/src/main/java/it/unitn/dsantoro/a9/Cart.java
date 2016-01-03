package it.unitn.dsantoro.a9;

import javax.ejb.Stateful;

/**
 * Session Bean implementation class Cart
 */
@Stateful
public class Cart implements CartRemote {
	String products = "";
    /**
     * Default constructor. 
     */
    public Cart() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void addProduct(String product) {
		this.products += product;
	}
	
	public String listProducts(){
		return this.products;
	}
}
