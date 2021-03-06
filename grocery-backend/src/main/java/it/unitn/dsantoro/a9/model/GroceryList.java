package it.unitn.dsantoro.a9.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: GroceryList
 *
 */
@Entity
public class GroceryList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String name;	
	
	@OneToMany(mappedBy="groceryList", cascade={CascadeType.ALL},fetch=FetchType.EAGER)
	private Collection<Product> products;
			
	public GroceryList() {
		super();
		products = new ArrayList<Product> ();		
	}
	
	public GroceryList(String listName) {
		super();
		this.name = listName;
		products = new ArrayList<Product> ();		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public Collection<Product> getProducts() {
		return products;
	}
	
	public Collection<Product> getProducts(boolean toBuy) {
		Collection<Product> products = new ArrayList<Product>();	
		Iterator<Product> ip = this.products.iterator();
		while(ip.hasNext()){			
			Product p = ip.next();			
			if (toBuy == p.isInList()) {
				products.add(p);
			}			
		}
		return products;
	}
	
	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product) {
		product.setGroceryList(this);
		products.add(product);
	}
	
	public Long delProduct(Long productId) {
		Long delId = null;
		Iterator<Product> pi = products.iterator();
		while (pi.hasNext()) {
			Product p = pi.next();
			if (p.getId() == productId) {
				pi.remove();
				delId = productId;
			}			
		}
		return delId;
	}
	
	public Product getProduct(Long productId) {
		Product product = null;
		Iterator<Product> pi = products.iterator();
		while (pi.hasNext()) {
			Product p = pi.next(); 
			if (p.getId() == productId) {
				product = p;				
			}			
		}
		return product;
	}
   
	@Override
	public String toString() {
		return "List name: " + name + ", List id: " + id;
	}
}
