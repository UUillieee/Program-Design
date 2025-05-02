
import java.util.HashMap;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * @author gcoll
 * @author William Bindon
 */

//Hotel display class, only display the hotels, not the rooms
public class HotelDisplay {
    public void displayHotels(HashMap<String, Hotel> hotels) {
        //if no hotels left
        if (hotels == null || hotels.isEmpty()) {
            System.out.println("No hotels available.");
            return;
        }
        //iterate through the availible hotels
        for (Hotel hotel : hotels.values()) {
            System.out.println("Hotel: " + hotel.getName() + " | Location: " + hotel.getLocation());
        }
    }
}

