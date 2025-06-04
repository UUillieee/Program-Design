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
public class BookingUpdateInfo {

    public void insertBooking(int id, int day, int time, int month, int endMonth, int roomNumber,
                              int guests, int totalPrice, int hotelId, boolean isBooked) {
        String sql = "INSERT INTO Bookings (id, time, day, month, endMonth, roomNumber, guests, totalPrice, hotelId, isBooked) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.out.println("Error: No valid database connection.");
                return;
            }
            pstmt.setInt(1, id);
            pstmt.setInt(2, day);
            pstmt.setInt(3, time);
            pstmt.setInt(4, month);
            pstmt.setInt(5, endMonth);
            pstmt.setInt(6, roomNumber);
            pstmt.setInt(7, guests);
            pstmt.setInt(8, totalPrice);
            pstmt.setInt(9, hotelId);
            pstmt.setBoolean(10, isBooked);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Booking inserted successfully.");
            } else {
                System.out.println("Booking insert failed.");
            }

        } catch (SQLException e) {
            System.out.println("Error inserting booking: " + e.getMessage());
        }
    }

    //get the latest ID put in the booking detail so it can plus 1
    public int getNextBookingId() {
        String query = "SELECT MAX(id) AS max_id FROM Bookings";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            // id++
            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving last booking ID: " + e.getMessage());
        }
        //use 1 if table is empty
        return 1; 
    }

    //test method
    public void insertUpdate(){
        int nextId = getNextBookingId();
        BookingUpdateInfo inserter = new BookingUpdateInfo();
        inserter.insertBooking(nextId, 1, 18, 9, 9, 6, 2, 300, 2, true);
    }
}
