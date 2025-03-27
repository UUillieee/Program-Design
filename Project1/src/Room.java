/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gcoll
 */
public class Room {

    private int roomNumber;
    private String type;
    private double price;
    private boolean available;
    private int maxGuests;
    //Constructor
    public Room(int roomNumber,String type, double price, boolean available, int maxGuests) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.available = available;
        this.maxGuests = maxGuests;
    }
    
    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }
    
    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }
    public String toString(){
        return "Room: "+roomNumber + ": "+type + ", Price: $"+price
                + ", Available: "+available
                + ", Max Guests: "+maxGuests;
    }
}
