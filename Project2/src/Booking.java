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
    private double totalPrice = 0;//Total price for the booking

    //constructor to initialize all details
    public Booking(int time, int day, int month, int endDay, int endMonth, int roomNumber, int guests,double totalPrice) {

        this.time = time;
        this.day = day;
        this.month = month;
        this.endDay = endDay;
        this.endMonth = endMonth;
        this.roomNumber = roomNumber;
        this.guests = guests;
        this.totalPrice = totalPrice;
    }

    //Returns the room number
    public int getRoomNumber() {
        return roomNumber;
    }

    //Returns a string of the booking details 
    @Override
    public String toString() {
        return "Check-in Time: " + time + ":00, Start Date: " + getDay() + "/" + month
                + ", End Date: " + getEndDay() + "/" + endMonth + ", Guests: " + guests + "\nTotal Price: $" + totalPrice;
    }

    //Returns total price of the booking
    public double getTotalPrice() {
        return totalPrice;
    }

    //Sets total price of the booking
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
  
    public int getDay() {
        return day;
    }

    public int getEndDay() {
        return endDay;
    }
    //Returns a string suitable for saving to a file

    public String toFileString() {
        return time + "," + getDay() + "," + month + "," + getEndDay() + "," + endMonth + "," + roomNumber + "," + guests + "," + totalPrice;
    }
}
