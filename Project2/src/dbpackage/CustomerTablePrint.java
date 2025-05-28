

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbpackage;

import java.sql.*;
/**
 *
 * @author billw
 */
public class CustomerTablePrint {
    public void printCustomer(){
        String query = "SELECT * FROM Customers";
        try (Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("=== Customers Table ===");
            while (rs.next()) {
                System.out.printf(
                    "ID: %d, Username: %s, Password: %s%n",
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        } 
        catch (SQLException e) {
            System.out.println("Error reading Customers table: " + e.getMessage());
        }
    }
}
