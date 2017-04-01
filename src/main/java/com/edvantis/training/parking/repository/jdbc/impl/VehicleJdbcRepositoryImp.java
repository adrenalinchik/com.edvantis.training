package com.edvantis.training.parking.repository.jdbc.impl;

import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.models.VehicleType;
import com.edvantis.training.parking.repository.VehicleRepository;
import com.edvantis.training.parking.repository.jdbc.AbstractJdbcRepository;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.Set;

import static com.edvantis.training.parking.jdbc.Constants.*;
import static com.edvantis.training.parking.models.VehicleType.*;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
public class VehicleJdbcRepositoryImp extends AbstractJdbcRepository implements VehicleRepository {

    private final Logger logger = Logger.getLogger(VehicleJdbcRepositoryImp.class);


    public VehicleJdbcRepositoryImp(String dbName) {
        super(dbName);
    }

    @Override
    public Vehicle getById(long id) {
        Vehicle vehicle = null;
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(GET_VEHICLE_BY_ID + id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            vehicle = new Vehicle();
            switch (rs.getInt(3)) {
                case 1:
                    vehicle.setCarType(ELECTRO);
                    break;
                case 2:
                    vehicle.setCarType(HIBRID);
                    break;
                case 3:
                    vehicle.setCarType(GASOLINE);
                    break;
                case 4:
                    vehicle.setCarType(DIESEL);
                    break;

            }
            vehicle.setNumber(rs.getString(4));
            vehicle.setModel(rs.getString(5));

        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }

        return vehicle;
    }

    @Override
    public Set<Vehicle> getAll() {
        return null;
    }

    @Override
    public void insert(Vehicle vehicle) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(CREATE_VEHICLE);
            pstmt.setNull(1, Types.INTEGER);
            switch (vehicle.getCarType()) {
                case ELECTRO:
                    pstmt.setInt(2, 1);
                    break;
                case HIBRID:
                    pstmt.setInt(2, 2);
                    break;
                case GASOLINE:
                    pstmt.setInt(2, 3);
                    break;
                case DIESEL:
                    pstmt.setInt(2, 4);
                    break;
            }
            pstmt.setString(3, vehicle.getNumber());
            pstmt.setString(4, vehicle.getModel());
            pstmt.executeUpdate();
            logger.info("Vehicle with " + vehicle.getNumber() + " number is saved successfully.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }

    }

    @Override
    public void update(int vehicleId, Vehicle vehicle) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(UPDATE_VEHICLE);
            switch (vehicle.getCarType()) {
                case ELECTRO:
                    pstmt.setInt(1, 1);
                    break;
                case HIBRID:
                    pstmt.setInt(1, 2);
                    break;
                case GASOLINE:
                    pstmt.setInt(1, 3);
                    break;
                case DIESEL:
                    pstmt.setInt(1, 4);
                    break;
            }
            pstmt.setString(2, vehicle.getNumber());
            pstmt.setString(3, vehicle.getModel());
            pstmt.setInt(4, vehicleId);
            pstmt.executeUpdate();
            logger.info("Vehicle with " + vehicleId + " id updated successfully.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }

    }

    @Override
    public void update(Vehicle vehicle) {

    }

    @Override
    public void delete(long Id) {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(DELETE_VEHICLE);
            pstmt.setLong(1, Id);
            pstmt.executeUpdate();
            logger.info("Vehicle with " + Id + " id removed from db successfully.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }

    }

    public Set<Vehicle> getAllVehiclesByType(VehicleType vehicleType) {
        Set<Vehicle> vehicleSet = new HashSet<>();
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(GET_ALL_VEHICLES_BY_TYPE);
            switch (vehicleType) {
                case ELECTRO:
                    pstmt.setInt(1, 1);
                    break;
                case HIBRID:
                    pstmt.setInt(1, 2);
                    break;
                case GASOLINE:
                    pstmt.setInt(1, 3);
                    break;
                case DIESEL:
                    pstmt.setInt(1, 4);
                    break;
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                switch (rs.getInt(3)) {
                    case 1:
                        vehicle.setCarType(ELECTRO);
                        break;
                    case 2:
                        vehicle.setCarType(HIBRID);
                        break;
                    case 3:
                        vehicle.setCarType(GASOLINE);
                        break;
                    case 4:
                        vehicle.setCarType(DIESEL);
                        break;
                }
                vehicle.setNumber(rs.getString(4));
                vehicle.setModel(rs.getString(5));
                vehicleSet.add(vehicle);
            }
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }

        return vehicleSet;
    }

    public Vehicle getVehicleByNumber(String vehicleNumber) {
        Vehicle vehicle = null;
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(GET_VEHICLE_BY_NUMBER);
            pstmt.setString(1, vehicleNumber);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            vehicle = new Vehicle();
            switch (rs.getInt(3)) {
                case 1:
                    vehicle.setCarType(ELECTRO);
                    break;
                case 2:
                    vehicle.setCarType(HIBRID);
                    break;
                case 3:
                    vehicle.setCarType(GASOLINE);
                    break;
                case 4:
                    vehicle.setCarType(DIESEL);
                    break;
                default:
            }
            vehicle.setNumber(rs.getString(4));
            vehicle.setModel(rs.getString(5));

        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }

        return vehicle;
    }

    public int getVehicleIdByNumber(String vehicleNumber) {
        int vehicleId = 0;
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(GET_VEHICLE_BY_NUMBER);
            pstmt.setString(1, vehicleNumber);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            vehicleId = rs.getInt(1);
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }
        return vehicleId;
    }

}


