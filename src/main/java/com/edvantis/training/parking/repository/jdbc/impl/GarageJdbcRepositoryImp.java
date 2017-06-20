package com.edvantis.training.parking.repository.jdbc.impl;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.enums.GarageType;
import com.edvantis.training.parking.repository.GarageRepository;
import com.edvantis.training.parking.repository.jdbc.AbstractJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/13/2017.
 */
@Repository
@Profile("jdbc")
public class GarageJdbcRepositoryImp extends AbstractJdbcRepository implements GarageRepository {

    private final Logger logger = LoggerFactory.getLogger(GarageJdbcRepositoryImp.class);

    @Autowired
    public GarageJdbcRepositoryImp(String dbName) {
        super(dbName);
    }

    @Override
    public Garage getById(long id) {
        Garage garage = null;
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM GARAGE WHERE ID=" + id);
            ResultSet rs = pstmt.executeQuery();
            garage = new Garage();
            rs.next();
            switch (rs.getString(2)) {
                case "SMALL":
                    garage.setGarageType(GarageType.SMALL);
                    break;
                case "MEDIUM":
                    garage.setGarageType(GarageType.MEDIUM);
                    break;
                case "BIG":
                    garage.setGarageType(GarageType.BIG);
                    break;
            }
            garage.setSquare(rs.getFloat(3));
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return garage;
    }

    @Override
    public Set<Garage> getAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Set<Garage> getGaragesByParking(long parkingId) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void insert(Garage garage) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("INSERT INTO GARAGE(PARKING_ID, TYPE, SQUARE) VALUES(?,?,?)");
            pstmt.setInt(1, 1);
            switch (garage.getGarageType()) {
                case SMALL:
                    pstmt.setString(2, GarageType.SMALL.toString());
                    break;
                case MEDIUM:
                    pstmt.setString(2, GarageType.MEDIUM.toString());
                    break;
                case BIG:
                    pstmt.setString(2, GarageType.BIG.toString());
                    break;
            }
            pstmt.setFloat(3, garage.getSquare());
            pstmt.executeUpdate();
            logger.info("Garage id={} is saved to db successfully.", garage.getId());
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public void update(long garageId, Garage garage) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("UPDATE GARAGE SET TYPE = ?, SQUARE = ? WHERE ID=?");
            switch (garage.getGarageType()) {
                case SMALL:
                    pstmt.setInt(1, 1);
                    break;
                case MEDIUM:
                    pstmt.setInt(1, 2);
                    break;
                case BIG:
                    pstmt.setInt(1, 3);
                    break;
            }
            pstmt.setFloat(2, garage.getSquare());
            pstmt.setLong(3, garageId);
            pstmt.executeUpdate();
            logger.info("Garage id={} updated successfully.", garage.getId());
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("DELETE FROM GARAGE WHERE ID=?");
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            logger.info("Garage id={} deleted successfully.", id);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    public Set<Garage> getAllGaragesByType(GarageType garageType) {
        Set<Garage> garageSet = new HashSet<>();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM GARAGE WHERE TYPE=?");
            switch (garageType) {
                case SMALL:
                    pstmt.setInt(1, 1);
                    break;
                case MEDIUM:
                    pstmt.setInt(1, 2);
                    break;
                case BIG:
                    pstmt.setInt(1, 3);
                    break;
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Garage garage = new Garage();
                switch (rs.getInt(3)) {
                    case 1:
                        garage.setGarageType(GarageType.SMALL);
                        break;
                    case 2:
                        garage.setGarageType(GarageType.MEDIUM);
                        break;
                    case 3:
                        garage.setGarageType(GarageType.BIG);
                        break;
                }
                garage.setSquare(rs.getInt(4));
                garageSet.add(garage);
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return garageSet;
    }

    public void addParkingIdToGarage(int parkingId, int garageId) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement("UPDATE GARAGE SET PARKING_ID = ? WHERE ID=?");
            pstmt.setInt(1, parkingId);
            pstmt.setInt(2, garageId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }
}
