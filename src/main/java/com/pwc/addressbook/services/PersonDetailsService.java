package com.pwc.addressbook.services;

import com.pwc.addressbook.entity.PersonDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonDetailsService {

    static int personId = 1;

    private List<PersonDetails> personDetailsList = new ArrayList<>();

    public void addPersonDetails(PersonDetails personDetails){
        personDetailsList.add(personDetails);
    }
}
