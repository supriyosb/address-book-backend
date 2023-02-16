package com.pwc.addressbook;

import com.pwc.addressbook.controller.PhoneBookController;
import com.pwc.addressbook.entity.PersonDetails;
import com.pwc.addressbook.entity.PhoneBook;
import com.pwc.addressbook.services.PhoneBookService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressBookApplicationTest {

    @Spy
    PhoneBookService phoneBookService;

    @InjectMocks
    PhoneBookController phoneBookController;

    @Test
    @Order(1)
    void addPhoneBookTest(){
        PhoneBook phoneBook = new PhoneBook(24534, "Book3", new ArrayList<>());
        PhoneBook response = phoneBookController.addPhoneBook(phoneBook);
        Assertions.assertEquals(response.getBookName(), "Book3");
        Assertions.assertEquals(response.getId(), 3);
    }

    @Test
    @Order(2)
    void getPhoneBookTest(){
        PhoneBook book1 = new PhoneBook(24534, "Book4", new ArrayList<>());
        phoneBookController.addPhoneBook(book1);
        PhoneBook book2 = new PhoneBook(8745, "Book5", new ArrayList<>());
        phoneBookController.addPhoneBook(book2);
        PhoneBook book3 = new PhoneBook(5435, "Book6", new ArrayList<>());
        phoneBookController.addPhoneBook(book3);
        PhoneBook response = phoneBookController.getPhoneBook(String.valueOf(7));
        Assertions.assertEquals(response.getBookName(), "Book5");
    }

    @Test
    @Order(3)
    void getPhoneBookByNameTest(){
        PhoneBook phoneBook = new PhoneBook(423, "Test Book 1", new ArrayList<>());
        phoneBookController.addPhoneBook(phoneBook);
        PhoneBook response = phoneBookService.getPhoneBookByBookName("Test Book 1");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getBookName(), "Test Book 1");
    }

    @Test
    @Order(4)
    void addPersonTest(){
        PhoneBook phoneBook = phoneBookService.getPhoneBookByBookName("Book2");
        PersonDetails personDetails = new PersonDetails(234, "John", "9876543210");
        PersonDetails response = phoneBookService.addPersonDetails(phoneBook, personDetails);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getPersonName(), "John");
        personDetails = new PersonDetails(534, "Snow", "1234567890");
        response = phoneBookService.addPersonDetails(phoneBook, personDetails);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getPhoneNumber(), "1234567890");
        Assertions.assertEquals(phoneBookService.getPhoneBookList().get(1).getPersonDetailsList().size(), 2);
    }

    @Test
    @Order(5)
    void uniquePersonTest(){
        PersonDetails personDetails = null;
        PhoneBook book1 = phoneBookService.getPhoneBookByBookName("Book1");
        personDetails = new PersonDetails(234, "John", "9876543210");
        phoneBookService.addPersonDetails(book1, personDetails);
        personDetails = new PersonDetails(111, "Snow", "9876543210");
        phoneBookService.addPersonDetails(book1, personDetails);
        personDetails = new PersonDetails(222, "Arya", "9876543210");
        phoneBookService.addPersonDetails(book1, personDetails);
        personDetails = new PersonDetails(333, "Stark", "9876543210");
        phoneBookService.addPersonDetails(book1, personDetails);

        PhoneBook book2 = phoneBookService.getPhoneBookByBookName("Book2");
        personDetails = new PersonDetails(444, "White", "9876543210");
        phoneBookService.addPersonDetails(book2, personDetails);
        personDetails = new PersonDetails(555, "Brian", "9876543210");
        phoneBookService.addPersonDetails(book2, personDetails);
        personDetails = new PersonDetails(666, "Stark", "9876543210");
        phoneBookService.addPersonDetails(book2, personDetails);
        personDetails = new PersonDetails(777, "John", "9876543210");
        phoneBookService.addPersonDetails(book2, personDetails);

        List<PersonDetails> response = phoneBookService.fetchUniquePersonFromTwoPhoneBook(book1, book2);
        Assertions.assertTrue(response.stream().anyMatch(p -> p.getPersonName().equals("Snow")));
        Assertions.assertTrue(response.stream().anyMatch(p -> p.getPersonName().equals("Arya")));
        Assertions.assertTrue(response.stream().anyMatch(p -> p.getPersonName().equals("White")));
        Assertions.assertTrue(response.stream().anyMatch(p -> p.getPersonName().equals("Brian")));
        Assertions.assertFalse(response.stream().anyMatch(p -> p.getPersonName().equals("John")));
        Assertions.assertFalse(response.stream().anyMatch(p -> p.getPersonName().equals("Stark")));
    }
}
