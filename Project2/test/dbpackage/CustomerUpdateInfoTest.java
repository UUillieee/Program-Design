/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dbpackage;

import Model.Customer;
import org.junit.Test;
import static org.junit.Assert.*;

//to delete the customer
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author billw
 */

//CustomerUpdateInfo test, it inserting and getting customer from the database
//deletes the customer once finished with the test

public class CustomerUpdateInfoTest {

    @Test
    public void testInsertAndGetCustomer() {
        CustomerUpdateInfo customerInfo = new CustomerUpdateInfo();

        //test username and password
        String testUsername = "testingname";
        String testPassword = "testingpassword";

        //insert customer
        customerInfo.insertCustomer(testUsername, testPassword);

        //retrieve customer using valid credentials
        Customer retrieved = customerInfo.getCustomer(testUsername, testPassword);

        assertNotNull("Customer should be retrieved with valid credentials", retrieved);
        //check if they match
        assertEquals("Username should match", testUsername, retrieved.getUsername());

        //retrieve customer using wrong credentials
        Customer shouldBeNull = customerInfo.getCustomer(testUsername, "wrongPassword");
        assertNull("Customer should not be retrieved with wrong password", shouldBeNull);
        
        //delete the test customer
        deleteTestCustomer(retrieved.getId());
    }
    
    //method to delete a customer from the db after the test is done
    private void deleteTestCustomer(int customerId) {
        String deleteSQL = "DELETE FROM Customers WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {
            stmt.setInt(1, customerId);
            int rows = stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error deleting test customer: " + e.getMessage());
        }
    }
}
