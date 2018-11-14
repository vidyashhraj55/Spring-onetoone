package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@SpringBootApplication
@ComponentScan("com.example.demo")
public class OnetoOneApplication  implements CommandLineRunner{
	
	
     @Autowired
     sampleService service;
     
     
    
    	 
	public static void main(String[] args) {
	
		SpringApplication.run(OnetoOneApplication.class, args);
		 
	}

	@Override
	public void run(String... args) throws Exception {
		Person person = new Person();
		person.setFirstName("fn");
		person.setLastName("ln");
		service.createNewPetWithNewPerson(new Pet("sonu",person));
		
		
	}

	
	
}
