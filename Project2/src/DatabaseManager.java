
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

    }
}
