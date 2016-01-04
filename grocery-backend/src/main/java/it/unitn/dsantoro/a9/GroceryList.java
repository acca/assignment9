package it.unitn.dsantoro.a9;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateful;

/**
 * Session Bean implementation class Cart
 */
@Stateful
public class GroceryList implements GroceryListRemote {
	List<Product> content = new ArrayList<Product>();
    /**
     * Default constructor. 
     */
    public GroceryList() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void addProduct(Product product) {
		content.add(product);
	}

	@Override
	public void removeProduct(String id) {
		Iterator<Product> i = content.iterator();
		while (i.hasNext()){
			if (id.equals(i.next().getId())) i.remove();			
		}
	}

	@Override
	public Collection list() {
		return content;
	}

	@Override
	public Collection listInChart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection listAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
