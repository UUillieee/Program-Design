package Model;

import java.io.BufferedReader;
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
  * @author gcoll
 * @author William Bindon
 */
public class HotelFileReader {

    //reads hotel data from hotel file and returns a hashmap of hotel objects.
    //the hotel name is used as the key and the hotel object as the value.
    public static HashMap<String, Hotel> readHotels() {
        HashMap<String, Hotel> hotels = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("./resources/Hotels.txt"))) {
            String line;
            //read file line by line
            while ((line = reader.readLine()) != null) {
                //split part of line with "-"
                String[] data = line.split("-");
                //line has exactly two parts: name and location
                if (data.length == 2) {
                    //create hotel object with name and location
                    Hotel hotel = new Hotel(1, data[0], data[1]);
                    //put in hashmap
                    hotels.put(data[0], hotel);
                }
            }
        } catch (IOException e) {
            Logger.getLogger(HotelFileReader.class.getName()).log(Level.SEVERE, "Error reading hotel data", e);
        }
        //return hotels
        return hotels;
    }
}

