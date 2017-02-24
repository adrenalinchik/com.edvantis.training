package com.edvantis.training.parking.jdbc;

import org.apache.log4j.Logger;

import java.sql.*;

import static com.edvantis.training.parking.jdbc.Constants.*;

/**
 * Created by taras.fihurnyak on 2/8/2017.
 */
public class DataBaseJdbcUtil {

    public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private final static Logger logger = Logger.getLogger(DataBaseJdbcUtil.class);


    private DataBaseJdbcUtil() {
    }

    public static void createDb(String dbName, String login, String password) {
        try (Connection connection = getConnection(DATABASE_URL + SSL_CONNECTION_FALSE, login, password)) {
            createDatabase(dbName, connection);
        } catch (SQLException e) {
            logger.warn(e);
        }
    }

    public static void clearDb(String dbName, String login, String password, String [] tableList) {
        try (Connection conn = getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password)) {
            for (int i=0;i<tableList.length;i++) {
                cleanTable(tableList[i], conn);
            }
            logger.info(dbName + " database is cleared.");
        } catch (SQLException e) {
            logger.warn(e);
        }
    }

    public static void dropDb(String dbName, String login, String password) {
        try (Connection connection = getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password)) {
            dropDatabase(dbName, connection);
            logger.info(dbName + " database is deleted.");
        } catch (SQLException e) {
            logger.warn(e);
        }
    }

    private static void createDatabase(String dbName, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(CREATE_DB + dbName);
            connection.setCatalog(dbName);
            //checkTables(connection);
        } catch (Exception e) {
            logger.warn(e);
        }
    }

    public static Connection getConnection(String dbName, String login, String password) {
        Connection connection = null;
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(dbName, login, password);
            logger.info("Connection with " + dbName + " database is established.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
        return connection;
    }

    private static void checkTables(Connection connection) {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            if (!isTableExist(meta, "vehicle")) {
                createTable(connection, CREATE_VEHICLE_TABLE);
                logger.info("Vehicle table is created.");
            }
            if (!isTableExist(meta, "owner")) {
                createTable(connection, CREATE_OWNER_TABLE);
                logger.info("Owner table is created.");
            }
            if (!isTableExist(meta, "garage")) {
                createTable(connection, CREATE_GARAGE_TABLE);
                logger.info("Garage table is created.");
            }
            if (!isTableExist(meta, "parking")) {
                createTable(connection, CREATE_PARKING_TABLE);
                logger.info("parking table is created.");
            }
        } catch (SQLException e) {
            logger.warn(e);
        }

    }

    private static boolean isTableExist(DatabaseMetaData meta, String tableName) throws SQLException {
        ResultSet resultSet = meta.getTables(null, null, tableName.toUpperCase(), null);
        while (resultSet.next()) {
            String name = resultSet.getString("TABLE_NAME");
            if (name.toLowerCase().equals(tableName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private static void createTable(Connection connection, String sqlCreate) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(sqlCreate);
        pstmt.executeUpdate();
    }

    private static void cleanTable(String tableName, Connection connection) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(CLEAN_TABLE + tableName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        }

    }

    private static void dropDatabase(String databaseName, Connection connection) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(DROP_DATABASE + databaseName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        }

    }

}
