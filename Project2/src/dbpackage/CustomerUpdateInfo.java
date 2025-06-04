/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbpackage;

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
            if (conn == null) {
                System.out.println("Error: No valid database connection.");
                return;
            }
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
            System.out.println("Error inserting customer details: " + e.getMessage());
        }
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
        //use 1 if empty
        return 1; 
    }
    
    //test method
    public static void main(String[] args) {
        new CustomerUpdateInfo().insertCustomer("newuser", "password123");
    }
}
