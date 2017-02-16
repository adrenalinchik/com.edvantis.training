package com.edvantis.training.parking.jdbc;

import java.util.ArrayList;

/**
 * Created by taras.fihurnyak on 2/8/2017.
 */
public interface ParkingJdbcService {

    void createDb();
    void populateDb(ArrayList<Object> arrayList);
    void clearDb(String tableName);
    void dropDb(String databaseName);
}
