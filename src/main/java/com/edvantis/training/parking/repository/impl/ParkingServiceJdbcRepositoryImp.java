package com.edvantis.training.parking.repository.impl;

import com.edvantis.training.parking.jdbc.ParkingJdbcServiceImpl;
import com.edvantis.training.parking.models.Parking;
import com.edvantis.training.parking.repository.ParkingServiceJdbcRepository;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import static com.edvantis.training.parking.jdbc.Constants.*;

/**
 * Created by taras.fihurnyak on 2/14/2017.
 */
public class ParkingServiceJdbcRepositoryImp implements ParkingServiceJdbcRepository {

    private final Logger logger = Logger.getLogger(ParkingServiceJdbcRepositoryImp.class);

    private String dbName;
    private String login;
    private String password;

    public ParkingServiceJdbcRepositoryImp(String dbName, String login, String password) {
        this.dbName = dbName;
        this.login = login;
        this.password = password;
    }

    @Override
    public Parking getById(int parkingId) {
        Parking parking = null;
        try {
            PreparedStatement pstmt = ParkingJdbcServiceImpl
                    .getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password)
                    .prepareStatement(GET_PARKING_BY_ID + parkingId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            parking = new Parking();
            parking.setAddress(rs.getString(2));
            if (rs.getInt(3) != 0)
                parking.setFreeGarages(rs.getInt(3));

        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }

        return parking;
    }

    @Override
    public void insert(Parking parking) {
        try {
            PreparedStatement pstmt = ParkingJdbcServiceImpl
                    .getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password)
                    .prepareStatement(CREATE_PARKING);
            pstmt.setNull(1, Types.INTEGER);
            pstmt.setString(2, parking.getAddress());
            if (parking.getFreeGarages() != 0) {
                pstmt.setInt(3, parking.getFreeGarages());
            } else pstmt.setNull(3, Types.INTEGER);

            pstmt.executeUpdate();
            logger.info("Parking saved to db successfully.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }

    }

    @Override
    public void update(int parkingId, Parking parking) {
        try {
            PreparedStatement pstmt = ParkingJdbcServiceImpl
                    .getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password)
                    .prepareStatement(UPDATE_PARKING);
            pstmt.setString(2, parking.getAddress());
            pstmt.setInt(3, parking.getFreeGarages());
            pstmt.setInt(4, parkingId);
            pstmt.executeUpdate();
            logger.info("Parking with " + parkingId + " id updated successfully.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void delete(int parkingId) {
        try {
            PreparedStatement pstmt = ParkingJdbcServiceImpl
                    .getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password)
                    .prepareStatement(DELETE_PARKING);
            pstmt.setInt(1, parkingId);
            pstmt.executeUpdate();
            logger.info("Parking with " + parkingId + " id removed from db successfully.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }

    }
}
