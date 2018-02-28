package com.ethanaa.differ.model;

import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class Person implements Serializable {

    private String prefix;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;

    private LocalDate birthDate;

    private ContactInfo contactInfo;

    public Person() {

    }

    public static Person random(EnhancedRandom enhancedRandom) {

        return enhancedRandom.nextObject(Person.class);
    }
}
