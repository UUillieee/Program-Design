package Model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Handles loading, saving, updating, and removing bookings from the CustomerInfo txt file text file
 * updates room availability in the hotel data based on booking changes
 *
 * @author gcoll
 * @author William Bindon
 */
import Model.Booking;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BookingManager {

    private final String filePath; //Path to the bookings data file

    //Constructor accepts a path to the file where bookings are stored
    public BookingManager(String filePath) {
        this.filePath = filePath;
    }

    //Loads all bookings from the file and updates hotel room availability.
    public Map<String, Booking> loadBookings(Map<String, Hotel> hotels) {
        Map<String, Booking> bookings = new HashMap<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return bookings; //Return empty map if file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String customerName = parts[0]; //Extract customer name
                    String[] data = parts[1].split(",");

                    if (data.length == 8) {
                        //Parse booking details and create Booking object
                        Booking booking = new Booking(
                                Integer.parseInt(data[0]), Integer.parseInt(data[1]),
                                Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                                Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                                Integer.parseInt(data[6]), Double.parseDouble(data[7])
                        );
                        //Add booking to map
                        bookings.put(customerName, booking);

                        //update room availability in hotel data
                        RoomAvailabilityManager.updateRoomAvailability(
                                hotels, booking.getRoomNumber(), false
                        );
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return bookings;
    }

    //Saves the booking by appending it to the file.
    public void saveBooking(String name, Booking booking) {
        Map<String, Booking> allBookings = loadBookings(new HashMap<>()); // load without needing hotel room updates
        allBookings.put(name, booking); // replace or add booking
        saveAllBookings(allBookings);  // overwrite entire file
    }

    public void removeBooking(String name, int roomNumber, Map<String, Hotel> hotels) {
        //Reload all bookings into memory
        Map<String, Booking> newBookings = loadBookings(hotels);
        //Mark the room as available again
        RoomAvailabilityManager.updateRoomAvailability(hotels, roomNumber, true);
        //Remove the specified booking
        newBookings.remove(name);
        //Save the updated list of bookings
        saveAllBookings(newBookings);
    }

    //Saves a full list of bookings to the file (overwrites existing file).
    public void saveAllBookings(Map<String, Booking> currentBookings) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Booking> entry : currentBookings.entrySet()) {
                writer.println(entry.getKey() + ": " + entry.getValue().toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }

}
