/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Handle booking interactions with the user
 * Manages creating, updating, and canceling bookings with BookingManager and DateService
 * 
 * @author gcoll
 */
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class BookingController {
    //Handles saving/loading bookings
    private final BookingManager bookingManager; 
    //Collects and validates booking date details
    private final DateService dateService; 
    //Stores current bookings keyed by customer name
    private final Map<String, Booking> bookings; 
    //Stores hotel data
    private final Map<String, Hotel> hotels;     

    //Constructor that initializes booking controller with required services and loads existing bookings
    public BookingController(BookingManager bookingManager, DateService dateService, Map<String, Hotel> hotels) {
        this.bookingManager = bookingManager;
        this.dateService = dateService;
        this.bookings = bookingManager.loadBookings(hotels); //load existing bookings and update hotel rooms
        this.hotels = hotels;
    }

    //main interaction loop for handling bookings
    public void run() {
        Scanner scanner = new Scanner(System.in);
        //ask user for name
        System.out.println("\nEnter your name:");
        String name = scanner.nextLine().trim();
        //If the user already has a booking
        if (bookings.containsKey(name)) {
            Booking booking = bookings.get(name);
            System.out.println("Welcome back, " + name + "! Your booking at:");
            System.out.println(booking);
            int roomNumber = booking.getRoomNumber();
            Room foundRoom = null;
            Hotel foundHotel = null;

            //search for hotel with the right number
            for (Hotel hotel : hotels.values()) {
                for (Room room : hotel.getRooms()) {
                    if (room.getRoomNumber() == roomNumber) {
                        foundRoom = room;
                        foundHotel = hotel;

                        //display hotel and room info
                        System.out.println("Room: " + foundHotel.getName() + " " 
                            + room.getType() + " (Room #" + room.getRoomNumber() + ")");
                        break;
                    }
                }
            }

            //if the room was found allow the user to cancel or update the booking
            if (foundRoom != null) {
                System.out.println("Would you like to?\n1) Cancel Booking\n2) Update Booking");
                int input = 0;

                //handle invalid input safely
                try {
                    input = scanner.nextInt();
                    scanner.nextLine(); //clear newline from buffer
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); // clear buffer
                    return;
                }

                //process user choice
                switch (input) {
                    case 1:
                        //remove the booking and update hotel room availability
                        bookings.remove(name);
                        bookingManager.removeBooking(name, booking.getRoomNumber(), hotels);
                        System.out.println("Booking Cancelled.");
                        break;

                    case 2:
                        //collect new booking details and save
                        Booking updatedBooking = dateService.collectBookingDetails(scanner);
                        bookings.put(name, updatedBooking);
                        bookingManager.saveBooking(name, updatedBooking); // overwrite old booking
                        System.out.println("Booking Updated: " + updatedBooking);
                        break;

                    default:
                        System.out.println("Invalid input. Returning to main menu.");
                }
            }

            //if the room could not be found in the hotel data
            if (foundRoom == null) {
                System.out.println("Room details not found.");
            }
        } 
        //if the user does not have a previous booking, collect and save a new booking
        else {
            Booking booking = dateService.collectBookingDetails(scanner);
            bookings.put(name, booking);
            bookingManager.saveBooking(name, booking);
            System.out.println("Booking saved: " + booking);
        }
    }
}
