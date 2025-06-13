/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package GUI;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Model.Booking;
import Controller.BookingBuilder;

/**
 *
 * @author billw
 */

//This test checks that BookingBuilder class correctly constructs a Booking object with all of the expected values
public class BookingBuilderTest {

    private BookingBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new BookingBuilder();
    }

    @Test
    void testBuildBookingWithValidData() {
        builder.setCustomerId(1);
        builder.setTime(14);
        builder.setDay(5);
        builder.setMonth(6);
        builder.setEndDay(8);
        builder.setEndMonth(6);
        builder.setRoomNumber(101);
        builder.setGuests(2);
        builder.setTotalPrice(300);

        Booking booking = builder.build();

        assertEquals(1, booking.getCustomer());
        assertEquals(14, booking.getTime());
        assertEquals(5, booking.getDay());
        assertEquals(6, booking.getMonth());
        assertEquals(8, booking.getEndDay());
        assertEquals(6, booking.getEndMonth());
        assertEquals(101, booking.getRoomNumber());
        assertEquals(2, booking.getGuests());
        assertEquals(200, booking.getTotalPrice());
    }
}
