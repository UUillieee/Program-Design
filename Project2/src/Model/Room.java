package Model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Represents a hotel room with properties such as room number, type, price,
 * availability status, and maximum number of guests
 * Provides getter and setter methods for each property
 * 
 * @author gcoll
 */
public class Room {

    // room number integer
    private int roomNumber; 
    //type of room (e.g., Single, Double, Suite)    
    private String type;
    //price per night for the room
    private double price;    
    //availability status of the room
    private boolean available;
    //maximum number of guests the room
    private int maxGuests;      
    
    //constructor
    public Room(int roomNumber, String type, double price, boolean available, int maxGuests) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.available = available;
        this.maxGuests = maxGuests;
    }

    //Getter, room number
    public int getRoomNumber() {
        return roomNumber;
    }
    //Setter, room number
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    //Getter, room type
    public String getType() {
        return type;
    }
    //Setter, room type
    public void setType(String type) {
        this.type = type;
    }

    //Getter for price
    public double getPrice() {
        return price;
    }

    //Setter for price
    public void setPrice(double price) {
        this.price = price;
    }

    //Setter for availability
    public void setAvailable(boolean available) {
        this.available = available;
    }

    //Getter to check room availability
    public boolean isAvailable() {
        return available;
    }
    
    //Getter for maximum number of guests
    public int getMaxGuests() {
        return maxGuests;
    }

    //Setter for maximum number of guests
    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }
    
    
    //Returns a formatted string with details of the room
    public String toString() {
        return "Room: " + roomNumber + ": " + type 
             + ", Price: $" + price 
             + ", Available: " + available 
             + ", Max Guests: " + maxGuests;
    }
}
