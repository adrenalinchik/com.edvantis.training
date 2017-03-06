package com.edvantis.training.parking.jdbc;

import java.lang.String;

/**
 * Created by taras.fihurnyak on 2/8/2017.
 */
public class Constants {

    public static final String CREATE_DB = "CREATE DATABASE IF NOT EXISTS ";
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/";
    public static final String SSL_CONNECTION_FALSE = "?autoReconnect=true&useSSL=false";
    public static final String CREATE_OWNER_TABLE = "CREATE TABLE OWNER (ID INT(11) PRIMARY KEY AUTO_INCREMENT, FIRSTNAME VARCHAR (255), LASTNAME VARCHAR (255), GENDER INT (11), DOB DATE)";
    public static final String CREATE_VEHICLE_TABLE = "CREATE TABLE VEHICLE (ID INT(11) PRIMARY KEY AUTO_INCREMENT, OWNER_ID INT (11), TYPE INT (11), NUMBER VARCHAR (255), MODEL VARCHAR (255))";
    public static final String CREATE_GARAGE_TABLE = "CREATE TABLE GARAGE (ID INT(11) PRIMARY KEY AUTO_INCREMENT, PARKING_ID INT (11), TYPE VARCHAR (10), SQUARE FLOAT)";
    public static final String CREATE_PARKING_TABLE = "CREATE TABLE PARKING (ID INT(11) PRIMARY KEY AUTO_INCREMENT, MANAGER_ID INT (11), ADDRESS VARCHAR (255), FREE_GARAGES INT(11))";
    public static final String CREATE_OWNER = "INSERT INTO OWNER(FIRSTNAME, LASTNAME, GENDER, DOB) VALUES(?,?,?,?)";
    public static final String CREATE_VEHICLE = "INSERT INTO VEHICLE(OWNER_ID,TYPE, NUMBER, MODEL) VALUES(?,?,?,?)";
    public static final String CREATE_GARAGE = "INSERT INTO GARAGE(PARKING_ID, TYPE, SQUARE) VALUES(?,?,?)";
    public static final String CREATE_PARKING = "INSERT INTO PARKING(MANAGER_ID, ADDRESS, FREE_GARAGES) VALUES(?,?,?)";
    public static final String SET_FOREIGN_KEY_CHECKS = "SET FOREIGN_KEY_CHECKS =";
    public static final String CLEAN_TABLE = "TRUNCATE ";
    public static final String DROP_DATABASE = "DROP DATABASE IF EXISTS ";
    public static final String GET_OWNER_BY_ID = "SELECT * FROM OWNER WHERE ID=";
    public static final String GET_VEHICLE_BY_ID = "SELECT * FROM VEHICLE WHERE ID=";
    public static final String GET_GARAGE_BY_ID = "SELECT * FROM GARAGE WHERE ID=";
    public static final String GET_PARKING_BY_ID = "SELECT * FROM PARKING WHERE ID=";
    public static final String UPDATE_OWNER = "UPDATE OWNER SET FIRSTNAME = ?, LASTNAME = ?, GENDER = ?, DOB = ? WHERE ID=?";
    public static final String UPDATE_VEHICLE = "UPDATE VEHICLE SET TYPE = ?, NUMBER = ?, MODEL = ? WHERE ID=?";
    public static final String UPDATE_GARAGE = "UPDATE GARAGE SET TYPE = ?, SQUARE = ? WHERE ID=?";
    public static final String UPDATE_PARKING = "UPDATE GARAGE SET ADDRESS = ?, FREE_GARAGES = ? WHERE ID=?";

    public static final String UPDATE_GARAGE_PARKING = "UPDATE GARAGE SET PARKING_ID = ? WHERE ID=?";
    public static final String DELETE_OWNER = "DELETE FROM OWNER WHERE ID=?";
    public static final String DELETE_VEHICLE = "DELETE FROM VEHICLE WHERE ID=?";
    public static final String DELETE_GRAGE = "DELETE FROM GARAGE WHERE ID=?";
    public static final String DELETE_PARKING = "DELETE FROM PARKING WHERE ID=?";
    public static final String GET_ALL_OWNERS = "SELECT * FROM OWNER";
    public static final String GET_ALL_VEHICLES_BY_TYPE = "SELECT * FROM VEHICLE WHERE TYPE=?";
    public static final String GET_ALL_GARAGES_BY_TYPE = "SELECT * FROM GARAGE WHERE TYPE=?";
    public static final String GET_OWNER_BY_LASTNAME = "SELECT * FROM OWNER WHERE LASTNAME = ?";
    public static final String GET_VEHICLE_BY_NUMBER = "SELECT * FROM VEHICLE WHERE NUMBER=?";


}
