package com.example.demo;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Person   {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long PersonId;

	private String firstName;
	private String lastName;
	
	@OneToOne(mappedBy="person")
	 private Pet pets;

	
	  public Pet getPets(){
		 return pets;
	 }
	  public void setPets(Pet pets) {
	        this.pets = pets;
	    }
	
	 
	
	
	@Override
	public String toString() {
		return "Person [PersonId=" + PersonId + ", firstName=" + firstName + ", lastName=" + lastName + ", pets=" + pets
				+ "]";
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}