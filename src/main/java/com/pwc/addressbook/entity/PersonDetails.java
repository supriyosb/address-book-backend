package com.pwc.addressbook.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PersonDetails {

    private int id;

    @NotEmpty
    @NotBlank(message = "Not a valid name")
    @Size(min=4, message = "Person name should be more than 3 character")
    private String personName;

    @NotEmpty
    @Pattern(regexp = "^(\\+\\d{1,2}\\s?)?1?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", message = "Not a valid phone number")
    private String phoneNumber;

    public PersonDetails(int id, String personName, String phoneNumber) {
        this.id = id;
        this.personName = personName;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "PersonDetails{" +
                "id=" + id +
                ", personName='" + personName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
