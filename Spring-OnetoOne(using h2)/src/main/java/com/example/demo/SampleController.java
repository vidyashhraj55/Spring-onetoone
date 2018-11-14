package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SampleController {

  @Autowired
  sampleService service;

//  @RequestMapping(value="/createNewPetWithNewPerson", method = RequestMethod.POST)
  @GetMapping(value="/createNewPetWithNewPerson")
  public void createNewPetWithNewPerson(Pet pet) {
    service.createNewPetWithNewPerson(pet);;
  }
}