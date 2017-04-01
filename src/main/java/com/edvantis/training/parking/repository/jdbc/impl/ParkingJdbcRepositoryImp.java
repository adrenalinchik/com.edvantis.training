package com.edvantis.training.parking.repository.jdbc.impl;

import com.edvantis.training.parking.models.Parking;
import com.edvantis.training.parking.repository.ParkingRepository;
import com.edvantis.training.parking.repository.jdbc.AbstractJdbcRepository;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Set;

import static com.edvantis.training.parking.jdbc.Constants.*;

/**
 * Created by taras.fihurnyak on 2/14/2017.
 */
public class ParkingJdbcRepositoryImp extends AbstractJdbcRepository implements ParkingRepository {

    private final Logger logger = Logger.getLogger(ParkingJdbcRepositoryImp.class);


    public ParkingJdbcRepositoryImp(String dbName) {
        super(dbName);
    }

    @Override
    public Parking getById(long id) {
        Parking parking = null;
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(GET_PARKING_BY_ID + id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            parking = new Parking();
            parking.setAddress(rs.getString(2));
            if (rs.getInt(3) != 0)
                parking.setFreeGaragesNumber(rs.getInt(3));

        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }

        return parking;
    }

    @Override
    public Set<Parking> getAll() {
        return null;
    }

    @Override
    public void insert(Parking parking) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(CREATE_PARKING);
            pstmt.setNull(1, Types.INTEGER);
            pstmt.setString(2, parking.getAddress());
            if (parking.getFreeGaragesNumber() != 0) {
                pstmt.setInt(3, parking.getFreeGaragesNumber());
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
            PreparedStatement pstmt = getConnection().prepareStatement(UPDATE_PARKING);
            pstmt.setString(2, parking.getAddress());
            pstmt.setInt(3, parking.getFreeGaragesNumber());
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
    public void update(Parking parking) {

    }

    @Override
    public void delete(long parkingId) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(DELETE_PARKING);
            pstmt.setLong(1, parkingId);
            pstmt.executeUpdate();
            logger.info("Parking with " + parkingId + " id removed from db successfully.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }

    }
}
