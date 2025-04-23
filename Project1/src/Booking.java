/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gcoll
 */
public class Booking {
    private final int time;
    private final int day;
    private final int month;
    private final int endDay;
    private final int endMonth;
    private int roomNumber = 0;
    private int guests;
    
   
    
    public Booking(int time, int day, int month, int endDay, int endMonth,int roomNumber,int guests) {
        this.time = time;
        this.day = day;
        this.month = month;
        this.endDay = endDay;
        this.endMonth = endMonth;
        this.roomNumber = roomNumber;
        this.guests = guests;
    }
    
    public int getRoomNumber() {
        return roomNumber;
    }

    @Override
    public String toString() {
        return "Check-in Time: " + time + ":00, Start Date: " + day + "/" + month +
                ", End Date: " + endDay + "/" + endMonth + "/" + "Guests: " + guests;
    }

    //puts details in file
    public String toFileString() {
        return time + "," + day + "," + month + "," + endDay + "," + endMonth + "," + roomNumber + "," + guests;
    }
}
