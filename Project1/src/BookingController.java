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
        String name = scanner.nextLine().trim().toLowerCase();
        //Exit program
        if (name.equalsIgnoreCase("q")) {
            throw new BookingCancelledException("Booking cancelled by user.");
        }

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
                System.out.println("Would you like to?\n1) Cancel Booking\n2) Update Booking\n3)Exit");
                String input = "";
                do {
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("q")) {
                        throw new BookingCancelledException("'q' entered.");
                    }
                } while (!input.equals("1") || !input.equals("2") || !input.equals("3"));

                switch (input) {
                    case "1":
                        // Add actual cancellation logic
                        bookings.remove(name);
                        bookingManager.removeBooking(name, booking.getRoomNumber(), hotels); // remove booking , with hotels to make room available again
                        System.out.println("Booking Cancelled.");
                        break;
                    case "2":
                        try {
                            //Update Booking , Collect Details Again
                            Booking updatedBooking = dateService.collectBookingDetails(scanner);
                            int duration = updatedBooking.getEndDay() - updatedBooking.getDay(); // Calculate length of stay
                            double totalPrice = RoomManager.calculateTotalPrice(updatedBooking.getRoomNumber(), duration); // Calculate total price of stay
                            updatedBooking.setTotalPrice(totalPrice);
                            bookings.put(name, updatedBooking);
                            bookingManager.saveBooking(name, updatedBooking); // overwrite old booking
                            System.out.println("Booking Updated: " + updatedBooking);
                        } catch (BookingCancelledException e) {
                            System.out.println(e.getMessage());
                            return; // Return to Menu class gracefully
                        }
                        break;
                    case "3":
                        System.out.println("Exiting, Thanks!");
                        break;
                    default:
                        System.out.println("Invalid input. Returning to main menu.");
                }
            }

            if (foundRoom == null) {
                System.out.println("Room details not found.");
            }
        } //if name found then prompt other options for booking
        //if name not found then save new booking details to the txt file
        else {

            try {
                Booking booking = dateService.collectBookingDetails(scanner);
                //Calc total price
                int duration = booking.getEndDay() - booking.getDay(); // Duration of stay
                double totalPrice = RoomManager.calculateTotalPrice(booking.getRoomNumber(), duration); // Calculate total price
                booking.setTotalPrice(totalPrice);  // Set the total price for the booking
                bookings.put(name, booking); // Save booking in bookings hashmap
                bookingManager.saveBooking(name, booking);
                System.out.println("Booking saved: " + booking);
            } catch (BookingCancelledException e) { // if booking is cancelled anywhere in the collect booking class.
                System.out.println(e.getMessage());
                return; // return to menu class gracefully
            }

        }
    }
}
