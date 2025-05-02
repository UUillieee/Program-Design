/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gcoll
 * @author William Bindon
 */
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BookingManager {

    private final String filePath;

    public BookingManager(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, Booking> loadBookings(Map<String, Hotel> hotels) { // Added hotels so can update Room to not available 
        Map<String, Booking> bookings = new HashMap<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return bookings;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String customerName = parts[0]; // save customer name in variable
                    String[] data = parts[1].split(",");
                    if (data.length == 8) {
                        Booking booking = new Booking(
                                Integer.parseInt(data[0]), Integer.parseInt(data[1]),
                                Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                                Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                                Integer.parseInt(data[6]),Double.parseDouble(data[7])
                        );

                        bookings.put(customerName, booking); // create booking seperately
                        RoomAvailabilityManager.updateRoomAvailability(hotels,booking.getRoomNumber(),false); // In List of rooms in the hotels map, mark room as taken ( false)
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return bookings;
    }

    //Save singular booking
    public void saveBooking(String name, Booking booking) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(name + ": " + booking.toFileString());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    
    // Update / Remove Booking function
    public void removeBooking(String name,int roomNumber, Map<String, Hotel> hotels){
        Map<String, Booking> newBookings = loadBookings(hotels); // Load ist of bookings
        
        RoomAvailabilityManager.updateRoomAvailability(hotels,roomNumber,true); // update room to available 
       
        newBookings.remove(name);   //Remove booking
    
        saveAllBookings(newBookings);     //Save full list of Bookings 
    }
    
    // Save all of the bookings
    public void saveAllBookings(Map<String, Booking> currentBookings){
         try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Booking> entry : currentBookings.entrySet()) {
                writer.println(entry.getKey() + ": " + entry.getValue().toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }
}
