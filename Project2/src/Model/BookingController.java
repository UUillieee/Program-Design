package Model;

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
import Model.Room;
import Model.Booking;
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
        System.out.println("\nEnter your name: (Case insensitive)");
      
        String name = scanner.nextLine().trim().toLowerCase();
        //Exit program
        if (name.equalsIgnoreCase("q")) {
            throw new BookingCancelledException("Booking cancelled by user.");
        }
 
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
             
//            //if the room was found allow the user to cancel or update the booking
//            // Update or cancel existing booking
//            if (foundRoom != null) {
//                System.out.println("Would you like to?\n1) Cancel Booking\n2) Update Booking\n3)Exit");
//                String input = "";
//                //handle invalid input safely
//                do {
//                    input = scanner.nextLine().trim();
//                    if (input.equalsIgnoreCase("q")) {
//                        throw new BookingCancelledException("'q' entered."); // Exit condition to leave back to main method
//                    }
//                } while (!input.equals("1") && !input.equals("2") && !input.equals("3")); // Keeps asking while input not valid.
//                  //process user choice
//                switch (input) {
//                    case "1":
//                        //remove the booking and update hotel room availability
//                        bookings.remove(name); // remove from local copy
//                        bookingManager.removeBooking(name, booking.getRoomNumber(), hotels); // Remove file copy, to make the room available again.
//                        System.out.println("Booking Cancelled.");
//                        break;
//                    //collect new booking details and save
//                    case "2":
//                        try {
//                            //Update Booking , Collect Details Again
//                            Booking updatedBooking = dateService.collectBookingDetails(scanner,hotels); // pass in final copy of hotels
//                            int duration = updatedBooking.getEndDay() - updatedBooking.getDay(); // Calculate length of stay
//                            double totalPrice = RoomManager.calculateTotalPrice(updatedBooking.getRoomNumber(), duration); // Calculate total price of stay
//                            updatedBooking.setTotalPrice(totalPrice); // Set total price 
//                            bookings.put(name, updatedBooking); // Add updated booking to local copy of bookings
//                            bookingManager.saveBooking(name, updatedBooking); // Save local copy to file copy of bookings
//                            System.out.println("Booking Updated: " + updatedBooking); // Update message
//                        } catch (BookingCancelledException e) { 
//                            System.out.println(e.getMessage());
//                            return; // Return to Menu class gracefully
//                        }
//                        break;
//                    case "3":
//                        System.out.println("Exiting, Thanks!");
//                        break;
//
//                    default:
//                        System.out.println("Invalid input. Returning to main menu.");
//                }
//            }
//
//            //if the room could not be found in the hotel data
//            if (foundRoom == null) {
//                System.out.println("Room details not found.");
//            }
//        } 
//        //if the user does not have a previous booking, collect and save a new booking
//
//        else {
//
//            try {
//                Booking booking = dateService.collectBookingDetails(scanner,hotels); // pass in final copy of hotels
//                //Calc total price
//                int duration = booking.getEndDay() - booking.getDay(); // Duration of stay
//                double totalPrice = RoomManager.calculateTotalPrice(booking.getRoomNumber(), duration); // Calculate total price
//                booking.setTotalPrice(totalPrice);  // Set the total price for the booking
//                bookings.put(name, booking); // Save booking in bookings hashmap
//                bookingManager.saveBooking(name, booking);
//                System.out.println("Booking saved: " + booking);
//            } catch (BookingCancelledException e) { // if booking is cancelled anywhere in the collect booking class.
//                System.out.println(e.getMessage());
//                return; // return to menu class gracefully
//            }
//
//        }
    }
}
}
