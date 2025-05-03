
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
 * @author gcoll
 * @author William Bindon
 */


//This class manages a collection of hotels
//it loads hotel data using the HotelileReader class
public class HotelManager {

    private HashMap<String, Hotel> hotels;

    // Constructor: Initialize the hotels collection by reading from the file
    public HotelManager() {
        this.hotels = HotelFileReader.readHotels(); // Read hotels 
    }

    // Method to get all hotels
    public HashMap<String, Hotel> getHotels() {
        return hotels;
    }

    // Method to add a hotel (if needed in future)
    public void addHotel(Hotel hotel) {
        hotels.put(hotel.getName(), hotel);
    }

}


