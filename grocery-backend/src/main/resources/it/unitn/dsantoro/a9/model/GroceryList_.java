package it.unitn.dsantoro.a9.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-01-08T15:55:37.351+0100")
@StaticMetamodel(GroceryList.class)
public class GroceryList_ {
	public static volatile SingularAttribute<GroceryList, Long> id;
	public static volatile SingularAttribute<GroceryList, String> name;
	public static volatile CollectionAttribute<GroceryList, Product> products;
}
