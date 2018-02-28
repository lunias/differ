package com.ethanaa.differ.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Email implements Serializable {

    private String address;
    private int priority;
}
