package com.edvantis.training.parking.jdbc;

import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.edvantis.training.parking.jdbc.Constants.*;

/**
 * Created by taras.fihurnyak on 2/8/2017.
 */
public class ParkingJdbcServiceImpl implements ParkingJdbcService {

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement pstmt = null;

    @Override
    public void createDb(String dbName, String login, String password) {
        getConnection(DATABASE_URL + SSL_CONECTION_FALSE, login, password);
        createDatabase(dbName);
        checkTables();
        disconnectFromDB();
    }

    @Override
    public void populateDb(String dbName, String login, String password, String tableName, int recordNumber) {
        getConnection(DATABASE_URL + dbName + SSL_CONECTION_FALSE, login, password);
        switch (tableName.toLowerCase()) {
            case "owner":
                generateOwners(recordNumber);
                break;
            case "vehicle":
                if (getOwnerTableRowNumber("owner") != 0) {
                    generateVehicles(recordNumber);
                } else System.out.println("No owner in table. Generate Owners first!");
                break;

        }
        disconnectFromDB();
    }

    @Override
    public void clearDb(String dbName, String login, String password, String tableName) {
        getConnection(DATABASE_URL + dbName + SSL_CONECTION_FALSE, login, password);
        cleanTable(tableName);
        disconnectFromDB();
    }

    @Override
    public void dropDb(String dbName, String login, String password, String databaseName) {
        getConnection(DATABASE_URL + dbName + SSL_CONECTION_FALSE, login, password);
        dropDatabase(databaseName);
        disconnectFromDB();
    }

    private void getConnection(String dbName, String login, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbName, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createDatabase(String dbName) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_DB + dbName);
            connection.setCatalog(dbName);
            checkTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkTables() {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            if (!existVehicleTable(meta)) {
                createVehicleTable(connection);
            }
            if (!existOwnerTable(meta)) {
                createOwnerTable(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    private void createVehicleTable(Connection connection) throws SQLException {
        pstmt = connection.prepareStatement(CREATE_VEHICLE_TABLE);
        pstmt.executeUpdate();
    }

    private void createOwnerTable(Connection connection) throws SQLException {
        pstmt = connection.prepareStatement(CREATE_OWNER_TABLE);
        pstmt.executeUpdate();
    }

    private void generateOwners(int rowNumber) {
        String firstname = "Owner_";
        String lastname = "OwnerLastname_";

        for (int i = 0; i <= rowNumber; i++) {
            addOwnerToTable(CREATE_OWNER, firstname + i, lastname + i, "Male", ThreadLocalRandom.current().nextInt(18, 100 + 1));
            addOwnerToTable(CREATE_OWNER, firstname + "Female_" + i, lastname + "Female_" + i, "Female", ThreadLocalRandom.current().nextInt(18, 100 + 1));
        }

    }

    private void generateVehicles(int rowNumber) {
        String model = "supercar_";
        for (int i = 0; i <= rowNumber; i++) {
            addVehicleToTable(CREATE_VEHICLE, "Electro", ThreadLocalRandom.current().nextInt(1, rowNumber + 1), String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999 + 1)), model + "ELECT");
            addVehicleToTable(CREATE_VEHICLE, "Diesel", ThreadLocalRandom.current().nextInt(1, rowNumber + 1), String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999 + 1)), model + "TDT");
            addVehicleToTable(CREATE_VEHICLE, "GASOLINE", ThreadLocalRandom.current().nextInt(1, rowNumber + 1), String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999 + 1)), model + "4W");
            addVehicleToTable(CREATE_VEHICLE, "HIBRID", ThreadLocalRandom.current().nextInt(1, rowNumber + 1), String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999 + 1)), model + "ECO");
        }

    }

    private int addOwnerToTable(String query, String firstname, String lastname, String gender, int age) {
        int id = 0;
        try {
            pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            if (gender.toLowerCase().equals("male")) {
                pstmt.setInt(3, 1);
            } else pstmt.setInt(3, 2);
            pstmt.setInt(4, age);
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            generatedKeys.next();
            id = generatedKeys.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private int addVehicleToTable(String query, String type, int owner_id, String carNumber, String model) {
        int id = 0;
        try {
            pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            switch (type.toLowerCase()) {
                case "electro":
                    pstmt.setInt(1, 1);
                    break;
                case "hibrid":
                    pstmt.setInt(1, 2);
                    break;
                case "gasoline":
                    pstmt.setInt(1, 3);
                    break;
                case "diesel":
                    pstmt.setInt(1, 4);
                    break;
            }
            pstmt.setInt(2, owner_id);
            pstmt.setString(3, carNumber);
            pstmt.setString(4, model);
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            generatedKeys.next();
            id = generatedKeys.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private int getOwnerTableRowNumber(String tableName) {
        int number = 0;
        try {
            pstmt = connection.prepareStatement(GET_MAX_ROW_NUMBER + tableName);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            number = rs.getInt(1);
            return number;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return number;
    }

    private void cleanTable(String tableName) {
        try {
            pstmt = connection.prepareStatement(CLEAN_TABLE + tableName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void dropDatabase(String databaseName) {
        try {
            pstmt = connection.prepareStatement(DROP_DATABASE + databaseName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void disconnectFromDB() {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
            if (pstmt != null) pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
