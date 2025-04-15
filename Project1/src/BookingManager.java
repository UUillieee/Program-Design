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
                    if (data.length == 6) {
                        Booking booking = new Booking(
                                Integer.parseInt(data[0]), Integer.parseInt(data[1]),
                                Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                                Integer.parseInt(data[4]), Integer.parseInt(data[5])
                        );

                        bookings.put(customerName, booking); // create booking seperately
                        markRoomAsUnavailable(hotels,booking); // In List of rooms in the hotels map, mark room as taken
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return bookings;
    }

    public void saveBooking(String name, Booking booking) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(name + ": " + booking.toFileString());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private void markRoomAsUnavailable(Map<String, Hotel> hotels, Booking booking) {
        int roomNumber = booking.getRoomNumber();
        for (Hotel hotel : hotels.values()) {
            Room room = hotel.getRoom(roomNumber);
            if (room != null) {
                room.setAvailable(false);
                break;
            }
        }
    }
}
