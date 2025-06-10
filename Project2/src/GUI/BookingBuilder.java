/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;


import Model.Booking;
import Model.Customer;
import Model.Hotel;

/**
 *
 * @author George
 */
public class BookingBuilder {
    private Hotel hotel; // not used in final booking. Just used to display rooms
    private Customer customer;
    private int time;
    private int day;
    private int month;
    private int endDay;
    private int endMonth;
    private int roomNumber;
    private int guests;
    private double totalPrice;
    private int lengthOfStay;

    //Setters
    public void setHotel(Hotel hot){ this.hotel = hot;}
    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setTime(int time) { this.time = time; }
    public void setDay(int day) { this.day = day; }
    public void setMonth(int month) { this.month = month; }
    public void setEndDay(int endDay) { this.endDay = endDay; }
    public void setEndMonth(int endMonth) { this.endMonth = endMonth; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public void setGuests(int guests) { this.guests = guests; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setLengthOfStay(int nights) {this.lengthOfStay = nights;}
  
    //Getters
    public Hotel getHotel(){
        return this.hotel;
    }
    
    public Customer getCustomer() {
        return this.customer; 
    }
    
    public int getLengthOfStay() {
        return lengthOfStay;
    }
    //Test method
    public String getBookingInfo(){
         return "Start date: "+this.day+ " Length "+this.lengthOfStay+ " end day: "+this.endDay;
    }
    // Final build method
    public Booking build() {
        return new Booking(time, day, month, endDay, endMonth, roomNumber, guests, totalPrice);
    }
    
}
