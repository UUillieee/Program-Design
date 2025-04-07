

import java.util.Scanner;


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
            System.out.println("\nWelcome to the hotel booking system.");
            System.out.println("1) View Hotels");
            System.out.println("2) View Rooms");
            System.out.println("3) Make a booking or view your Booking");
            System.out.println("4) Exit");
            
            //If not a number
              while (!s.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number: ");
                s.next(); // discard invalid input
            }
              input = s.nextInt();
            switch (input){
                case 1:
                    System.out.println("\nViewing hotels");
                    //Call method
                    HotelManager.displayHotels();
                    break;
                case 2:
                    System.out.println("\nViewing rooms");
                    //Call Method
                    RoomManager.displayHotelRooms();
                    break;
                case 3:
                    System.out.println("\nMake a booking or view your Booking");
                    //Call method
                    //Booking.makeBooking();
                    DateService ds = new DateService();
                    BookingManager bm = new BookingManager("./resources/CustomerInfo.txt");
                    BookingController controller = new BookingController(bm,ds);
                    controller.run();
                    break;
                case 4:
                    System.out.println("\nView booking");
                    //Call Method
                    break;
                case 5:
                    System.out.println("\nExiting, Thanks!");
                    break;
            }
        }while(input != 4);
        s.close();

        

       //DateCollector dateCollector = new DateCollector();
        
    }

  

     
}
