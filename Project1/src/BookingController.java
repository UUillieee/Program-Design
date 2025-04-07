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

    public BookingController(BookingManager bookingManager, DateService dateService) {
        this.bookingManager = bookingManager;
        this.dateService = dateService;
        this.bookings = bookingManager.loadBookings();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter your name:");
        String name = scanner.nextLine().trim();

        if (bookings.containsKey(name)) {
            System.out.println("Welcome back, " + name + "! Your booking:");
            System.out.println(bookings.get(name));
        } else {
            Booking booking = dateService.collectBookingDetails(scanner);
            bookings.put(name, booking);
            bookingManager.saveBooking(name, booking);
            System.out.println("Booking saved: " + booking);
        }
    }
}
