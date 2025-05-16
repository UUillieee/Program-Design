package dbpackage;



import java.sql.*;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author billw
 */
//CHATGPT DEBUGING - to view room table contents for testing
public class RoomCollect {
    public void printAllRooms() {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Error retrieving rooms: No current connection");
            return;
        }

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Rooms");
            System.out.println("Rooms:");
            while (rs.next()) {
                System.out.printf("ID: %d, Hotel ID: %d, Type: %s, Cost: %d, Max Guests: %d, Booked: %b%n",
                    rs.getInt("id"), rs.getInt("hotelId"), rs.getString("type"),
                    rs.getInt("cost"), rs.getInt("maxGuests"), rs.getBoolean("isBooked"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving rooms: " + e.getMessage());
        }
    }
}

