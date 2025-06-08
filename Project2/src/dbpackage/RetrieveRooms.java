/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbpackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author billw
 */
public class RetrieveRooms {
    public static List<String[]> getRoomsByHotelId(int hotelId) {
        List<String[]> rooms = new ArrayList<>();
        //prepared statement to retrive the rooms
        String sql = "SELECT Hotels.name, Rooms.type, Rooms.cost, Rooms.maxGuests, Rooms.isBooked " +
                     "FROM Rooms JOIN Hotels ON Rooms.hotelId = Hotels.id " +
                     "WHERE hotelId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, hotelId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String[] row = new String[5];
                //hotel name
                row[0] = rs.getString("name"); 
                //room type
                row[1] = rs.getString("type");
                //cost
                row[2] = String.valueOf(rs.getInt("cost"));
                //max guests
                row[3] = String.valueOf(rs.getInt("maxGuests"));
                //status
                row[4] = rs.getBoolean("isBooked") ? "Booked" : "Available"; 
                rooms.add(row);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving rooms: " + e.getMessage());
        }
        
        //print out hotel ID and amount of rooms under that id
        System.out.println("Rooms for hotel ID " + hotelId + ": " + rooms.size());

        return rooms;
    }
}
