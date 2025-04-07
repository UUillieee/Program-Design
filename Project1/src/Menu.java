
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author George
 */
public class Menu {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        //Write Menu Here, call all methods to read in data
        int input = 0;
        
        do {
            System.out.println("Welcome to the hotel booking system.");
            System.out.println("1) View Hotels");
            System.out.println("2) View Rooms");
            System.out.println("3) Make a Booking");
            System.out.println("5) Exit");
            
            //If not a number
              while (!s.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number: ");
                s.next(); // discard invalid input
            }
              input = s.nextInt();
            switch (input){
                case 1:
                    System.out.println("Viewing hotels");
                    //Call method
                    HotelManager.displayHotels();
                    break;
                case 2:
                    System.out.println("Viewing rooms");
                    //Call Method
                    RoomManager.displayHotelRooms();
                    break;
                case 3:
                    System.out.println("Make a booking");
                    //Call method
                    break;
                case 4:
                    System.out.println("View booking");
                    //Call Method
                    break;
                case 5:
                    System.out.println("Exiting, Thanks!");
                    break;
            }
        }while(input != 5);
        s.close();

        

       DateCollector dateCollector = new DateCollector();
        
    }

  

     
}
