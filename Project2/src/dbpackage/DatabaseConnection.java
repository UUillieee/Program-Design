package dbpackage;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author billw
 */

//manages connection to databas using singleton pattern
public class DatabaseConnection {
    
    //database login details and url
    private static final String USER_NAME = "gc1";//DB username
    private static final String PASSWORD = "gc1";//DB password
    private static final String DB_URL = "jdbc:derby:myDB;create=true";//url of DB host
    
    //static connection objeect to ensure singleton 
    private static Connection conn;
    
    //returns a connection to the database if a connection does not already exist or is closed, it creates a new one
    public static Connection getConnection() {
            try {
                //if a connection does not already exist or is closed it creates a new one
                if (conn == null || conn.isClosed()){
                    conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
                    System.out.println(DB_URL + " Connected successfully.");
                }
            } 
            catch (SQLException ex) {
                System.out.println("Connection failed: " + ex.getMessage());
            }
        return conn;
    }
    
//    public static void closeConnection() {
//        try {
//            if (conn != null && !conn.isClosed()) {
//                conn.close();
//                System.out.println("Database connection closed.");
//            }
//        } catch (SQLException e) {
//            System.out.println("Error closing connection: " + e.getMessage());
//        }
//    }
    
    
}

