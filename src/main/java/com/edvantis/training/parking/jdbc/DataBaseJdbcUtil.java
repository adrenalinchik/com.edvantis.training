package com.edvantis.training.parking.jdbc;

import com.edvantis.training.parking.config.ApplicationConfig;
import com.edvantis.training.parking.config.Util;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by taras.fihurnyak on 2/8/2017.
 */
public class DataBaseJdbcUtil {

    private final static Logger logger = LoggerFactory.getLogger(DataBaseJdbcUtil.class);
    private static Properties prop = new AppProperty().getApplicationProperties();
    private static String url = prop.getProperty("url");
    private static String dbName = prop.getProperty("dbName");

    private DataBaseJdbcUtil() {
    }

    public static void createDb() {
        try (Connection connection = getConnection(url)) {
            if (url.contains("mysql")) {
                createDatabase(dbName, connection);
                Flyway flyway = new ApplicationConfig().getFlywayInstance(url + dbName, prop.getProperty("login"), prop.getProperty("password"));
                flyway.setLocations("classpath:db/migration");
                flyway.clean();
                flyway.migrate();
                logger.info("Tables for {} db are created.", dbName);
            } else if (url.contains("h2")) {
                url = url.replace("test", "");
                logger.info("Database {} is created.", dbName);
                Flyway flyway = new ApplicationConfig().getFlywayInstance(url + dbName, prop.getProperty("login"), prop.getProperty("password"));
                flyway.setLocations("classpath:db/migration");
                flyway.clean();
                flyway.migrate();
            }
        } catch (SQLException e) {
            logger.warn(e.toString());
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
            logger.warn(e.getMessage());
        }
    }

    public static void dropDb() {
        try (Connection connection = getConnection(url + dbName)) {
            dropDatabase(dbName, connection);
            logger.info(dbName + " database is deleted.");
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    public static void dropDB() {
        try (Connection connection = getConnection(url + dbName)) {
            InputStream in = new FileInputStream(
                    new File("src/main/resources/drop_db.sql"));
            Util.importSQL(connection, in);
            logger.info(dbName + " database is deleted.");
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    private static void createDatabase(String dbName, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            connection.setCatalog(dbName);
            logger.info("Database " + dbName + " is created.");
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    public static Connection getConnection(String dbName) {
        Connection connection = null;
        try {
            Class.forName(prop.getProperty("driver"));
            connection = DriverManager.getConnection(dbName, prop.getProperty("login"), prop.getProperty("password"));
            logger.info("Connection with " + dbName + " database is established.");
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        return connection;
    }

    public static DatabaseMetaData getDatabaseMetaData(String dbName, String login, String password) {
        DatabaseMetaData meta = null;
        try (Connection connection = getConnection(url + dbName)) {
            meta = connection.getMetaData();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
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
            PreparedStatement pstmt = connection.prepareStatement("TRUNCATE TABLE " + tableName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }

    }

    public static void dropAllObjects() {
        try (Connection connection = getConnection(url + dbName)) {
            PreparedStatement pstmt = connection.prepareStatement("DROP ALL OBJECTS");
            pstmt.executeUpdate();
            logger.info("All objects are deleted");
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }

    }

    private static void dropDatabase(String databaseName, Connection connection) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("DROP SCHEMA IF EXISTS " + databaseName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }

    }

    private static void setForeignKeyChecks(int checks, Connection connection) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("SET FOREIGN_KEY_CHECKS = " + checks);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }
}
