/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dbpackage;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import dbpackage.CustomerUpdateInfo;
import Model.Customer;
/**
 *
 * @author billw
 */

//This test requires mocking or populating a test database first
public class CustomerUpdateInfoTest {

    @Test
    void testGetCustomerWithValidCredentials() {
        CustomerUpdateInfo customerInfo = new CustomerUpdateInfo();
        // Insert customer into test DB first if needed
        Customer customer = customerInfo.getCustomer("testuser", "testpass");

        assertNotNull(customer);
        assertEquals("testuser", customer.getUsername());
    }

    @Test
    void testGetCustomerWithInvalidCredentials() {
        CustomerUpdateInfo customerInfo = new CustomerUpdateInfo();
        Customer customer = customerInfo.getCustomer("wronguser", "wrongpass");

        assertNull(customer);
    }
}
