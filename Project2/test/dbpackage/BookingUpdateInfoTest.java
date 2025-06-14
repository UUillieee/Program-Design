/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dbpackage;

import Model.BookingBuilder;
import Model.Customer;
import Model.Hotel;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.*;
import org.junit.After;
import org.junit.Before;

/**
 *
 * @author billw
 */

//this test tests the class BookingUpdateInfo, which writes bookings to the database
public class BookingUpdateInfoTest {

    private BookingUpdateInfo bookingUpdateInfo;
    private int insertedBookingId;

    @Before
    public void setUp() {
        bookingUpdateInfo = new BookingUpdateInfo();
    }

    @Test
    public void testInsertUpdateAndDeleteBooking() {
        //setup BookingBuilder
        BookingBuilder builder = new BookingBuilder();
        //set the hotel and location
        builder.setHotel(new Hotel(1, "testhotel", "testlocation"));
        //set ID and username
        builder.setCustomer(new Customer(9999, "testname"));
        builder.setTime(12);
        builder.setDay(10);
        builder.setMonth(6);
        builder.setEndDay(12);
        builder.setEndMonth(6);
        builder.setRoomNumber(121);
        builder.setGuests(2);
        builder.setRoomPrice(100.0);
        builder.setLengthOfStay(2);
        builder.setTotalPrice();

        //insert booking
        bookingUpdateInfo.insertUpdate(builder);
        //get the booking from the last ID inserted, -1 because getNextBookingId() +1
        insertedBookingId = bookingUpdateInfo.getNextBookingId() - 1;

        //retrieve last inserted booking directly from DB for check
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT * FROM Bookings WHERE id = ?""")) {

            stmt.setInt(1, insertedBookingId);
            ResultSet rs = stmt.executeQuery();

            assertTrue("Booking was not found", rs.next());
            //check the returned booking values
            assertEquals(9999, rs.getInt("customerId"));
            assertEquals(101, rs.getInt("roomNumber"));
            assertEquals(200, rs.getInt("totalPrice")); // 100 * 2
            assertTrue(rs.getBoolean("isBooked"));

        } 
        catch (SQLException e) {
            fail("Database error during test: " + e.getMessage());
        }
    }
    
    //after the test delete the bookinhg under the ID
    @After
    public void tearDown() {
        //delete bookingId
        if (insertedBookingId > 0) {
            bookingUpdateInfo.deleteBookingById(insertedBookingId);
        }
    }
}

