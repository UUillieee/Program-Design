
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author gcoll
 * @author William Bindon
 */

public class DatabaseManager {

    private static final String USER_NAME = "gc1";//DB username
    private static final String PASSWORD = "gc1";//DB password
    private static final String dbURL = "jdbc:derby:myDB;create=true";//url of DB host

    Connection conn;

    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.insertTables();
        
        
        //CHAT GPT - RUN METHOD IF TESTING TO SEE DATABASE TABLE CONTENT
        dbManager.printHotels();
        dbManager.printRooms();
    }

    public DatabaseManager() {
        establishConnection();
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void establishConnection() {
        if (this.conn == null) {
            try {
                conn = DriverManager.getConnection(dbURL, USER_NAME, PASSWORD);
                System.out.println(dbURL + " Connected successfully.");
            } catch (SQLException ex) {
                System.out.println("Connection failed: " + ex.getMessage());
            }
        }
    }

    public void insertTables() {
        try (Statement stmt = conn.createStatement()) {
            //Drop existing Rooms table
            try {
                stmt.executeUpdate("DROP TABLE Rooms");
                System.out.println("Rooms table dropped.");
            } catch (SQLException ex) {
                if (!"42Y55".equals(ex.getSQLState())) {
                    System.out.println("Error dropping Rooms table: " + ex.getMessage());
                }
            }
            //Create Hotels table
            try {
                stmt.executeUpdate("""
                    CREATE TABLE Hotels (
                        id INT PRIMARY KEY,
                        name VARCHAR(100),
                        location VARCHAR(100)
                    )
                """);
                System.out.println("Hotels table created.");
            } catch (SQLException ex) {
                if (!"X0Y32".equals(ex.getSQLState())) {
                    System.out.println("Error creating Hotels table: " + ex.getMessage());
                } else {
                    System.out.println("Hotels table already exists.");
                }
            }
            //Create Rooms table
            try {
                stmt.executeUpdate("""
                    CREATE TABLE Rooms (
                        id INT PRIMARY KEY,
                        hotelId INT,
                        type VARCHAR(50),
                        cost INT,
                        maxGuests INT,
                        isBooked BOOLEAN,
                        FOREIGN KEY (hotelId) REFERENCES Hotels(id)
                    )
                """);
                System.out.println("Rooms table created.");
            } catch (SQLException ex) {
                if (!"X0Y32".equals(ex.getSQLState())) {
                    System.out.println("Error creating Rooms table: " + ex.getMessage());
                } else {
                    System.out.println("Rooms table already exists.");
                }
            }
            //Create Bookings table
            try {
                stmt.executeUpdate("""
                    CREATE TABLE Bookings (
                        id INT PRIMARY KEY,
                        time INT,
                        month INT,
                        endMonth INT,
                        roomNumberBooked INT,
                        guests INT,
                        totalPrice INT,
                        hotelId INT,
                        isBooked BOOLEAN,
                        FOREIGN KEY (hotelId) REFERENCES Hotels(id)
                    )
                """);
                System.out.println("Bookings table created.");
            } catch (SQLException ex) {
                if (!"X0Y32".equals(ex.getSQLState())) {
                    System.out.println("Error creating Bookings table: " + ex.getMessage());
                } else {
                    System.out.println("Bookings table already exists.");
                }
            }

            //Create Customers table
            try {
                stmt.executeUpdate("""
                    CREATE TABLE Customers (
                        id INT PRIMARY KEY,
                        username VARCHAR(100),
                        password VARCHAR(100)
                    )
                """);
                System.out.println("Customers table created.");
            } catch (SQLException ex) {
                if (!"X0Y32".equals(ex.getSQLState())) {
                    System.out.println("Error creating Customers table: " + ex.getMessage());
                } else {
                    System.out.println("Customers table already exists.");
                }
            }

            //Insert hotels if table is empty
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Hotels");
            rs.next();
            int hotelCount = rs.getInt(1);

            if (hotelCount == 0) {
                stmt.executeUpdate("INSERT INTO Hotels VALUES (1, 'Azure', 'Auckland')");
                stmt.executeUpdate("INSERT INTO Hotels VALUES (2, 'SkyCity', 'Auckland')");
                stmt.executeUpdate("INSERT INTO Hotels VALUES (3, 'The Crown', 'Wellington')");
                stmt.executeUpdate("INSERT INTO Hotels VALUES (4, 'Celestial', 'Taupo')");
                
                
                System.out.println("Hotels inserted.");
            } else {
                System.out.println("Hotels already exist. Skipping insert.");
            }

            //Map hotel names to IDs
            Map<String, Integer> hotelMap = new HashMap<>();
            ResultSet rsHotels = stmt.executeQuery("SELECT id, name FROM Hotels");
            while (rsHotels.next()) {
                hotelMap.put(rsHotels.getString("name"), rsHotels.getInt("id"));
            }

            //Room data
            //an array of rooms
            int roomId = 1;
            String[][] roomData = {
                {"Azure", "Penthouse", "500", "5", "1"},
                {"Azure", "Suite", "300", "4", "3"},
                {"Azure", "Single", "150", "5", "30"},
                {"SkyCity", "Penthouse", "500", "5", "10"},
                {"SkyCity", "Suite", "300", "4", "2"},
                {"SkyCity", "Single", "150", "5", "10"},
                {"The Crown", "Penthouse", "500", "5", "4"},
                {"The Crown", "Suite", "300", "5", "3"},
                {"The Crown", "Single", "150", "5", "6"},
                {"Celestial", "Penthouse", "500", "5", "4"},
                {"Celestial", "Suite", "300", "5", "3"},
                {"Celestial", "Single", "150", "5", "7"}
            };

            for (String[] row : roomData) {
                String hotel = row[0];
                String type = row[1];
                int cost = Integer.parseInt(row[2]);
                int maxGuests = Integer.parseInt(row[3]);
                int amount = Integer.parseInt(row[4]);
                int hotelId = hotelMap.get(hotel);

                for (int i = 0; i < amount; i++) {
                    stmt.executeUpdate("INSERT INTO Rooms VALUES (" +
                            roomId++ + ", " +
                            hotelId + ", '" +
                            type + "', " +
                            cost + ", " +
                            maxGuests + ", " +
                            "false)");
                }
            }

            System.out.println("Rooms data inserted.");

        } catch (SQLException e) {
            System.out.println("Error in insertTables: " + e.getMessage());
        }
    }

    //CHATGPT DEBUGING - to view hotel table contents for testing
    public void printHotels() {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Hotels");
            System.out.println("Hotels Table:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String location = rs.getString("location");
                System.out.println("ID: " + id + ", Name: " + name + ", Location: " + location);
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving hotels: " + ex.getMessage());
        }
    }
    
    //CHATGPT DEBUGING - to view room table contents for testing
    public void printRooms() {
    try (Statement stmt = conn.createStatement()) {
        ResultSet rs = stmt.executeQuery("SELECT * FROM Rooms");
        System.out.println("Rooms Table:");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") +
                ", Hotel ID: " + rs.getInt("hotelId") +
                ", Type: " + rs.getString("type") +
                ", Cost: " + rs.getInt("cost") +
                ", Max Guests: " + rs.getInt("maxGuests") +
                ", Booked: " + rs.getBoolean("isBooked"));
        }
    } catch (SQLException ex) {
        System.out.println("Error retrieving rooms: " + ex.getMessage());
    }
}
}

