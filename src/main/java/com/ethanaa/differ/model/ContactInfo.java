package com.ethanaa.differ.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class ContactInfo implements Serializable {

    private Set<Address> addresses;
    private Set<Email> emails;
    private Set<Phone> phones;
}
