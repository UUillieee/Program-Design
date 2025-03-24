
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author George
 */
public class Hotel {
    //Needs to be able to store rooms somehow // 

    private String name;
    private String location;
    private int roomsAvail;

    //Constructor
    public Hotel(String n, String l, int rooms) {
        this.name = n;
        this.location = l;
        this.roomsAvail = rooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRoomsAvail() {
        return roomsAvail;
    }

    public void setRoomsAvail(int roomsAvail) {
        this.roomsAvail = roomsAvail;
    }

}
