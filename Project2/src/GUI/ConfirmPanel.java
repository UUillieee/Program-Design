/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.event.ActionListener;

/**
 *
 * @author George
 */
public class ConfirmPanel {

    private final HotelFrame mainFrame;
    private final BookingBuilder builder;
    private final ActionListener controller;

    public ConfirmPanel(HotelFrame mainFrame, BookingBuilder builder, ActionListener controller) {
        this.mainFrame = mainFrame;
        this.builder = builder;
        this.controller = controller;
    }

    public void createConfirmPanel(){
        
    }
    
}
