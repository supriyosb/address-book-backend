package com.pwc.addressbook.services;

import com.pwc.addressbook.entity.PersonDetails;
import com.pwc.addressbook.entity.PhoneBook;
import com.pwc.addressbook.exceptions.ResourceNotFoundException;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PhoneBookService {
    public static int phoneBookId = 0;
    public static int personDetailsId = 0;

    private List<PhoneBook> phoneBookList = new ArrayList<>(Arrays.asList(
            new PhoneBook(++phoneBookId, "Book1", new ArrayList<>()),
            new PhoneBook(++phoneBookId, "Book2", new ArrayList<>())
    ));

    public List<PhoneBook> getPhoneBookList(){
        return phoneBookList;
    }

    public PhoneBook getPhoneBook(int id){
    	/*PhoneBook phoneBook = null;
    	try {
    		phoneBook =  phoneBookList.stream().filter(t -> t.getId()==id).findFirst().get();
    	} catch (NoSuchElementException ex) {
    		throw new ResourceNotFoundException("Phone book not found with id: " + id);
    	}
        return phoneBook;*/
        return phoneBookList.stream().filter(t -> t.getId()==id).findFirst().get();
    }

    public PhoneBook getPhoneBookByBookName(String bookName){
        return phoneBookList.stream().filter(t -> t.getBookName().equalsIgnoreCase(bookName)).findFirst().get();
    }

    public PersonDetails addPersonDetails(PhoneBook phoneBook, PersonDetails personDetails){
        personDetails.setId(+personDetailsId);
        phoneBook.getPersonDetailsList().add(personDetails);
        return personDetails;
    }

    public List<PersonDetails> fetchUniquePersonFromTwoPhoneBook(PhoneBook phoneBookFirst, PhoneBook phoneBookSecond){
        List<PersonDetails> bookFirstPersonList = phoneBookFirst.getPersonDetailsList();
        List<PersonDetails> bookSecondPersonList = phoneBookSecond.getPersonDetailsList();
        System.out.println("+++++++++++" + bookFirstPersonList);
        System.out.println("+++++++++++" + bookSecondPersonList);
        List<PersonDetails> listOne = new ArrayList<>(bookFirstPersonList);
        List<PersonDetails> listTwo = new ArrayList<>(bookSecondPersonList);

        List<PersonDetails> list = listOne.stream()
                .filter(second -> listTwo.stream()
                        .anyMatch(first -> first.getPersonName().equals(second.getPersonName())
                        )).collect(Collectors.toList());
        System.out.println("========="+list);

        for(PersonDetails personDetails : list){
            PersonDetails p = listOne.stream().filter(t -> t.getPersonName().equals(personDetails.getPersonName())).findFirst().get();
            listOne.remove(p);
        }
        System.out.println("*********"+listOne);
        for(PersonDetails personDetails : list){
            PersonDetails p = listTwo.stream().filter(t -> t.getPersonName().equals(personDetails.getPersonName())).findFirst().get();
            listTwo.remove(p);
        }
        System.out.println("*********"+listTwo);
        listOne.addAll(listTwo);
        return listOne;
    }
}
