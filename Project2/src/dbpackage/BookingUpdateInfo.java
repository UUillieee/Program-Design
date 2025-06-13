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
        String insertSQL = "INSERT INTO Bookings (id, customerId, time, day, month, endDay ,endMonth, roomNumber, guests, totalPrice, hotelId, isBooked) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        //change rooms availabilty to true 
        String updateRoomSQL = "UPDATE Rooms SET isBooked = true WHERE id = ? AND hotelId = ?";
                
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement insertStmt = conn.prepareStatement(insertSQL);
            PreparedStatement updateStmt = conn.prepareStatement(updateRoomSQL)) {

            if (conn == null) {
                System.out.println("Error: No valid database connection.");
                return;
            }
            insertStmt.setInt(1, id);
            insertStmt.setInt(2, customerId);
            insertStmt.setInt(3, time);
            insertStmt.setInt(4, day);
            insertStmt.setInt(5, month);
            insertStmt.setInt(6, endDay);
            insertStmt.setInt(7, endMonth);
            insertStmt.setInt(8, roomNumber);
            insertStmt.setInt(9, guests);
            insertStmt.setInt(10, totalPrice);
            insertStmt.setInt(11, hotelId);
            insertStmt.setBoolean(12, isBooked);
            
            

            int rowsAffected = insertStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Booking inserted successfully.");
            } else {
                System.out.println("Booking insert failed.");
            }
            
            // Set room availability update parameters
            updateStmt.setInt(1, roomNumber);
            updateStmt.setInt(2, hotelId);

            updateStmt.executeUpdate();
            System.out.println("Room marked as booked");
            
        } catch (SQLException e) {
            System.out.println("Error inserting booking: " + e.getMessage());
        }
    }
    
    //delete the provided booking from the table,
    public void deleteBookingById(int bookingId) {
    //SQL statements
    String selectSQL = "SELECT roomNumber, hotelId FROM Bookings WHERE id = ?";
    String deleteSQL = "DELETE FROM Bookings WHERE id = ?";
    String updateRoomSQL = "UPDATE Rooms SET isBooked = false WHERE id = ? AND hotelId = ?";
    
    //db connection error
    try (Connection conn = DatabaseConnection.getConnection()) {
        if (conn == null) {
            System.out.println("Error: No valid database connection.");
            return;
        }

        //retrieve roomNumber and hotelId
        int roomNumber = -1;
        int hotelId = -1;

        //find booking of the ID
        try (PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
            selectStmt.setInt(1, bookingId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                roomNumber = rs.getInt("roomNumber");
                hotelId = rs.getInt("hotelId");
            } else {
                System.out.println("No booking found with ID: " + bookingId);
                return;
            }
        }

        //delete the booking
        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL)) {
            deleteStmt.setInt(1, bookingId);
            deleteStmt.executeUpdate();
            System.out.println("Booking with ID " + bookingId + " has been cancelled.");
        }

        //update the room availability
        try (PreparedStatement updateStmt = conn.prepareStatement(updateRoomSQL)) {
            updateStmt.setInt(1, roomNumber);
            updateStmt.setInt(2, hotelId);
            updateStmt.executeUpdate();
            System.out.println("Room availability updated to false.");
        }
    } 
        catch (SQLException e) {
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

   
    public void insertUpdate(Model.BookingBuilder bookingBuilder) {
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