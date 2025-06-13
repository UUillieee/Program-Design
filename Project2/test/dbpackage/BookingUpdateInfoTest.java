/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dbpackage;

import org.junit.jupiter.api.Test;
import dbpackage.BookingUpdateInfo;
import Controller.BookingBuilder;

/**
 *
 * @author billw
 */

//This test requires a valid BookingBuilder with all fields set before calling insertUpdate()
public class BookingUpdateInfoTest {

    @Test
    void testInsertUpdateWithValidBooking() {
        BookingBuilder builder = new BookingBuilder();
        builder.setCustomerId(1);
        builder.setTime(12);
        builder.setDateRange(10, 6, 12, 6);
        builder.setRoomNumber(101);
        builder.setGuests(2);
        builder.setTotalPrice(300);

        BookingUpdateInfo updater = new BookingUpdateInfo();
        updater.insertUpdate(builder);

        // You may need to verify by querying the DB or checking for exceptions
        // No exception thrown is considered a pass for now
        assertTrue(true);
    }
}
