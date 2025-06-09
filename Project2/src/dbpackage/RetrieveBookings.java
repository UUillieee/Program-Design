/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbpackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RetrieveBookings {

    //return all bookings in the db
    public static List<Object[]> getAllBookings() {
        List<Object[]> bookings = new ArrayList<>();
        
       //get the hotel name instead of ID, this is done by creating a join between the two tables
       String sql = "SELECT B.id, B.customerId, H.name AS hotelName, B.time, B.day, B.month, B.endMonth, " +
                 "B.roomNumber, B.guests, B.totalPrice, B.isBooked " +
                 "FROM Bookings B " +
                 "JOIN Hotels H ON B.hotelId = H.id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[7];
                //hotel ID
                row[0] = rs.getInt("id");
                //customer ID
                row[1] = rs.getInt("customerId");
                //hotel Name
                row[2] = rs.getString("hotelName");
                //start day and start month (check in)
                row[3] = rs.getInt("day") + "/" + rs.getInt("month");  
                //endday and end month (check out)
                row[4] = rs.getInt("day") + "/" + rs.getInt("endMonth"); 
                //room number
                row[5] = "Room #" + rs.getInt("roomNumber");
                //total price
                row[6] = "$" + rs.getInt("totalPrice");
                //availibility
                row[7] = rs.getBoolean("isBooked") ? "Confirmed" : "Pending";
                bookings.add(row);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving bookings: " + e.getMessage());
        }
        //send object to UserDashboardPanel
        return bookings;
    }
    
    //return bookings of the customer ID
    public static List<Object[]> getBookingsByCustomerId(int customerId) {
    List<Object[]> bookings = new ArrayList<>();
    String query = "SELECT * FROM Bookings WHERE customer_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, customerId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Object[] row = new Object[]{
                rs.getInt("booking_id"),
                rs.getInt("customer_id"),
                rs.getString("hotel_name"),
                rs.getString("check_in_date"),
                rs.getString("check_out_date"),
                rs.getString("room_type"),
                rs.getDouble("total_cost"),
                rs.getString("status")
            };
            bookings.add(row);
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving bookings: " + e.getMessage());
    }

    return bookings;
}
}

