package Model;


import Model.Room;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 * 
 * @author: gcoll
 * @author: William Bindon
 */

 //loading room data from a file, checking availability, displaying all rooms
public class RoomManager {
    //Expected format for each line in Rooms.txt:
    //HotelName-RoomType-Price-MaxGuests-AvailableRoomsCount

    //method to test/display hotel rooms
    public static void main(String[] args) {
        //displayHotelRooms();
        System.out.println("Total price for room 30 for 4 days = "+RoomManager.calculateTotalPrice(30, 4));
    }

    //Reads hotel and room data and displays rooms grouped by hotels
    public static void displayHotelRooms() {
        //load hotels from file
        HashMap<String, Hotel> hotels = HotelFileReader.readHotels(); 
        //load rooms and assign them to hotels
        HashMap<String, Room> rooms = readRooms(hotels);              

        System.out.println("Displaying all available rooms.\n");

        //iterate through each hotel and display its rooms
        for (Hotel hotel : hotels.values()) {
            hotel.displayRooms();
            System.out.println("");
        }
    }

    //Checks if a room with the room number is available
    public static boolean isRoomAvailable(int roomNumber,Map<String, Hotel> hotels) {
        for (Hotel hotel : hotels.values()) {
            Room room = hotel.getRoom(roomNumber);
            if (room != null && room.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    //Reads from Rooms.txt, creates Room objects, and assigns them with hotels
    public static HashMap<String, Room> readRooms(HashMap<String, Hotel> hotels) {
        HashMap<String, Room> rooms = new HashMap<>();
        try {
            FileReader fr = new FileReader("./resources/Rooms.txt");
            BufferedReader inStream = new BufferedReader(fr);
            String line;
            //room number counter
            int idNumber = 1; 
            while ((line = inStream.readLine()) != null) {
                String[] str = line.split("-");
                //expect 5 fields per line
                if (str.length == 5) {
                    String hotelName = str[0];
                    String type = str[1];
                    double price = Double.parseDouble(str[2]);
                    int maxGuests = Integer.parseInt(str[3]);
                    int availableRooms = Integer.parseInt(str[4]);
                    //Create the number of rooms and assign to hotel
                    for (int i = 0; i < availableRooms; i++) {
                        Room room = new Room(idNumber, type, price, true, maxGuests);
                        rooms.put(type + "-" + idNumber, room);
                        if (hotels.containsKey(hotelName)) {
                            //Link room to hotel
                            hotels.get(hotelName).addRoom(room); 
                        } else {
                            System.out.println("Warning: Hotel " + hotelName + " not found for room type " + type);
                        }
                        //room ID +1
                        idNumber++; 
                    }
                }
            }
            inStream.close();
            //after loading rooms, update their availability based from bookings
            RoomAvailabilityManager.updateRoomAvailabilityFromBookings(hotels, "./resources/CustomerInfo.txt");
            
        } catch (FileNotFoundException e) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        return rooms;
    }
    
    //Retrieves the highest room number currently assigned.
    public static int getMaxRoomNumber() {
        HashMap<String, Hotel> hotels = HotelFileReader.readHotels();
        HashMap<String, Room> rooms = readRooms(hotels);

        int max = 0;
        for (Room room : rooms.values()) {
            if (room.getRoomNumber() > max) {
                max = room.getRoomNumber();
            }
        }
        return max;
    }
    
    //Gets the maximum number of guests allowed for a specific room by its number.
    public static int getMaxGuestsForRoom(int roomNumber) {
        HashMap<String, Hotel> hotels = HotelFileReader.readHotels();
        HashMap<String, Room> rooms = readRooms(hotels); 
        for (Hotel hotel : hotels.values()) {
            Room room = hotel.getRoom(roomNumber);
            if (room != null) {
                return room.getMaxGuests();
            }
        }
        //room not found
        return -1; 
    }

    public static double calculateTotalPrice(int roomNumber, int duration) {
        Room room = getRoomByNumber(roomNumber);
        if (room != null) {
            return duration * room.getPrice();
        } else {
            throw new IllegalArgumentException("Room not found: " + roomNumber);
        }
    }

    public static Room getRoomByNumber(int roomNumber) {
        HashMap<String, Hotel> hotels = HotelFileReader.readHotels();
        HashMap<String, Room> rooms = RoomManager.readRooms(hotels);
        for (Hotel hotel : hotels.values()) {
            for (Room room : hotel.getRooms()) {
                if (room.getRoomNumber() == roomNumber) {
                    return room;
                }
            }
        }
        return null;
    }

}
