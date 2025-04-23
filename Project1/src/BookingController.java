/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gcoll
 */
import java.util.InputMismatchException;
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
        this.bookings = bookingManager.loadBookings(hotels); // pass hotels to booking manager to set rooms to booked
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
            Hotel foundHotel = null;

            // Search through hotels and rooms to find the matching room - and print out booking information
            for (Hotel hotel : hotels.values()) {
                for (Room room : hotel.getRooms()) {
                    if (room.getRoomNumber() == roomNumber) {
                        foundRoom = room;
                        foundHotel = hotel;
                        System.out.println("Room: " + foundHotel.getName() + " " + room.getType() + " (Room #" + room.getRoomNumber() + ")");
                        break;
                    }
                }
            }
            
            // Update or cancel existing booking
            if (foundRoom != null) {
                System.out.println("Would you like to?\n1) Cancel Booking\n2) Update Booking");
                int input = 0;
                
                try {
                    input = scanner.nextInt();
                    scanner.nextLine(); // clear newline
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); // clear scanner buffer
                    return;
                }
                switch (input) {
                    case 1:
                        // Add actual cancellation logic
                        bookings.remove(name);
                        bookingManager.removeBooking(name,booking.getRoomNumber(),hotels); // remove booking , with hotels to make room available again
                        System.out.println("Booking Cancelled.");
                        break;
                    case 2:
                        Booking updatedBooking = dateService.collectBookingDetails(scanner);
                        bookings.put(name, updatedBooking);
                        bookingManager.saveBooking(name, updatedBooking); // overwrite old booking
                        System.out.println("Booking Updated: " + updatedBooking);
                        break;
                    default:
                        System.out.println("Invalid input. Returning to main menu.");
                }
            }
            
            if (foundRoom == null) {
                System.out.println("Room details not found.");
            }
        } //if name not found then save new booking details to the txt file
        else {
            Booking booking = dateService.collectBookingDetails(scanner);
            bookings.put(name, booking);
            bookingManager.saveBooking(name, booking);
            System.out.println("Booking saved: " + booking);
        }
    }
}
