package com.edvantis.training.parking.jdbc;

import java.util.ArrayList;

/**
 * Created by taras.fihurnyak on 2/8/2017.
 */
public interface ParkingJdbcService {

    void createDb(String dbName, String login, String password);
    void populateDb(String dbName, String login, String password, ArrayList<Object> arrayList);
    void clearDb(String dbName, String login, String password, String tableName);
    void dropDb(String dbName, String login, String password, String databaseName);
}
