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

    public void insertBooking(int id, int customerId, int day, int time, int month,int endDay, int endMonth, int roomNumber,
                              int guests, int totalPrice, int hotelId, boolean isBooked) {
        String sql = "INSERT INTO Bookings (id, customerId, time, day, month, endDay ,endMonth, roomNumber, guests, totalPrice, hotelId, isBooked) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            pstmt.setInt(6, endDay);
            pstmt.setInt(7, endMonth);
            pstmt.setInt(8, roomNumber);
            pstmt.setInt(9, guests);
            pstmt.setInt(10, totalPrice);
            pstmt.setInt(11, hotelId);
            pstmt.setBoolean(12, isBooked);

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
    
    //delete the provided booking from the table,
    public void deleteBookingById(int bookingId) {      
        String sql = "DELETE FROM Bookings WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Booking with ID " + bookingId + " has been cancelled.");
            } else {
                System.out.println("No booking found with ID: " + bookingId);
            }

        } catch (SQLException e) {
            System.out.println("Error cancelling booking: " + e.getMessage());
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

   
    public void insertUpdate(GUI.BookingBuilder bookingBuilder) {
    int nextId = getNextBookingId();
    Model.Booking booking = bookingBuilder.build(); //Build once

    int customerId = bookingBuilder.getCustomerId(); // More reliable
    int hotelId = bookingBuilder.getHotel().getId();
    int day = booking.getDay();
    int time = booking.getTime();
    int month = booking.getMonth();
    int endDay = booking.getEndDay();
    int endMonth = booking.getEndMonth();
    int roomNumber = booking.getRoomNumber();
    int guests = booking.getGuests();
    int totalPrice = (int) booking.getTotalPrice(); // casting double to int
    boolean isBooked = true;
    
    insertBooking(
        nextId,
        customerId,
        booking.getDay(),
        booking.getTime(),
        booking.getMonth(),
        booking.getEndDay(),
        booking.getEndMonth(),
        booking.getRoomNumber(),
        booking.getGuests(),
        (int) booking.getTotalPrice(), // You can store as double in DB if needed
        hotelId,
        true);
    
    System.out.println("Inserting Booking:");
    System.out.println("ID: " + nextId +
                       ", CustomerID: " + customerId +
                       ", Day: " + day +
                       ", Time: " + time +
                       ", Month: " + month +
                       ", EndDay: " + endDay +
                       ", EndMonth: " + endMonth +
                       ", RoomNumber: " + roomNumber +
                       ", Guests: " + guests +
                       ", TotalPrice: " + totalPrice +
                       ", HotelID: " + hotelId +
                       ", isBooked: " + isBooked);
    }
}