package com.edvantis.training.parking.repository.jdbc.impl;

import com.edvantis.training.parking.models.Parking;
import com.edvantis.training.parking.models.enums.ModelState;
import com.edvantis.training.parking.repository.ParkingRepository;
import com.edvantis.training.parking.repository.jdbc.AbstractJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/14/2017.
 */
@Repository
@Profile("jdbc")
public class ParkingJdbcRepositoryImp extends AbstractJdbcRepository implements ParkingRepository {

    private final Logger logger = LoggerFactory.getLogger(ParkingJdbcRepositoryImp.class);

    @Autowired
    public ParkingJdbcRepositoryImp(String dbName) {
        super(dbName);
    }

    @Override
    public Parking getById(long id) {
        Parking parking = null;
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM PARKING WHERE ID = " + id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            parking = new Parking();
            parking.setAddress(rs.getString(2));
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return parking;
    }

    @Override
    public Set<Parking> getAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Set<Parking> getActiveOrInactive(ModelState state) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void insert(Parking parking) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("INSERT INTO PARKING(ADDRESS, GARAGES_NUMBER) VALUES(?,?)");
            pstmt.setNull(1, Types.INTEGER);
            pstmt.setString(2, parking.getAddress());
            pstmt.executeUpdate();
            logger.info("Parking with id={} id saved to db successfully.", parking.getId());
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public void update(long parkingId, Parking parking) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("UPDATE GARAGE SET ADDRESS = ? WHERE ID=?");
            pstmt.setString(1, parking.getAddress());
            pstmt.setLong(2, parkingId);
            pstmt.executeUpdate();
            logger.info("Parking with id={} id updated successfully.", parking.getId());
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("DELETE FROM PARKING WHERE ID=?");
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            logger.info("Parking with id={} id removed from db successfully.", id);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public Parking getParkingByAddress(String address) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
