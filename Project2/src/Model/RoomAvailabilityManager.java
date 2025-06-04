package Model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import Model.Room;
import Model.Booking;
import java.util.Map;

//Manages room availability updates across hotels
/* 
 * 
 * @author: gcoll
 */

public class RoomAvailabilityManager {
    //Updates the availability status of a room across hotels
    public static void updateRoomAvailability(Map<String, Hotel> hotels, int roomNumber, boolean availability) {
        //iterate through all hotels
        for (Hotel hotel : hotels.values()) {
            //find the room with roomNumber
            Room room = hotel.getRoom(roomNumber);
            if (room != null) {
                //if found, update availability status
                room.setAvailable(availability);
                //exit loop after updating
                break; 
            }
        }
    }
    
    //updates room availability based on bookings read from a file
    public static void updateRoomAvailabilityFromBookings(Map<String, Hotel> hotels, String bookingsFilePath) {
        //BookingManager instance using the bookings file path
        BookingManager bookingManager = new BookingManager(bookingsFilePath);
        //load bookings from file and associate them with hotels
        var bookings = bookingManager.loadBookings(hotels);
        //mark the corresponding room as unavailable
        for (Booking booking : bookings.values()) {
            int bookedRoomNumber = booking.getRoomNumber();
            for (Hotel hotel : hotels.values()) {
                Room room = hotel.getRoom(bookedRoomNumber);
                if (room != null) {
                    //mark it unavailable
                    room.setAvailable(false); 
                }
            }
        }
    }
}
