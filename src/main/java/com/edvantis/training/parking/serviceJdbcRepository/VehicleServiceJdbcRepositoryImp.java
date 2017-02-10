package com.edvantis.training.parking.serviceJdbcRepository;

import com.edvantis.training.parking.jdbc.ParkingJdbcServiceImpl;
import com.edvantis.training.parking.models.Vehicle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.edvantis.training.parking.jdbc.Constants.*;
import static com.edvantis.training.parking.models.VehicleType.*;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
public class VehicleServiceJdbcRepositoryImp implements VehicleServiceJdbcRepository {

    @Override
    public Vehicle getById(String dbName, String login, String password, int vehicleId) {
        Vehicle vehicle = null;
        try {
            PreparedStatement pstmt = ParkingJdbcServiceImpl
                    .getConnection(DATABASE_URL + dbName + SSL_CONECTION_FALSE, login, password)
                    .prepareStatement(GET_VEHICLE_BY_ID + vehicleId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            vehicle = new Vehicle();
            switch (rs.getInt(2)) {
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
            vehicle.setNumber(rs.getString(3));
            vehicle.setModel(rs.getString(4));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicle;
    }

    @Override
    public void insert(String dbName, String login, String password, Vehicle vehicle) {
        try {
            PreparedStatement pstmt = ParkingJdbcServiceImpl
                    .getConnection(DATABASE_URL + dbName + SSL_CONECTION_FALSE, login, password)
                    .prepareStatement(CREATE_VEHICLE);
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
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(String dbName, String login, String password, int vehicleId, Vehicle vehicle) {
        try {
            PreparedStatement pstmt = ParkingJdbcServiceImpl
                    .getConnection(DATABASE_URL + dbName + SSL_CONECTION_FALSE, login, password)
                    .prepareStatement(UPDATE_VEHICLE);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(String dbName, String login, String password, int vehicleId) {
        try {
            PreparedStatement pstmt = ParkingJdbcServiceImpl
                    .getConnection(DATABASE_URL + dbName + SSL_CONECTION_FALSE, login, password)
                    .prepareStatement(DELETE_VEHICLE);
            pstmt.setInt(1, vehicleId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
