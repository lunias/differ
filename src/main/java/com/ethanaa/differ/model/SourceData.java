package com.ethanaa.differ.model;

import lombok.Data;

@Data
public class SourceData<T> {

    private String sourceId;
    private Source source;
    private T customer;
}
