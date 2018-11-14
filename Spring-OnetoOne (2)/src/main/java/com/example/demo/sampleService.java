package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class sampleService {

	@Autowired
	PersonRepository personrepository;
	
	@Autowired
	PetRepository petrepository;
	
	public void sample() {
		Person person=new Person();
		person.setFirstName("vidya");
		person.setLastName("s");
		Pet pet=new Pet();
		pet.setName("sonu");
		pet.setPerson(person);
		person.setPets(pet);
		personrepository.save(person);
		System.out.println(personrepository);
		petrepository.save(pet);
		
	}
	
	
}
