package com.ethanaa.differ.model;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
public class MasterData<T> {

    private UUID enterpriseId;
    private T customer;

    public MasterData() {

        this.enterpriseId = UUID.randomUUID();
    }

    public MasterData(SourceData<T> sourceData) {

        this();

        this.customer = sourceData.getCustomer();
    }

    public MasterData(MasterData<T> masterData) {

        BeanUtils.copyProperties(masterData, this);
    }
}
