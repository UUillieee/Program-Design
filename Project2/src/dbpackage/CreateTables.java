package dbpackage;

import java.sql.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author billw
 */


public class CreateTables {

//if table does not exist call create table methods    
    public void insertTables() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            //hotel table
            if (!tableExists(conn, "HOTELS")) {
                createHotelsTable(stmt);
            }
            
            //room table
            if (!tableExists(conn, "ROOMS")) {
                createRoomsTable(stmt);
            }
            
            //customer bookings table
            if (!tableExists(conn, "BOOKINGS")) {
                createBookingsTable(stmt);
            }
            
            //customer account details
            if (!tableExists(conn, "CUSTOMERS")) {
                createCustomersTable(stmt);
            }

        } catch (SQLException e) {
            System.out.println("Schema Initialization Error: " + e.getMessage());
        }
    }
    
    //check if the table with the specified name exists
    private boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        //ask the metadata to check for tables matched with name.toUpperCase
        try (ResultSet rs = meta.getTables(null, null, tableName.toUpperCase(), null)) {
            //return true if the result set contains any rows, which means table exists 
            return rs.next(); 
        }
    }
    
    private void createHotelsTable(Statement stmt) throws SQLException {
        //SQL statement to create hotel table with rows
        stmt.executeUpdate("""
            CREATE TABLE Hotels (
                id INT PRIMARY KEY,
                name VARCHAR(100),
                location VARCHAR(100)
            )
        """);
        System.out.println("Hotels table created.");
    }

    private void createRoomsTable(Statement stmt) throws SQLException {
        //SQL statement to create room table with rows
        stmt.executeUpdate("""
            CREATE TABLE Rooms (
                id INT PRIMARY KEY,
                hotelId INT,
                type VARCHAR(50),
                cost INT,
                maxGuests INT,
                isBooked BOOLEAN,
                FOREIGN KEY (hotelId) REFERENCES Hotels(id)
            )
        """);
        System.out.println("Rooms table created.");
    }

    private void createBookingsTable(Statement stmt) throws SQLException {
        //SQL statement to create bookings table with rows
        stmt.executeUpdate("""
            CREATE TABLE Bookings (
                id INT PRIMARY KEY,
                customerId INT,
                time INT,
                day INT,
                month INT,
                endDay INT,
                endMonth INT,
                roomNumber INT,
                guests INT,
                totalPrice INT,
                hotelId INT,
                isBooked BOOLEAN,
                FOREIGN KEY (hotelId) REFERENCES Hotels(id)
            )
        """);
        System.out.println("Bookings table created.");
    }

    private void createCustomersTable(Statement stmt) throws SQLException {
        //SQL statement to create Customers table with rows
        stmt.executeUpdate("""
            CREATE TABLE Customers (
                id INT PRIMARY KEY,
                username VARCHAR(100),
                password VARCHAR(100)
            )
        """);
        System.out.println("Customers table created.");
    }
}
