package it.unitn.dsantoro.a9.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String name;
	private String username;
	private String password;
	
	public User() {
		super();
	}
   
}
