package it.unitn.dsantoro.a9;

import java.io.Serializable;

public class Product implements Serializable {
	private String id;
	private String name;
	private int quantity;
	private double costPerUnit;
	
	public Product(){
		
	}
	
	public Product(String id, String name, int quantity, double costPerUnit){
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.costPerUnit = costPerUnit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public double getCostPerUnit() {
		return costPerUnit;
	}

	public void setCostPerUnit(double costPerUnit) {
		this.costPerUnit = costPerUnit;
	}
}
