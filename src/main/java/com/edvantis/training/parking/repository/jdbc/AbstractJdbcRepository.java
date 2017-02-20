package com.edvantis.training.parking.repository.jdbc;

import com.edvantis.training.parking.jdbc.DataBaseJdbcUtil;

import java.sql.Connection;

import static com.edvantis.training.parking.jdbc.Constants.DATABASE_URL;
import static com.edvantis.training.parking.jdbc.Constants.SSL_CONNECTION_FALSE;

/**
 * Created by taras.fihurnyak on 2/20/2017.
 */
public abstract class AbstractJdbcRepository {

    private String dbName;
    private String login;
    private String password;

    public AbstractJdbcRepository(String dbName, String login, String password) {
        this.dbName = dbName;
        this.login = login;
        this.password = password;
    }

    protected Connection getConnection(){
        return DataBaseJdbcUtil.getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password);
    }
}
