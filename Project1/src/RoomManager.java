
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
                /* test string splitting
                System.out.print("Hotel: " + str[0]);
                System.out.print(" Location: " + str[1]);
                System.out.print(" Rooms Available: " + str[2] + "\n");
                 */

            }
            inStream.close();

            BookingManager bookingManager = new BookingManager("./resources/CustomerInfo.txt");
            var bookings = bookingManager.loadBookings(hotels); 

            for (Booking booking : bookings.values()) {
                int bookedRoomNumber = booking.getRoomNumber();

                for (Hotel hotel : hotels.values()) {
                    Room r = hotel.getRoom(bookedRoomNumber);
                    if (r != null) {
                        r.setAvailable(false);
                    }
                }
            }
            }catch (FileNotFoundException e) {
            Logger.getLogger(RoomManager.class.getName()).log(Level.SEVERE, null, e);
        }catch (IOException e) {
            System.out.println("IO Exception");
        }
            return rooms;
        }

    }
