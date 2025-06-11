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
    private int customerId;
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
    public void setCustomer(Customer customer) {
        this.customer = customer;
        if (customer != null) {
            this.customerId = customer.getId();
        }
    }
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
    public Hotel getHotel(){return this.hotel;}
    
    public int getCustomerId() {return customerId;}
    
    public int getLengthOfStay() {return lengthOfStay;}
    
    public Customer getCustomer() {return customer;}
    
    public int getTime() {return time;}
    public int getDay() {return day;}
    public int getMonth() {return month; }
    public int getEndDay() {return endDay;}
    public int getEndMonth() {return endMonth;}
    public int getRoomNumber() {return roomNumber;}
    public int getGuests() {return guests; }
    public double getTotalPrice() {return totalPrice;}

    // Final build method
    public Booking build() {
        return new Booking(getTime(), getCustomerId(), getDay(), getMonth(), getEndDay(), getEndMonth(), getRoomNumber(), getGuests(), getTotalPrice());
    }

    
    
}
