package com.edvantis.training.parking.serviceJdbcRepository;

import com.edvantis.training.parking.jdbc.ParkingJdbcServiceImpl;
import com.edvantis.training.parking.models.Gender;
import com.edvantis.training.parking.models.Owner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.edvantis.training.parking.jdbc.Constants.*;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
public class OwnerServiceJdbcRepositoryImp implements OwnerServiceJdbcRepository {

    @Override
    public Owner getById(String dbName, String login, String password, int id) {
        Owner owner = null;
        try {
            PreparedStatement pstmt = ParkingJdbcServiceImpl
                    .getConnection(DATABASE_URL + dbName + SSL_CONECTION_FALSE, login, password)
                    .prepareStatement(GET_OWNER_BY_ID + id);
            ResultSet rs = pstmt.executeQuery();
            owner = new Owner();
            rs.next();
            owner.setFirstName(rs.getString(2));
            owner.setLastName(rs.getString(3));
            if (rs.getInt(4) == 1)
                owner.setGender(Gender.MALE);
            else owner.setGender(Gender.FEMALE);
            owner.setDOB(convertDateToOwnerAttribute(rs.getDate(5)));
        } catch (SQLException e) {
            e.getMessage();
        }
        return owner;
    }

    @Override
    public void insert(String dbName, String login, String password, Owner owner) {
        try {
            PreparedStatement pstmt = ParkingJdbcServiceImpl
                    .getConnection(DATABASE_URL + dbName + SSL_CONECTION_FALSE, login, password)
                    .prepareStatement(CREATE_OWNER);
            pstmt.setString(1, owner.getFirstName());
            pstmt.setString(2, owner.getLastName());
            if (owner.getGender().toString().toLowerCase().equals("male")) {
                pstmt.setInt(3, 1);
            } else pstmt.setInt(3, 2);
            pstmt.setDate(4, convertDateToDatabaseColumn(owner.getDOB()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String dbName, String login, String password, int ownerId, Owner owner) {
        try {
            PreparedStatement pstmt = ParkingJdbcServiceImpl
                    .getConnection(DATABASE_URL + dbName + SSL_CONECTION_FALSE, login, password)
                    .prepareStatement(UPDATE_OWNER);
            pstmt.setString(1, owner.getFirstName());
            pstmt.setString(2, owner.getLastName());
            if (owner.getGender().equals(Gender.MALE)) {
                pstmt.setInt(3, 1);
            } else pstmt.setInt(3, 2);
            pstmt.setDate(4, convertDateToDatabaseColumn(owner.getDOB()));
            pstmt.setInt(5, ownerId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String dbName, String login, String password, int ownerId) {
        try {
            PreparedStatement pstmt = ParkingJdbcServiceImpl
                    .getConnection(DATABASE_URL + dbName + SSL_CONECTION_FALSE, login, password)
                    .prepareStatement(DELETE_OWNER);
            pstmt.setInt(1, ownerId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private java.sql.Date convertDateToDatabaseColumn(LocalDate date) {
        return java.sql.Date.valueOf(date);
    }

    private LocalDate convertDateToOwnerAttribute(java.sql.Date databaseValue) {
        return databaseValue.toLocalDate();
    }
}
