/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author billw
 */

//tests for booking builder class
public class BookingBuilderTest {

    private BookingBuilder bookingBuilder;
    
    //before to to initialize BookingBuilder fresh before each test 
    @Before
    public void setUp() {
        bookingBuilder = new BookingBuilder();
    }

    //tests all setter and getter methods to ensure correct values are stored and retrieved
    //tests that totalPrice is calculated correctly (roomPrice * nights)
    @Test
    public void testSettersAndGetters() {
        //create objects
        Hotel mockHotel = new Hotel(1, "test Hotel", "test location");
        Customer mockCustomer = new Customer(101, "john doe");
        
        //set properties on bookingBuilder
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
        //calculate 150 * 3 = 450
        bookingBuilder.setTotalPrice(); 
        
        //assert that getters return correct value
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
        //0.01 allow slight float tolerance
        assertEquals(450.0, bookingBuilder.getTotalPrice(), 0.01);
    }
    
    
    //tests that the build() method creates a Booking object with correctly populated fields
    @Test
    public void testBuildCreatesBooking() {
        BookingBuilder builder = new BookingBuilder();

        //set up mock customer with known ID
        Customer customer = new Customer(101, "testname");
        builder.setCustomer(customer);

        //set details in the fields
        builder.setTime(14);
        builder.setDay(5);
        builder.setMonth(6);
        builder.setEndDay(7);
        builder.setEndMonth(6);
        builder.setRoomNumber(202);
        builder.setGuests(2);
        builder.setLengthOfStay(2);
        builder.setRoomPrice(150.0);
        //triggers calculation
        builder.setTotalPrice(); 

        //build the booking object
        Booking booking = builder.build();

        //validate the contents of the booking
        assertEquals(101, booking.getCustomerId());
        assertEquals(14, booking.getTime());
        assertEquals(5, booking.getDay());
        assertEquals(6, booking.getMonth());
        assertEquals(7, booking.getEndDay());
        assertEquals(6, booking.getEndMonth());
        assertEquals(202, booking.getRoomNumber());
        assertEquals(2, booking.getGuests());
        //0.01 allow slight float tolerance
        // 150 * nights
        assertEquals(300.0, booking.getTotalPrice(), 0.01); 
    }
}
