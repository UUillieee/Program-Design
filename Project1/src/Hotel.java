

import java.util.ArrayList;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayList; // Import the ArrayList class for storing a list of rooms

/**
 * represents a hotel with a name, location, and a collection of rooms
 * @author George
 */
public class Hotel {
    //name of the hotel
    private String name; 
    //location of the hotel
    private String location; 
    //list to store Room objects
    private ArrayList<Room> rooms;
    //number of available rooms
    private int roomsAvail; 

    
    //constructor, initialize a hotel with a name and location.
    public Hotel(String n, String l) {
        this.name = n;
        this.location = l;
        this.roomsAvail = 0;
        this.rooms = new ArrayList<>();
    }

    
    //adds a room to the hotel and increments the available room count.
    public void addRoom(Room room) {
        this.rooms.add(room);
        //increase available room
        this.roomsAvail++; 
    }
    //returns the list of all rooms in the hotel.
    public ArrayList<Room> getRooms() {
        return this.rooms;
    }
    //getter and setter for hotel name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    //getter and setter for hotel location
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    //getter and setter for number of available rooms
    public int getRoomsAvail() {
        return roomsAvail;
    }
    public void setRoomsAvail(int roomsAvail) {
        this.roomsAvail = roomsAvail;
    }
    
    //displays the list of rooms in the hotel with their details.
    //only availible rooms will be shown
    public void displayRooms() {
        System.out.println("Rooms in Hotel: " + this.name);
        System.out.println("Total Available: " + this.getRoomsAvail());
        int availableCount = 0;
        //tterate through the room list and display only available rooms
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println("Room #" + room.getRoomNumber() + ": " 
                    + room.getType() + " - Price: $" + room.getPrice() 
                    + " - Max Guests: " + room.getMaxGuests());
                availableCount++;
            }
        }
    }
    //retrieves a room object by its number.
    Room getRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null; //room not found
    }
}
