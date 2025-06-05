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

//CHATGPT DEBUGING - to view hotel table contents for testing
public class HotelTablePrint {
    public void printAllHotels() {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Error retrieving hotels: No current connection");
            return;
        }

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Hotels");
            System.out.println("=== Hotels Table ===");
            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s, Location: %s%n",
                    rs.getInt("id"), rs.getString("name"), rs.getString("location"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving hotels: " + e.getMessage());
        }
    }
}
