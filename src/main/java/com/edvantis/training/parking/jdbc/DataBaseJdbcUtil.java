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

    public static void clearDb(String dbName, String login, String password, String[] tableList) {
        try (Connection conn = getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password)) {
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
            logger.info("Database " + dbName + " is created.");
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

    public static DatabaseMetaData getDatabaseMetaData(String dbName, String login, String password) {
        DatabaseMetaData meta = null;
        try (Connection connection = getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password)) {
            meta = connection.getMetaData();
        } catch (SQLException e) {
            logger.warn(e);
        }
        return meta;
    }

    public static boolean isDbCreated(String dbName, String login, String password) {
        boolean isCreated = false;
        try (Connection connection = getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password)) {
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

    public static boolean isTableExist(String dbName, String login, String password, String[] tableList) {
        try (Connection connection = getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password)) {
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

    public static boolean isTableExist(String dbName, String login, String password, String tableName) {
        try (Connection connection = getConnection(DATABASE_URL + dbName + SSL_CONNECTION_FALSE, login, password)) {
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

    private static void setForeignKeyChecks(int checks, Connection connection) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(SET_FOREIGN_KEY_CHECKS + checks);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        }
    }

}
