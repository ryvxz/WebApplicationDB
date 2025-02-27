package com.mycompany.webapplicationdb.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles database connections and operations
 *
 */
public class DatabaseHandler
{

    // Database connection parameters
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_NAME = "webappdb";
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    // JDBC URL
    private static final String JDBC_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/"
            + DATABASE_NAME + "?useSSL=false&serverTimezone=UTC";
    
    /**
     * Get a connection to the database
     *
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException
    {
        try
        {
            // Load the JDBC driver
            Class.forName(JDBC_DRIVER);

            // Create and return connection
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

        } catch (ClassNotFoundException e)
        {
            throw new SQLException("JDBC Driver not found: " + e.getMessage());
        }
    }

    /**
     * Close a database connection safely
     *
     * @param connection Connection to close
     */
    public static void closeConnection(Connection connection)
    {
        if (connection != null)
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Test the database connection
     *
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection()
    {
        Connection conn = null;
        try
        {
            conn = getConnection();
            return conn != null;
        } catch (SQLException e)
        {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        } finally
        {
            closeConnection(conn);
        }
    }

    public static void main(String[] args)
    {
        if (testConnection())
        {
            System.out.println("Database connection successful!");
        } else
        {
            System.out.println("Database connection failed!");
        }
    }
}
