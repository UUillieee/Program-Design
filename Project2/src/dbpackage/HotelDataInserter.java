package dbpackage;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author billw
 */

//inserts data into the tables if the tables are empty 
public class HotelDataInserter {
    
    //inserts data into the data base
    //connects to the data base to check if data exists in the tables
    //inserts data if empty
    public void insert() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            //check if there is a valid connection with the database
            if (conn == null) {
                System.out.println("Seeding Error: No valid connection.");
                return;
            }
        //Statement to interact with the database
        try (Statement stmt = conn.createStatement()) {
            //insert if empty 
            if (isHotelTableEmpty(stmt)) {
                insertHotels(stmt);
                insertRooms(stmt);
                }
            }
        } 
        catch (SQLException e) {
        System.out.println("Seeding Error: " + e.getMessage());
        }
    }
    
    //Checks if the hotel table is empty
    private boolean isHotelTableEmpty(Statement stmt) throws SQLException {
        //SQL query that counts the numbeer of rows in the Hotels table
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Hotels");
        rs.next();
        //checks if the table has no rows (0)
        return rs.getInt(1) == 0;
    }    
    
    //SQL statements to insert values into the hotel
    private void insertHotels(Statement stmt) throws SQLException{
        stmt.executeUpdate("INSERT INTO Hotels VALUES (1, 'Azure', 'Auckland')");
        stmt.executeUpdate("INSERT INTO Hotels VALUES (2, 'SkyCity', 'Auckland')");
        stmt.executeUpdate("INSERT INTO Hotels VALUES (3, 'The Crown', 'Wellington')");
        stmt.executeUpdate("INSERT INTO Hotels VALUES (4, 'Celestial', 'Taupo')");
        
        System.out.println("Hotels inserted");
    }
    
    //SQL statements to insert pre defined values into the room table
     private void insertRooms(Statement stmt) throws SQLException {
         //query hotel ID's and names to map hotel names to IDs
        Map<String, Integer> hotelMap = new HashMap<>();
        ResultSet rs = stmt.executeQuery("SELECT id, name FROM Hotels");
        while (rs.next()) {
            hotelMap.put(rs.getString("name"), rs.getInt("id"));
        }
        
        int roomId = 1;
        
        //Each sub-array has: {hotelName, roomType, cost, maxGuests, quantity availible} 
        String[][] roomData = {
            {"Azure", "Penthouse", "500", "5", "1", "false"},
            {"Azure", "Suite", "300", "4", "3", "false"},
            {"Azure", "Single", "150", "5", "30", "false"},
            {"SkyCity", "Penthouse", "500", "5", "10", "false"},
            {"SkyCity", "Suite", "300", "4", "2", "false"},
            {"SkyCity", "Single", "150", "5", "10", "false"},
            {"The Crown", "Penthouse", "500", "5", "4", "false"},
            {"The Crown", "Suite", "300", "5", "3", "false"},
            {"The Crown", "Single", "150", "5", "6", "false"},
            {"Celestial", "Penthouse", "500", "5", "4", "false"},
            {"Celestial", "Suite", "300", "5", "3", "false"},
            {"Celestial", "Single", "150", "5", "7", "false"}
        };
        
        //insert each room into the rooms table 
        for (String[] row : roomData) {
            String hotel = row[0];
            //gt hotel ID using the map
            int hotelId = hotelMap.get(hotel);
            String type = row[1];
            int cost = Integer.parseInt(row[2]);
            int maxGuests = Integer.parseInt(row[3]);
            //insert this number of rooms into the table
            int amount = Integer.parseInt(row[4]); 
            boolean availible = false;
            
            //insert "amount" of rooms of each type into the table
            for (int i = 0; i < amount; i++) {
                stmt.executeUpdate(String.format(
                    "INSERT INTO Rooms VALUES (%d, %d, '%s', %d, %d, %s)", roomId++, hotelId, type, cost, maxGuests, availible));
            }
        }
        System.out.println("Rooms inserted.");
    }
}
