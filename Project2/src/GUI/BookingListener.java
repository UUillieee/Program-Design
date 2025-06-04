/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Model.Hotel;

/**
 *
 * @author George
 */

public interface BookingListener {

    void updateBookingInfo();

    default void onHotelSelected(Hotel hotel) {
    }
}
