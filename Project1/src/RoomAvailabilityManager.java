
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gcoll
 */
public class RoomAvailabilityManager {
    //In Seperate Class to adhere to SOLID principles
    
            // Function to mark availability of a room
    public static void updateRoomAvailability(Map<String, Hotel> hotels, int roomNumber,boolean availability) {
        for (Hotel hotel : hotels.values()) {
            Room room = hotel.getRoom(roomNumber);
            if (room != null) {
                room.setAvailable(availability);
                break;
            }
        }
    }
}
