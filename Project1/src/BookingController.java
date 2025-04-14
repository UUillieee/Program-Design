/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gcoll
 */
import java.util.Map;
import java.util.Scanner;

public class BookingController {
    private final BookingManager bookingManager;
    private final DateService dateService;
    private final Map<String, Booking> bookings;
    private final Map<String, Hotel> hotels;

    public BookingController(BookingManager bookingManager, DateService dateService, Map<String, Hotel> hotels) {
        this.bookingManager = bookingManager;
        this.dateService = dateService;
        this.bookings = bookingManager.loadBookings();
        this.hotels = hotels;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter your name:");
        String name = scanner.nextLine().trim();

        if (bookings.containsKey(name)) {
            Booking booking = bookings.get(name);
            System.out.println("Welcome back, " + name + "! Your booking at:");
            System.out.println(booking);
            
            
            int roomNumber = booking.getRoomNumber();
            Room foundRoom = null;

            // Search through hotels and rooms to find the matching room
            for (Hotel hotel : hotels.values()) {
                for (Room room : hotel.getRooms()) {
                    if (room.getRoomNumber() == roomNumber) {
                        foundRoom = room;
                        System.out.println("Room: " + room.getType() + " (Room #" + room.getRoomNumber() + ")");
                        break;
                    }
                }
            if (foundRoom != null) break;
            }

            if (foundRoom == null) {
                System.out.println("Room details not found.");
            }
        }
        
        else {
            Booking booking = dateService.collectBookingDetails(scanner);
            bookings.put(name, booking);
            bookingManager.saveBooking(name, booking);
            System.out.println("Booking saved: " + booking);
        }
    }
}
