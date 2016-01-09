package it.unitn.dsantoro.a9.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Product
 *
 */
@Entity

public class Product implements Serializable {	
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String name;
	private int quantity = 1;
	private double pricePerUnit;
	private boolean inList = true;	
	
	@ManyToOne
	@JoinColumn(name="idGroceryList",referencedColumnName="id")
	private GroceryList groceryList;
	
	public Product() {
		super();		
	}
	
	public Product(String productName){
		this.name = productName;
	}

	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public boolean isInList() {
		return inList;
	}

	public void setInList(boolean inList) {		
		this.inList = inList;		
	}

	public GroceryList getGroceryList() {
		return groceryList;
	}

	protected void setGroceryList(GroceryList groceryList) {
		this.groceryList = groceryList;
	}
   
	@Override
	public String toString() {
		return "Product name : " + name + ", Product id: " + id;
	}
	
}
