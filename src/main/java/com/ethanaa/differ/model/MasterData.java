package com.ethanaa.differ.model;

import com.ethanaa.differ.DifferApplication;
import lombok.Data;

import java.io.*;
import java.util.UUID;

@Data
public class MasterData<T extends Serializable> implements Masterable<T>, Serializable {

    private UUID enterpriseId;
    private T customer;

    public MasterData() {

        this.enterpriseId = UUID.randomUUID();
    }

    public MasterData(Masterable<T> masterableData) {

        this();

        this.customer = DifferApplication.deepClone(masterableData.getCustomer());
    }

    @Override
    public String getMasteryKey() {

        return enterpriseId.toString();
    }
}
