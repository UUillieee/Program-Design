/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gcoll
 */
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BookingManager {

    private final String filePath;

    public BookingManager(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, Booking> loadBookings() {
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
                    String[] data = parts[1].split(",");
                    if (data.length == 5) {
                        bookings.put(parts[0], new Booking(
                                Integer.parseInt(data[0]), Integer.parseInt(data[1]),
                                Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                                Integer.parseInt(data[4])
                        ));
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
}
