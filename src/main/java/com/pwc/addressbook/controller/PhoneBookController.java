package com.pwc.addressbook.controller;

import com.pwc.addressbook.entity.PhoneBook;
import com.pwc.addressbook.exceptions.ResourceNotFoundException;
import com.pwc.addressbook.services.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@RestController
public class PhoneBookController {

    @Autowired
    PhoneBookService phoneBookService;

    @GetMapping("/api/v1/get-all-phone-book")
    public List<PhoneBook> phoneBookList(){
        return phoneBookService.getPhoneBookList();
    }

    @GetMapping("/api/v1/phone-book/{id}")
    public PhoneBook getPhoneBook(@PathVariable String id){
    	PhoneBook phoneBook = null;
    	try {
    		phoneBook = phoneBookService.getPhoneBook(Integer.valueOf(id));
    	} catch(Exception e) {
    		throw new ResourceNotFoundException("Phone book not found with id: " + id);
    	}
        return phoneBook;
    }

    @PostMapping("/api/v1/add-phone-book")
    public PhoneBook addPhoneBook(@Valid @RequestBody PhoneBook phoneBook){
    	phoneBook.setId(++PhoneBookService.phoneBookId);
        phoneBook.setPersonDetailsList(new ArrayList<>());
        phoneBookService.getPhoneBookList().add(phoneBook);
        return phoneBook;
    }
}
