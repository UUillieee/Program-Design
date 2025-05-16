package dbpackage;

import dbpackage.HotelDataInserter;
import dbpackage.CreateTables;
import java.sql.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author billw
 */

//manager for all of the data bases
public class DatabaseManager {
    public static void main(String[] args) {
        new CreateTables().insertTables();
        new HotelDataInserter().insert();
        new HotelCollect().printAllHotels();
        new RoomCollect().printAllRooms();
    } 
}


