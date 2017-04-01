package com.edvantis.training.parking.jdbc;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Properties;

/**
 * Created by taras.fihurnyak on 2/8/2017.
 */
public class DataBaseJdbcUtil {

    private final static Logger logger = Logger.getLogger(DataBaseJdbcUtil.class);
    private static Properties prop = new AppProperty().getApplicationProperties();
    private static String url = prop.getProperty("url");
    private static String dbName = prop.getProperty("dbName");

    private DataBaseJdbcUtil() {
    }

    public static void createDb() {
        try (Connection connection = getConnection(url)) {
            if (url.contains("mysql"))
                createDatabase(dbName, connection);
            else if (url.contains("h2")) {
                url = url.replace("test", "");
                logger.info("Database " + dbName + " is created.");
            }
        } catch (SQLException e) {
            logger.warn(e);
        }
    }

    public static void clearDb(String[] tableList) {
        try (Connection conn = getConnection(url + dbName)) {
            setForeignKeyChecks(0, conn);
            for (int i = 0; i < tableList.length; i++) {
                cleanTable(tableList[i], conn);
            }
            setForeignKeyChecks(1, conn);
            logger.info(dbName + " database is cleared.");
        } catch (SQLException e) {
            logger.warn(e);
        }
    }

    public static void dropDb() {
        try (Connection connection = getConnection(url + dbName)) {
            dropDatabase(dbName, connection);
            logger.info(dbName + " database is deleted.");
        } catch (SQLException e) {
            logger.warn(e);
        }
    }

    private static void createDatabase(String dbName, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(Constants.CREATE_DB + dbName);
            connection.setCatalog(dbName);
            logger.info("Database " + dbName + " is created.");
        } catch (Exception e) {
            logger.warn(e);
        }
    }

    public static Connection getConnection(String dbName) {
        Connection connection = null;
        try {
            //String dbName = prop.getProperty("dbName");
            Class.forName(prop.getProperty("driver"));
            connection = DriverManager.getConnection(dbName, prop.getProperty("login"), prop.getProperty("password"));
            logger.info("Connection with " + dbName + " database is established.");
        } catch (SQLException e) {
            logger.warn(e);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
        return connection;
    }

    public static DatabaseMetaData getDatabaseMetaData(String dbName, String login, String password) {
        DatabaseMetaData meta = null;
        try (Connection connection = getConnection(url + dbName)) {
            meta = connection.getMetaData();
        } catch (SQLException e) {
            logger.warn(e);
        }
        return meta;
    }

    public static boolean isDbCreated(String dbName) {
        boolean isCreated = false;
        try (Connection connection = getConnection(url)) {
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                String dbUrl = meta.getURL();
                isCreated = dbUrl.contains(dbName);
            }
        } catch (SQLNonTransientConnectionException e) {
            return isCreated;
        } catch (SQLException e) {
            return isCreated;
        }
        return isCreated;
    }

    public static boolean isTableExist(String dbName, String[] tableList) {
        try (Connection connection = getConnection(url + dbName)) {
            ResultSet resultSet = connection.getMetaData().getTables(null, null, "%", null);
            while (resultSet.next()) {
                for (String s : tableList) {
                    String name = resultSet.getString("TABLE_NAME");
                    if (name.toLowerCase().equals(s.toLowerCase())) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public static boolean isTableExist(String dbName, String tableName) {
        try (Connection connection = getConnection(url + dbName)) {
            ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null);
            while (resultSet.next()) {
                String name = resultSet.getString("TABLE_NAME");
                if (name.toLowerCase().equals(tableName.toLowerCase())) {
                    return true;
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    private static void cleanTable(String tableName, Connection connection) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(Constants.CLEAN_TABLE + tableName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        }

    }

    public static void dropAllObjects() {
        try (Connection connection = getConnection(url + dbName)) {
            PreparedStatement pstmt = connection.prepareStatement(Constants.DROP_ALL_OBJECTS);
            pstmt.executeUpdate();
            logger.info("All objects are deleted");
        } catch (SQLException e) {
            logger.warn(e);
        }

    }

    private static void dropDatabase(String databaseName, Connection connection) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(Constants.DROP_DATABASE + databaseName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        }

    }

    private static void setForeignKeyChecks(int checks, Connection connection) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(Constants.SET_FOREIGN_KEY_CHECKS + checks);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        }
    }
}
