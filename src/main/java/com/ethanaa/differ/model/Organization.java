package com.ethanaa.differ.model;

import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Organization {

    private String name;
    private LocalDate firstContactedAt;

    private ContactInfo contactInfo;

    public Organization() {

    }

    public static Organization random(EnhancedRandom enhancedRandom) {

        return enhancedRandom.nextObject(Organization.class);
    }
}
