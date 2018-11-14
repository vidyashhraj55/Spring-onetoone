package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class sampleService {

	@Autowired
	PersonRepository personrepository;
	
	@Autowired
	PetRepository petrepository;
	
	public  void createNewPetWithNewPerson(Pet pet) {
		
		Person person=pet.getPerson();
//		if(person==null)
//		{
//			throw new RuntimeException("Expecting .....");
//		}
//		if(pet.getPetId()!=null)
//		{
//			throw new RuntimeException("Does not look like a new entity .....");
//		}
//		//add for persn
//		if(person.getPersonId()!=null)
//		{
//			throw new RuntimeException("Does not look like a new entity .....");
//		}
		pet.setPerson(person);	
		Person savedPerson = personrepository.save(person);
		petrepository.save(pet);

		Pet pet1=new Pet();
		pet1.setName("sonali");
	
		pet1.setPerson(person);
		petrepository.save(pet1);
		
		
		
		
	}
	
	}
