
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private ArrayList<Room> rooms;
    private int roomsAvail;

    //Constructor
    public Hotel(String n, String l) {
        this.name = n;
        this.location = l;
        this.roomsAvail = 0; 
        this.rooms = new ArrayList<>();
    }
   public void addRoom(Room room) {
        this.rooms.add(room);
        this.roomsAvail++;  // increment when room gets added.
    }
     public ArrayList<Room> getRooms() {
        return this.rooms;
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
    
    public void displayRooms() {
        System.out.println("Rooms in Hotel: " + this.name);
        System.out.println("Total Available: "+this.getRoomsAvail());
        for (Room room : rooms) {
            System.out.println("Room "+ room.getRoomNumber()+ ") "+room.getType() + " - Price: $" 
                    +room.getPrice() + " - Max Guests: " + room.getMaxGuests() + " Available? " + room.isAvailable());
        }
    }
    

}
