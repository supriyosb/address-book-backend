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

    /**
     * Method to return list of phone books
     * @return
     */
    public List<PhoneBook> getPhoneBookList(){
        return phoneBookList;
    }

    /**
     * Method to return a single phone book object by phone book id
     * @param id
     * @return
     */
    public PhoneBook getPhoneBook(int id){
        return phoneBookList.stream().filter(t -> t.getId()==id).findFirst().get();
    }

    /**
     * Method to return  phone book object by phone book name
     * @param bookName
     * @return
     */
    public PhoneBook getPhoneBookByBookName(String bookName){
        return phoneBookList.stream().filter(t -> t.getBookName().equalsIgnoreCase(bookName)).findFirst().get();
    }

    /**
     * Method to add person name and phone number into a phone book
     * @param phoneBook
     * @param personDetails
     * @return
     */
    public PersonDetails addPersonDetails(PhoneBook phoneBook, PersonDetails personDetails){
        personDetails.setId(+personDetailsId);
        phoneBook.getPersonDetailsList().add(personDetails);
        return personDetails;
    }

    /**
     * Method to compare two phone book and provide a list of uncommon person details inside those two phone book
     * @param phoneBookFirst
     * @param phoneBookSecond
     * @return
     */
    public List<PersonDetails> fetchUniquePersonFromTwoPhoneBook(PhoneBook phoneBookFirst, PhoneBook phoneBookSecond){
        List<PersonDetails> bookFirstPersonList = phoneBookFirst.getPersonDetailsList();
        List<PersonDetails> bookSecondPersonList = phoneBookSecond.getPersonDetailsList();
        List<PersonDetails> listOne = new ArrayList<>(bookFirstPersonList);
        List<PersonDetails> listTwo = new ArrayList<>(bookSecondPersonList);

        List<PersonDetails> list = listOne.stream()
                .filter(second -> listTwo.stream()
                        .anyMatch(first -> first.getPersonName().equals(second.getPersonName())
                        )).collect(Collectors.toList());

        for(PersonDetails personDetails : list){
            PersonDetails p = listOne.stream().filter(t -> t.getPersonName().equals(personDetails.getPersonName())).findFirst().get();
            listOne.remove(p);
        }
        for(PersonDetails personDetails : list){
            PersonDetails p = listTwo.stream().filter(t -> t.getPersonName().equals(personDetails.getPersonName())).findFirst().get();
            listTwo.remove(p);
        }
        listOne.addAll(listTwo);
        Comparator<PersonDetails> comparator = Comparator.comparing(PersonDetails::getPersonName);
        listOne.sort(comparator);
        return listOne;
    }
}
