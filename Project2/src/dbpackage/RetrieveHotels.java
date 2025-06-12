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
public class RetrieveHotels {
    public String[][] getAllHotels() {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Error retrieving hotels: No current connection");
            return new String[0][0]; // return empty array on failure
        }

        String query = "SELECT * FROM Hotels";

        try (Statement stmt = conn.createStatement(
             ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery(query)) {

            // Move to last row to get row count
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst(); // Reset cursor

            String[][] hotels = new String[rowCount][3];
            int i = 0;

            while (rs.next()) {
                hotels[i][0] = String.valueOf(rs.getInt("id"));
                hotels[i][1] = rs.getString("name");
                hotels[i][2] = rs.getString("location");
                i++;
            }
            return hotels;

        } catch (SQLException e) {
            System.out.println("Error retrieving hotels: " + e.getMessage());
            return new String[0][0];
        }
    }

//    Optional test
//    public static void main(String[] args) {
//        RetrieveHotels hotelTable = new RetrieveHotels();
//        String[][] hotels = hotelTable.getAllHotels();
//
//        for (String[] row : hotels) {
//            System.out.printf("ID: %s, Name: %s, Location: %s%n", row[0], row[1], row[2]);
//        }
//    }
}
