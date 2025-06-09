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

    public void insertBooking(int id, int customerId, int day, int time, int month, int endMonth, int roomNumber,
                              int guests, int totalPrice, int hotelId, boolean isBooked) {
        String sql = "INSERT INTO Bookings (id, customerId, time, day, month, endMonth, roomNumber, guests, totalPrice, hotelId, isBooked) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.out.println("Error: No valid database connection.");
                return;
            }
            pstmt.setInt(1, id);
            pstmt.setInt(2, customerId);
            pstmt.setInt(3, day);
            pstmt.setInt(4, time);
            pstmt.setInt(5, month);
            pstmt.setInt(6, endMonth);
            pstmt.setInt(7, roomNumber);
            pstmt.setInt(8, guests);
            pstmt.setInt(9, totalPrice);
            pstmt.setInt(10, hotelId);
            pstmt.setBoolean(11, isBooked);

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
    public void insertUpdate(GUI.BookingBuilder bookingBuilder) {
        int nextId = getNextBookingId();

        // Get customer ID
        int customerId = bookingBuilder.getCustomer().getId();

        // Extract booking details from builder
        int day = bookingBuilder.build().getDay();
        int time = bookingBuilder.build().getTime();
        int month = bookingBuilder.build().getMonth();
        int endMonth = bookingBuilder.build().getEndMonth();
        int roomNumber = bookingBuilder.build().getRoomNumber();
        int guests = bookingBuilder.build().getGuests();
        int totalPrice = (int) bookingBuilder.build().getTotalPrice();  // cast if stored as double
        int hotelId = bookingBuilder.getHotel().getId();

        insertBooking(nextId, customerId, day, time, month, endMonth, roomNumber, guests, totalPrice, hotelId, true);
    }
}