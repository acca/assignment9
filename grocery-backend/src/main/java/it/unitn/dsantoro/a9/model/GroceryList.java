package it.unitn.dsantoro.a9.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
	
	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product) {
		product.setGroceryList(this);
		products.add(product);
	}
	
	public Long delProduct(Long productId) {
		Long delId = null;
		while (products.iterator().hasNext()) {
			if (products.iterator().next().getId() == productId) {
				products.iterator().remove();
				delId = productId;
			}			
		}
		return delId;
	}
	
	public Product getProduct(Long productId) {
		Product product = null;
		while (products.iterator().hasNext()) {
			if (products.iterator().next().getId() == productId) {
				product = products.iterator().next();				
			}			
		}
		return product;
	}
   
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "List: " + name + ",id: " + id;
	}
}
