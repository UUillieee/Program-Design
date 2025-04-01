
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
 */
public class HotelManager {

    /*
    Add Methods in here for working with hotels
    e.g display info, so you dont have to make the main method complex
     */
    public static void main(String[] args) {

        //Read hotels and store into HashMap
        System.out.println("\nTesting Hotel Locations:\n");
        HashMap<String, Hotel> hotels = readHotels();
        //Test accessing information from hotel objects stored in the hashmap
        // System.out.println(hotels.get("SkyCity").getLocation());
        HotelManager.displayInfo(hotels);

    }

    //Display info about all available hotels
    public static void displayInfo(HashMap<String, Hotel> hotels) {
        for (String hotelName : hotels.keySet()) {
            System.out.print("Name: " + hotels.get(hotelName).getName());
            System.out.print("    Location: " + hotels.get(hotelName).getLocation());
            
        }

    }

    public static HashMap readHotels() {
        // Read File Hotels.txt stored in the resources folder
        // Splits data by each line and splits line by character "-"
        // Prints data for each hotel from file, currently storing the data in a hashMap
        HashMap<String, Hotel> hotels = new HashMap<>();
        try {
            FileReader fr = new FileReader("./resources/Hotels.txt");
            BufferedReader inStream = new BufferedReader(fr);
            String line = null;
            while ((line = inStream.readLine()) != null) {
                String str[] = line.split("-");
                //Get variables from string array if str split properly
                if (str.length == 2) {
                    String name = str[0];
                    String location = str[1];
                    //Convert string to integer for rooms availabe
                    //Create new hotel object with read data to store a list
                    Hotel h = new Hotel(name, location);
                    //Add hotel object to hashmap where the key is the name, and the value is the hotel object
                    hotels.put(name, h);
                }
                /* test string splitting
                System.out.print("Hotel: " + str[0]);
                System.out.print(" Location: " + str[1]);
                System.out.print(" Rooms Available: " + str[2] + "\n");
                 */

            }
            inStream.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(HotelManager.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        return hotels;
    }

}
