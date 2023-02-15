package com.pwc.addressbook.entity;

import java.util.Comparator;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PhoneBook {

    private int id;

    @NotEmpty
    @Size(min=1, message = "Book name is required")
    private String bookName;

    private List<PersonDetails> personDetailsList;

    public PhoneBook(int id, String bookName, List<PersonDetails> personDetailsList) {
        this.id = id;
        this.bookName = bookName;
        this.personDetailsList = personDetailsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public List<PersonDetails> getPersonDetailsList() {
        Comparator<PersonDetails> personDetailsComparator = Comparator.comparing(PersonDetails::getPersonName);
        personDetailsList.sort(personDetailsComparator);
        return personDetailsList;
    }

    public void setPersonDetailsList(List<PersonDetails> personDetailsList) {
        this.personDetailsList = personDetailsList;
    }

    @Override
    public String toString() {
        return "PhoneBook{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
