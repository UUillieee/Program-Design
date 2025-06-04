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
public class BookingsTablePrint {
    public void printBookings(){
        String query = "SELECT * FROM Bookings";
        try (Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("=== Bookings Table ===");
            while (rs.next()) {
                System.out.printf(
                    "ID: %d, Time: %d, Day: %d, Month: %d, EndMonth: %d, Room: %d, Guests: %d, Total Price: %d, Hotel ID: %d, Is Booked: %b%n",
                    rs.getInt("id"),
                    rs.getInt("time"),
                    rs.getInt("day"),
                    rs.getInt("month"),
                    rs.getInt("endMonth"),
                    rs.getInt("roomNumber"),
                    rs.getInt("guests"),
                    rs.getInt("totalPrice"),
                    rs.getInt("hotelId"),
                    rs.getBoolean("isBooked")
                );
            }
        } 
        catch (SQLException e) {
            System.out.println("Error reading Bookings table: " + e.getMessage());
        }
    }
}
