
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
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
public class RoomManager {

    /*
    Room.txt Format
    RoomType-Price-
     */
    public static void main(String[] args) {
        displayHotelRooms();
    }

    public static void displayHotelRooms() {
        HashMap<String, Hotel> hotels = HotelManager.readHotels();

        HashMap<String, Room> rooms = readRooms(hotels);
        //Test accessing information from hotel objects stored in the hashmap
        //System.out.println(rooms.get("Penthouse").getType());
        System.out.println("Displaying all available rooms.\n");
        //Display all hotels and their rooms
        for (Hotel hotel : hotels.values()) {
            hotel.displayRooms();
            System.out.println("");
        }
    }

    public static boolean isRoomAvailable(int roomNumber) {
        HashMap<String, Hotel> hotels = HotelManager.readHotels();
        for (Hotel hotel : hotels.values()) {
            Room room = hotel.getRoom(roomNumber);
            if (room != null && room.isAvailable()) {
                return true;  // Room is available
            }
        }
        return false;  // Room is not available
    }

    public static HashMap readRooms(HashMap<String, Hotel> hotels) {
        //Adapt the readHotels function to read the different room types

        HashMap<String, Room> rooms = new HashMap<>();
        try {
            FileReader fr = new FileReader("./resources/Rooms.txt");
            BufferedReader inStream = new BufferedReader(fr);
            String line = null;
            int idNumber = 1;
            while ((line = inStream.readLine()) != null) {
                String str[] = line.split("-");
                //Get variables from string array if str split properly
                if (str.length == 5) {
                    String hotelName = str[0];
                    String type = str[1];
                    int price = Integer.parseInt(str[2]);
                    int maxGuests = Integer.parseInt(str[3]);
                    int availableRooms = Integer.parseInt(str[4]);

                    // Create the Room objects with the amount of available rooms
                    for (int i = 0; i < availableRooms; i++) {

                        //Room Number- Type- price - available? - maxGuests
                        Room room = new Room(idNumber, type, price, true, maxGuests);
                        //Add room to rooms hashmap
                        rooms.put(type + "-" + idNumber, room);
                        // Add to the corresponding hotel's ArrayList of rooms

                        if (hotels.containsKey(hotelName)) {
                            hotels.get(hotelName).addRoom(room);
                        } else {
                            System.out.println("Warning: Hotel " + hotelName + " not found for room type " + type);
                        }
                        idNumber++;
                    }

                }
            }

            inStream.close();
            // Set room availability from the list of bookings.
            RoomAvailabilityManager.updateRoomAvailabilityFromBookings(hotels, "./resources/CustomerInfo.txt");

        } catch (FileNotFoundException e) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        return rooms;
    }

    public static int getMaxRoomNumber() {
        // Load Hotels - they dont have rooms in them
        HashMap<String, Hotel> hotels = HotelManager.readHotels();
        //Load Rooms 
        HashMap<String, Room> rooms = readRooms(hotels);
        int max = 0;

        for (Room room : rooms.values()) {
            if (room.getRoomNumber() > max) {
                max = room.getRoomNumber();
            }
        }

        return max;
    }

    public static int getMaxGuestsForRoom(int roomNumber) { // get maximum number of guests a specific room if you only have the roomNumber.
        HashMap<String, Hotel> hotels = HotelManager.readHotels();
        for (Hotel hotel : hotels.values()) {
            Room room = hotel.getRoom(roomNumber);
            if (room != null) {
                return room.getMaxGuests();
            }
        }
        return -1; // if room not found return -1
    }

}
