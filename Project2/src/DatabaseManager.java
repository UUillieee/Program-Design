
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author gcoll
 */
//Model layer
public class DatabaseManager {

    private static final String USER_NAME = "gc1"; //your DB username
    private static final String PASSWORD = "gc1"; //your DB password
    private static final String dbURL = "jdbc:derby:myDB;create=true";  //url of the DB host

    public static void main(String[] args) {

        DatabaseManager dbManager = new DatabaseManager();
        System.out.println(dbManager.getConnection());
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
    }

    Connection conn;

    public DatabaseManager() {
        establishConnection();
    }

    public Connection getConnection() {
        return this.conn;
    }

    //Establish connection
    public void establishConnection() {
        if (this.conn == null) {
            try {
                conn = DriverManager.getConnection(dbURL, USER_NAME, PASSWORD);
                System.out.println(dbURL + " Get Connected Successfully ....");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void insertTables() {
        try (Statement stmt = conn.createStatement()) {
            // 1. Create Hotels table
            try {
                stmt.executeUpdate("""
                CREATE TABLE Hotels (
                    id INT PRIMARY KEY,
                    name VARCHAR(100)
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

            // 2. Create Rooms table
            try {
                stmt.executeUpdate("""
                CREATE TABLE Rooms (
                    id INT PRIMARY KEY,
                    hotelId INT,
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

            // 3. Check if Hotels table has data
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Hotels");
            rs.next();
            int hotelCount = rs.getInt(1);

            if (hotelCount == 0) {
                // Insert sample hotel
                stmt.executeUpdate("INSERT INTO Hotels VALUES (1, 'Grand Plaza')");

                // Insert sample rooms
                for (int i = 101; i <= 105; i++) {
                    stmt.executeUpdate("INSERT INTO Rooms VALUES (" + i + ", 1, false)");
                }

                System.out.println("Initial data inserted.");
            } else {
                System.out.println("Initial data already exists. Skipping insert.");
            }

        } catch (SQLException e) {
            System.out.println("Error in insertTables: " + e.getMessage());
        }
    }

}
