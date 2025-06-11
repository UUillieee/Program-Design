/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbpackage;

import Model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author billw
 */
public class CustomerUpdateInfo {
    public void insertCustomer(String username, String password) {
        String sql = "INSERT INTO Customers (id, username, password) VALUES (?, ?, ?)";
        //connect to the database
        try (Connection conn = DatabaseConnection.getConnection()) {

            //get the next customer ID from the when previously given
            int nextId = getNextCustomerId(conn);

            //insert the values in to the database
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, nextId);
                pstmt.setString(2, username);
                pstmt.setString(3, password);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Customer inserted successfully with ID: " + nextId);
                } else {
                    System.out.println("Customer insert failed.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error inserting booking: " + e.getMessage());
            System.out.println("Error inserting customer details: " + e.getMessage());
        }
    }
    
    public Customer getCustomerByUsername(String username) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT * FROM Customers WHERE username = ?")) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // You can customize how you build your Customer object here
                return new Customer(rs.getInt("id"), rs.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("DB Error: " + e.getMessage());
        }
        return null;
    }   
    
    public Customer getCustomer(String username, String password) {
    String query = "SELECT * FROM Customers WHERE username = ? AND password = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("username");
            return new Customer(id, name); // Assumes your Customer constructor takes id and username
        }

    } catch (SQLException e) {
        System.out.println("Error fetching customer: " + e.getMessage());
    }

    return null; // Login failed
}

    private int getNextCustomerId(Connection conn) throws SQLException {
        String query = "SELECT MAX(id) AS max_id FROM Customers";
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            //return the next id
            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }
        }
        //use 1 if table is empty
        //use 1 if empty
        return 1; 
    }

    public static void main(String[] args) {
        new CustomerUpdateInfo().insertCustomer("newuser", "password123");
    }
}