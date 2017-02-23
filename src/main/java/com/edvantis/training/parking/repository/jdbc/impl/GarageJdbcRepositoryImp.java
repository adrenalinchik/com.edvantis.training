package com.edvantis.training.parking.repository.jdbc.impl;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.GarageType;
import com.edvantis.training.parking.repository.GarageJdbcRepository;
import com.edvantis.training.parking.repository.jdbc.AbstractJdbcRepository;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static com.edvantis.training.parking.jdbc.Constants.*;

/**
 * Created by taras.fihurnyak on 2/13/2017.
 */
public class GarageJdbcRepositoryImp extends AbstractJdbcRepository implements GarageJdbcRepository {

    private final Logger logger = Logger.getLogger(GarageJdbcRepositoryImp.class);

    public GarageJdbcRepositoryImp(String dbName, String login, String password) {
        super(dbName, login, password);
    }

    @Override
    public Garage getById(int id) {
        Garage garage = null;

        try {
            PreparedStatement pstmt = getConnection().prepareStatement(GET_GARAGE_BY_ID + id);
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
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }
        return garage;
    }

    @Override
    public void insert(Garage garage) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(CREATE_GARAGE);
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
            logger.info("Garage is saved to db successfully.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void update(int garageId, Garage garage) {

        try {
            PreparedStatement pstmt = getConnection().prepareStatement(UPDATE_GARAGE);
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
            pstmt.setInt(3, garageId);
            pstmt.executeUpdate();
            logger.info("Garage " + garageId + " updated successfully.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }

    }

    @Override
    public void delete(int garageId) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(DELETE_GRAGE);
            pstmt.setInt(1, garageId);
            pstmt.executeUpdate();
            logger.info("Garage with " + garageId + " id removed from db successfully.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public Set<Garage> getAllGaragesByType(GarageType garageType) {
        Set<Garage> garageSet = new HashSet<>();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(GET_ALL_GARAGES_BY_TYPE);
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
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }

        return garageSet;
    }

    public void addParkingIdToGarage(int parkingId, int garageId) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(UPDATE_GARAGE_PARKING);
            pstmt.setInt(1, parkingId);
            pstmt.setInt(2, garageId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        }

    }

}
