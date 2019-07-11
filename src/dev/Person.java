package dev;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Person
 *
 */
@Entity
@Table(schema="personSchema", name="person")
public class Person implements Serializable {

	@Id()
	private Integer Id;
	
	@Column(name="nameCol")
	private String Name;
	
	@Column(name="ageCol")
	private Integer Age;
	
	private static final long serialVersionUID = 1L;

	public Person() {
		super();
	}   
	
	public Integer getId() {
		return this.Id;
	}

	public void setId(Integer Id) {
		this.Id = Id;
	}
	
	public String getName() {
		return this.Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}   
	public Integer getAge() {
		return this.Age;
	}

	public void setAge(Integer Age) {
		this.Age = Age;
	}
}
