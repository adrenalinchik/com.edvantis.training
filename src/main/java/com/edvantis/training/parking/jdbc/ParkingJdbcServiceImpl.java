package com.edvantis.training.parking.jdbc;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.Owner;
import com.edvantis.training.parking.models.Parking;
import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.repository.impl.GarageServiceJdbcRepositoryImp;
import com.edvantis.training.parking.repository.impl.OwnerServiceJdbcRepositoryImp;
import com.edvantis.training.parking.repository.impl.ParkingServiceJdbcRepositoryImp;
import com.edvantis.training.parking.repository.impl.VehicleServiceJdbcRepositoryImp;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

import static com.edvantis.training.parking.jdbc.Constants.*;

/**
 * Created by taras.fihurnyak on 2/8/2017.
 */
public class ParkingJdbcServiceImpl implements ParkingJdbcService {

    private String dbName;
    private String login;
    private String password;

    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement pstmt = null;
    private final static Logger logger = Logger.getLogger(ParkingJdbcServiceImpl.class);

    public ParkingJdbcServiceImpl(String dbName, String login, String password) {
        this.dbName = dbName;
        this.login = login;
        this.password = password;
    }

    @Override
    public void createDb() {
        Connection connection = getConnection(DATABASE_URL + SSL_CONNECTION_FALSE, login, password);
        createDatabase(dbName, connection);
        disconnectFromDB(connection);

    }

    @Override
    public void populateDb(ArrayList<Object> arrayList) {
        Connection connection = getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password);
        for (Object obj : arrayList) {
            if (obj instanceof Owner) {
                saveOwner((Owner) obj);

            } else if (obj instanceof Vehicle) {
                saveVehicle((Vehicle) obj);

            } else if (obj instanceof Garage) {
                saveGarage((Garage) obj);

            } else if (obj instanceof Parking) {
                saveParking((Parking) obj);
            }
        }

        disconnectFromDB(connection);

    }

    @Override
    public void clearDb(String tableName) {
        Connection connection = getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password);
        cleanTable(tableName, connection);
        disconnectFromDB(connection);
        logger.info(tableName + " table is cleared successfully.");
    }

    @Override
    public void dropDb(String databaseName) {
        Connection connection = getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password);
        dropDatabase(databaseName, connection);
        disconnectFromDB(connection);
        logger.info(dbName + " database is deleted successfully.");
    }

    public static Connection getConnection(String dbName, String login, String password) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbName, login, password);
            logger.info("Connection with " + dbName + " database is established.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
        return connection;
    }

    private void saveVehicle(Vehicle vehicle) {
        new VehicleServiceJdbcRepositoryImp(dbName, login, password).insert(vehicle);

    }

    private void saveOwner(Owner owner) {
        new OwnerServiceJdbcRepositoryImp(dbName, login, password).insert(owner);

    }

    private void saveGarage(Garage garage) {
        new GarageServiceJdbcRepositoryImp(dbName, login, password).insert(garage);

    }

    private void saveParking(Parking parking) {
        new ParkingServiceJdbcRepositoryImp(dbName, login, password).insert(parking);

    }

    private void createDatabase(String dbName, Connection connection) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_DB + dbName);
            connection.setCatalog(dbName);
            checkTables(connection);
        } catch (SQLException e) {
            logger.warn(e);
        } catch (Exception e) {
            logger.error(e);
        }

    }

    private void checkTables(Connection connection) {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            if (!existVehicleTable(meta)) {
                createVehicleTable(connection);
                logger.info("Vehicle table is created.");
            }
            if (!existOwnerTable(meta)) {
                createOwnerTable(connection);
                logger.info("Owner table is created.");
            }
            if (!existGarageTable(meta)) {
                createGarageTable(connection);
                logger.info("Garage table is created.");
            }
            if (!existParkingTable(meta)) {
                createParkingTable(connection);
                logger.info("parking table is created.");
            }
        } catch (SQLException e) {
            logger.warn(e);
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
            logger.warn(e);
        }

    }

    private void dropDatabase(String databaseName, Connection connection) {
        try {
            pstmt = connection.prepareStatement(DROP_DATABASE + databaseName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
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
