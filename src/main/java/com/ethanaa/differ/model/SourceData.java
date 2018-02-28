package com.ethanaa.differ.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SourceData<T extends Serializable> implements Masterable<T> {

    private String sourceId;
    private Source source;
    private T customer;

    @Override
    public String getMasteryKey() {

        return sourceId;
    }
}
