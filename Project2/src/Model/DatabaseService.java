package Model;

//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
///**
// *
// * @author gcoll
// */
////Class used to query and update database
//public class DatabaseService {
//
//    private final DatabaseManager dbManager;
//    private final Connection conn;
//    private Statement statement;
//
//    public DatabaseService() {
//        dbManager = new DatabaseManager();
//        conn = dbManager.getConnection();
//    }
//
//    public ResultSet queryDB(String sql) {
//
//        Connection connection = this.conn;
//        Statement statement = null;
//        ResultSet resultSet = null;
//
//        try {
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(sql);
//
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return resultSet;
//    }
//
//    public void updateDB(String sql) {
//
//        Connection connection = this.conn;
//        Statement statement = null;
//        ResultSet resultSet = null;
//
//        try {
//            statement = connection.createStatement();
//            statement.executeUpdate(sql);
//
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
//    public void insertTables(){
//        
//    }
//    public void createTables(){
//        
//    }
//}
