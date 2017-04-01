package com.edvantis.training.parking.repository.jdbc;

import com.edvantis.training.parking.jdbc.AppProperty;
import com.edvantis.training.parking.jdbc.DataBaseJdbcUtil;

import java.sql.Connection;

/**
 * Created by taras.fihurnyak on 2/20/2017.
 */
public abstract class AbstractJdbcRepository {

    private String dbName;

    public AbstractJdbcRepository(String dbName) {
        this.dbName = dbName;
    }

    protected Connection getConnection(){
        return DataBaseJdbcUtil.getConnection(new AppProperty().getApplicationProperties().getProperty("url") + dbName);
    }
}
