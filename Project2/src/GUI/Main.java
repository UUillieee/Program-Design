package GUI;

import java.awt.Dimension;
import javax.swing.JFrame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author George
 */
public class Main {

    public static void main(String[] args) {
        HotelFrame f = new HotelFrame();
        f.setMinimumSize(new Dimension(800, 600));  // Ensure decent minimum - so it doesnt jumble words when sized too small
        f.setResizable(true);                       // Allow resizing

    }

}
