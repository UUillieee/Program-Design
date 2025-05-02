
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author George
 * @author William Bindon
 */

//This is the main class and should be the file that is run
//This class contains method calls and initializations of 

public class Menu {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        //Write Menu Here, call all methods to read in data
        String input = "";

        do {
            System.out.println("\nWelcome to the hotel booking system. 'q' to quit at any time.");
            System.out.println("1) View Hotels");
            System.out.println("2) View Rooms");
            System.out.println("3) Make a booking or view your Booking");
            //If not a number
            while (!s.hasNextLine()) {
                System.out.print("Invalid input. Please enter a number: ");
                s.next(); //discard invalid input
            }
            input = s.nextLine();
            switch (input) {
                case "1":
                    System.out.println("\nViewing hotels");
                    //initialize HotelManager
                    HotelManager hotelManager = new HotelManager();
                    //initialize HotelDisplay to handle display
                    HotelDisplay hotelDisplay = new HotelDisplay();
                    //display hotels with HotelDisplay class
                    hotelDisplay.displayHotels(hotelManager.getHotels());
                    break;
                case "2":
                    System.out.println("\nViewing rooms");
                    //call Method
                    RoomManager.displayHotelRooms();
                    break;
                case "3":
                    System.out.println("\nEnter Booking Details");
                    //get required arguments for booking controller
                    //create list of hotels
                    HashMap<String, Hotel> hotels = HotelFileReader.readHotels();
                    //create rooms in hotels
                    RoomManager.readRooms(hotels); 
                    //load date service
                    DateService ds = new DateService(); 
                    //load Booking Manager
                    BookingManager bm = new BookingManager("./resources/CustomerInfo.txt"); 
                    //pass dateservice, bookingmanager and hotel list into the controller
                    BookingController controller = new BookingController(bm, ds, hotels);
                    //run the controller
                    try {
                        controller.run();
                    } catch (InputMismatchException e) {
                        System.out.println("Input Not Correct");
                    } catch (BookingCancelledException e) {
                        System.out.println("Booking was cancelled: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Something went wrong: " + e.getMessage());
                    }
                    break;
                //if input == q, quti program
                case "q":
                    System.out.println("\nExiting, Thanks!");
                    break;
                default:
                    System.out.println("Invalid Input. Try Again.");
            }

        } while (!input.equalsIgnoreCase(
                "q"));
        s.close();
    }
}
