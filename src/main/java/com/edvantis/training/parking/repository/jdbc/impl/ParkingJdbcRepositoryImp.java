package com.edvantis.training.parking.repository.jdbc.impl;

import com.edvantis.training.parking.models.Parking;
import com.edvantis.training.parking.repository.ParkingRepository;
import com.edvantis.training.parking.repository.jdbc.AbstractJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/14/2017.
 */
public class ParkingJdbcRepositoryImp extends AbstractJdbcRepository implements ParkingRepository {

    private final Logger logger = LoggerFactory.getLogger(ParkingJdbcRepositoryImp.class);


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
            if (rs.getInt(3) != 0)
                parking.setFreeGaragesNumber(rs.getInt(3));
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
    public void insert(Parking parking) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("INSERT INTO PARKING(MANAGER_ID, ADDRESS, FREE_GARAGES) VALUES(?,?,?)");
            pstmt.setNull(1, Types.INTEGER);
            pstmt.setString(2, parking.getAddress());
            if (parking.getFreeGaragesNumber() != 0) {
                pstmt.setInt(3, parking.getFreeGaragesNumber());
            } else pstmt.setNull(3, Types.INTEGER);
            pstmt.executeUpdate();
            logger.info("Parking with id={} id saved to db successfully.", parking.getId());
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public void update(long parkingId, Parking parking) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("UPDATE GARAGE SET ADDRESS = ?, FREE_GARAGES = ? WHERE ID=?");
            pstmt.setString(2, parking.getAddress());
            pstmt.setInt(3, parking.getFreeGaragesNumber());
            pstmt.setLong(4, parkingId);
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
}
