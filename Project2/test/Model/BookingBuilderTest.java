/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author billw
 */
public class BookingBuilderTest {

    private BookingBuilder bookingBuilder;
    
    //before to to initialize BookingBuilder fresh before each test 
    @Before
    public void setUp() {
        bookingBuilder = new BookingBuilder();
    }

    @Test
    public void testSettersAndGetters() {
        Hotel mockHotel = new Hotel(1, "test Hotel", "test location");
        Customer mockCustomer = new Customer(101, "john doe");

        bookingBuilder.setHotel(mockHotel);
        bookingBuilder.setCustomer(mockCustomer);
        bookingBuilder.setTime(10);
        bookingBuilder.setDay(15);
        bookingBuilder.setMonth(6);
        bookingBuilder.setEndDay(18);
        bookingBuilder.setEndMonth(6);
        bookingBuilder.setRoomNumber(202);
        bookingBuilder.setGuests(2);
        bookingBuilder.setRoomPrice(150.0);
        bookingBuilder.setLengthOfStay(3);
        bookingBuilder.setTotalPrice(); // Calculates 150 * 3 = 450

        assertEquals(mockHotel, bookingBuilder.getHotel());
        assertEquals(mockCustomer, bookingBuilder.getCustomer());
        assertEquals(101, bookingBuilder.getCustomerId());
        assertEquals(10, bookingBuilder.getTime());
        assertEquals(15, bookingBuilder.getDay());
        assertEquals(6, bookingBuilder.getMonth());
        assertEquals(18, bookingBuilder.getEndDay());
        assertEquals(6, bookingBuilder.getEndMonth());
        assertEquals(202, bookingBuilder.getRoomNumber());
        assertEquals(2, bookingBuilder.getGuests());
        assertEquals(3, bookingBuilder.getLengthOfStay());
        assertEquals(450.0, bookingBuilder.getTotalPrice(), 0.01);
    }

    @Test
    public void testBuildCreatesBooking() {
        Customer customer = new Customer(101, "jane doe");

        bookingBuilder.setCustomer(customer);
        bookingBuilder.setTime(12);
        bookingBuilder.setDay(5);
        bookingBuilder.setMonth(7);
        bookingBuilder.setEndDay(8);
        bookingBuilder.setEndMonth(7);
        bookingBuilder.setRoomNumber(303);
        bookingBuilder.setGuests(1);
        bookingBuilder.setRoomPrice(100.0);
        bookingBuilder.setLengthOfStay(2);
        bookingBuilder.setTotalPrice(); // 100 * 2 = 200

        Booking booking = bookingBuilder.build();

        assertEquals(101, booking.getCustomerId());
        assertEquals(12, booking.getTime());
        assertEquals(5, booking.getDay());
        assertEquals(7, booking.getMonth());
        assertEquals(8, booking.getEndDay());
        assertEquals(7, booking.getEndMonth());
        assertEquals(303, booking.getRoomNumber());
        assertEquals(1, booking.getGuests());
        assertEquals(200.0, booking.getTotalPrice(), 0.01);
    }
}
