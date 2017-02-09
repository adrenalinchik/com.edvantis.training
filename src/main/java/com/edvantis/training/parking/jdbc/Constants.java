package com.edvantis.training.parking.jdbc;

import java.lang.String; /**
 * Created by taras.fihurnyak on 2/8/2017.
 */
public class Constants {

    public static final String CREATE_DB = "CREATE DATABASE IF NOT EXISTS ";
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/";
    public static final String SSL_CONECTION_FALSE = "?autoReconnect=true&useSSL=false";
    public static final String CREATE_OWNER_TABLE = "CREATE TABLE OWNER (ID INT(11) PRIMARY KEY AUTO_INCREMENT, FIRSTNAME VARCHAR (255), LASTNAME VARCHAR (255), GENDER INT (11), AGE INT (11))";
    public static final String CREATE_VEHICLE_TABLE = "CREATE TABLE VEHICLE (ID INT(11) PRIMARY KEY AUTO_INCREMENT, TYPE INT (11), OWNER_ID INT (11),NUMBER VARCHAR (255), MODEL VARCHAR (255))";
    public static final String CREATE_OWNER = "INSERT INTO OWNER(FIRSTNAME, LASTNAME, GENDER, AGE) VALUES(?,?,?,?)";
    public static final String CREATE_VEHICLE = "INSERT INTO VEHICLE(TYPE, OWNER_ID, NUMBER, MODEL) VALUES(?,?,?,?)";
    public static final String GET_MAX_ROW_NUMBER = "SELECT COUNT(*) FROM ";
    public static final String CLEAN_TABLE = "TRUNCATE ";
    public static final String DROP_DATABASE = "DROP DATABASE IF EXISTS ";

}
