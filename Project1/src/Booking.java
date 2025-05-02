/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Represents a hotel room booking
 * Stores details such as time, start and end dates, room number, guest count, and total price
 * 
 * @author gcoll
 */
public class Booking {
    private final int time; //time of check-in (24hr time)
    private final int day;//start day of booking
    private final int month;//Start month of booking
    private final int endDay;//End day of booking
    private final int endMonth;//End month of booking
    private int roomNumber = 0;//Room number assigned to the booking
    private int guests;//Number of guests for the booking
    private int totalPrice;//Total price for the booking

    //constructor to initialize all details
    public Booking(int time, int day, int month, int endDay, int endMonth, int roomNumber, int guests) {
        this.time = time;
        this.day = day;
        this.month = month;
        this.endDay = endDay;
        this.endMonth = endMonth;
        this.roomNumber = roomNumber;
        this.guests = guests;
    }

    //Returns the room number
    public int getRoomNumber() {
        return roomNumber;
    }

    //Returns a string of the booking details 
    @Override
    public String toString() {
        return "Check-in Time: " + time + ":00, Start Date: " + day + "/" + month
                + ", End Date: " + endDay + "/" + endMonth + ", Guests: " + guests;
    }

    //Returns total price of the booking
    public int getTotalPrice() {
        return totalPrice;
    }

    //Sets total price of the booking
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    //Returns a string suitable for saving to a file
    public String toFileString() {
        return time + "," + day + "," + month + "," + endDay + "," + endMonth + "," + roomNumber + "," + guests;
    }
}
