package com.edvantis.training.parking.jdbc;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.repository.GarageServiceJdbcRepositoryImp;
import com.edvantis.training.parking.repository.OwnerServiceJdbcRepositoryImp;
import com.edvantis.training.parking.repository.VehicleServiceJdbcRepositoryImp;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

import static com.edvantis.training.parking.jdbc.Constants.*;

/**
 * Created by taras.fihurnyak on 2/8/2017.
 */
public class ParkingJdbcServiceImpl implements ParkingJdbcService {

    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement pstmt = null;
    private final static Logger logger = Logger.getLogger(ParkingJdbcServiceImpl.class);

    @Override
    public void createDb(String dbName, String login, String password) {
        Connection connection = getConnection(DATABASE_URL + SSL_CONNECTION_FALSE, login, password);
        createDatabase(dbName, connection);
        disconnectFromDB(connection);
    }

    @Override
    public void populateDb(String dbName, String login, String password, ArrayList<Object> arrayList) {
        Connection connection = getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password);
        if (arrayList.get(0) instanceof Owner) {
            for (Object i : arrayList) {
                generateOwners(dbName, login, password, (Owner) i);
            }

        } else if (arrayList.get(0) instanceof Vehicle) {
            for (Object i : arrayList) {
                generateVehicles(dbName, login, password, (Vehicle) i);
            }
        } else if (arrayList.get(0) instanceof Garage) {
            for (Object i : arrayList) {
                generateGarages(dbName, login, password, (Garage) i);
            }
        }

        disconnectFromDB(connection);
    }

    @Override
    public void clearDb(String dbName, String login, String password, String tableName) {
        Connection connection = getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password);
        cleanTable(tableName, connection);
        disconnectFromDB(connection);
    }

    @Override
    public void dropDb(String dbName, String login, String password, String databaseName) {
        Connection connection = getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password);
        dropDatabase(databaseName, connection);
        disconnectFromDB(connection);
    }

    public static Connection getConnection(String dbName, String login, String password) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbName, login, password);
        } catch (SQLException e) {
            logger.error(e);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
        return connection;
    }

    private void generateVehicles(String dbName, String login, String password, Vehicle vehicle) {

        VehicleServiceJdbcRepositoryImp.getInstance().insert(dbName, login, password, vehicle);


    }

    private void generateOwners(String dbName, String login, String password, Owner owner) {

        OwnerServiceJdbcRepositoryImp.getInstance().insert(dbName, login, password, owner);

    }

    private void generateGarages(String dbName, String login, String password, Garage garage) {

        GarageServiceJdbcRepositoryImp.getInstance().insert(dbName, login, password, garage);

    }

    private void createDatabase(String dbName, Connection connection) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_DB + dbName);
            connection.setCatalog(dbName);
            checkTables(connection);
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    private void checkTables(Connection connection) {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            if (!existVehicleTable(meta)) {
                createVehicleTable(connection);
            }
            if (!existOwnerTable(meta)) {
                createOwnerTable(connection);
            }
            if (!existGarageTable(meta)) {
                createGarageTable(connection);
            }
            if (!existParkingTable(meta)) {
                createParkingTable(connection);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    private boolean existVehicleTable(DatabaseMetaData meta) throws SQLException {
        resultSet = meta.getTables(null, null, "VEHICLE", null);
        while (resultSet.next()) {
            String name = resultSet.getString("TABLE_NAME");
            if (name.equals("vehicle")) {
                return true;
            }
        }
        return false;
    }

    private boolean existGarageTable(DatabaseMetaData meta) throws SQLException {
        resultSet = meta.getTables(null, null, "GARAGE", null);
        while (resultSet.next()) {
            String name = resultSet.getString("TABLE_NAME");
            if (name.equals("garage")) {
                return true;
            }
        }
        return false;
    }

    private boolean existOwnerTable(DatabaseMetaData meta) throws SQLException {
        resultSet = meta.getTables(null, null, "OWNER", null);
        while (resultSet.next()) {
            String name = resultSet.getString("TABLE_NAME");
            if (name.equals("owner")) {
                return true;
            }
        }
        return false;
    }

    private boolean existParkingTable(DatabaseMetaData meta) throws SQLException {
        resultSet = meta.getTables(null, null, "PARKING", null);
        while (resultSet.next()) {
            String name = resultSet.getString("TABLE_NAME");
            if (name.equals("parking")) {
                return true;
            }
        }
        return false;
    }

    private void createVehicleTable(Connection connection) throws SQLException {
        pstmt = connection.prepareStatement(CREATE_VEHICLE_TABLE);
        pstmt.executeUpdate();
    }

    private void createOwnerTable(Connection connection) throws SQLException {
        pstmt = connection.prepareStatement(CREATE_OWNER_TABLE);
        pstmt.executeUpdate();
    }

    private void createGarageTable(Connection connection) throws SQLException {
        pstmt = connection.prepareStatement(CREATE_GARAGE_TABLE);
        pstmt.executeUpdate();
    }

    private void createParkingTable(Connection connection) throws SQLException {
        pstmt = connection.prepareStatement(CREATE_PARKING_TABLE);
        pstmt.executeUpdate();
    }

    private void cleanTable(String tableName, Connection connection) {
        try {
            pstmt = connection.prepareStatement(CLEAN_TABLE + tableName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }

    }

    private void dropDatabase(String databaseName, Connection connection) {
        try {
            pstmt = connection.prepareStatement(DROP_DATABASE + databaseName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }

    }

    private void disconnectFromDB(Connection connection) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
            if (pstmt != null) pstmt.close();
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
