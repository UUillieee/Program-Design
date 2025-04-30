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

    public static HashMap<String, Hotel> readHotels() {
        HashMap<String, Hotel> hotels = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("./resources/Hotels.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("-");
                if (data.length == 2) {
                    Hotel hotel = new Hotel(data[0], data[1]);
                    hotels.put(data[0], hotel);
                }
            }
        } catch (IOException e) {
            Logger.getLogger(HotelFileReader.class.getName()).log(Level.SEVERE, "Error reading hotel data", e);
        }
        return hotels;
    }
}

