package it.unitn.dsantoro.a9.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-01-08T15:55:37.386+0100")
@StaticMetamodel(Product.class)
public class Product_ {
	public static volatile SingularAttribute<Product, Long> id;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, Integer> quantity;
	public static volatile SingularAttribute<Product, Double> pricePerUnit;
	public static volatile SingularAttribute<Product, GroceryList> groceryList;
	public static volatile SingularAttribute<Product, Boolean> inList;
}
