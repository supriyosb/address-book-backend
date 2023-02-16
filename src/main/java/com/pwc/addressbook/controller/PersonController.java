package com.pwc.addressbook.controller;

import com.pwc.addressbook.entity.PersonDetails;
import com.pwc.addressbook.entity.PhoneBook;
import com.pwc.addressbook.exceptions.ResourceNotFoundException;
import com.pwc.addressbook.services.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PersonController {

    @Autowired
    PhoneBookService phoneBookService;

    @PostMapping("/api/v1/{id}/add-person")
    public PersonDetails addPerson(@PathVariable String id,@Valid @RequestBody PersonDetails personDetails){
        PhoneBook phoneBook = null;
        try {
        	phoneBook = phoneBookService.getPhoneBook(Integer.valueOf(id));
            personDetails.setId(++PhoneBookService.personDetailsId);
            personDetails.setBookName(phoneBook.getBookName());
            phoneBookService.addPersonDetails(phoneBook, personDetails);
        } catch (Exception e) {
        	throw new ResourceNotFoundException("Phone book not found with id: " + id);
        }
        return personDetails;
    }

    @GetMapping("/api/v1/phone-book/get-unique-person")
    public List<PersonDetails> getUniquePersonList(@RequestParam String book1, @RequestParam String book2){
    	PhoneBook phoneBook1 = null;
        PhoneBook phoneBook2 = null;
    	try {
    		phoneBook1 = phoneBookService.getPhoneBookByBookName(book1);
    	} catch(Exception ex) {
    		throw new ResourceNotFoundException("Phone book not found with book name: " + book1);
    	}
    	try {
    		phoneBook2 = phoneBookService.getPhoneBookByBookName(book2);
    	} catch(Exception ex) {
    		throw new ResourceNotFoundException("Phone book not found with book name: " + book2);
    	}
        return phoneBookService.fetchUniquePersonFromTwoPhoneBook(phoneBook1, phoneBook2);
    }
}
