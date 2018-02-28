package com.ethanaa.differ.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Phone implements Serializable {

    private String number;
    private int priority;
}
