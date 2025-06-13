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
//Listener to update rooms panel based on hotel selected in hotel panel.
public interface BookingListener {

    void updateBookingInfo();

    void onHotelSelected(Hotel hotel);
}

