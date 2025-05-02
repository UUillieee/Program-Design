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
    private double totalPrice = 0;
    // Maybe add a total price by taking amount of nights and then price per night 

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

    public int getRoomNumber() {
        return roomNumber;
    }

    @Override
    public String toString() {
        return "Check-in Time: " + time + ":00, Start Date: " + getDay() + "/" + month
                + ", End Date: " + getEndDay() + "/" + endMonth + ", Guests: " + guests + "\nTotal Price: $" + totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getDay() {
        return day;
    }

    public int getEndDay() {
        return endDay;
    }

    //puts details in file
    public String toFileString() {
        return time + "," + getDay() + "," + month + "," + getEndDay() + "," + endMonth + "," + roomNumber + "," + guests + "," + totalPrice;
    }
}
