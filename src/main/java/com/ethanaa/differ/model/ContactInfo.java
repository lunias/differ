package com.ethanaa.differ.model;

import lombok.Data;

import java.util.Set;

@Data
public class ContactInfo {

    private Set<Address> addresses;
    private Set<Email> emails;
    private Set<Phone> phones;
}
