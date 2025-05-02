/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * this class is responsible for collecting and validating booking details from the user
 * it handles date validation, room availability checks, and guest limit validation
 * @author William Bindon
 * 
 */
import java.util.Map;
import java.util.Scanner;

public class DateService {

    //days in each month (0-index unused to match month numbers 1–12)
    private static final int[] DAYS_IN_MONTH = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int MAX_GUESTS = 5;


    //main method to collect all booking details from the user
    public Booking collectBookingDetails(Scanner scanner,Map<String, Hotel> hotels) throws BookingCancelledException{
        int roomNumber = getValidRoomNumber(scanner,hotels);//Validate room selection
        int month = getValidMonth(scanner);//Validate month
        int day = getValidDay(scanner, month);//Validate day in the selected month
        int duration = getValidDuration(scanner);//Validate duration of stay
        int time = getValidArrivalTime(scanner);//Validate arrival time
        int guests = getValidGuests(scanner, roomNumber);//Validate number of guests
        int[] endDate = calculateEndDate(day, month, duration);//Calculate checkout date
        //return a new Booking object with collected data
        return new Booking(time, day, month, endDate[0], endDate[1], roomNumber, guests,0);

    }

    //returns a correct room number
    private int getValidRoomNumber(Scanner scanner,Map<String, Hotel> hotels) {
        int roomNumber = -1;
        int maxRoomNumber = RoomManager.getMaxRoomNumber();
        System.out.println("Max rooms " + maxRoomNumber);
        RoomManager.displayHotelRooms(); // Show available rooms to the user

        do {
            System.out.println("What Room Would you like to book? Enter Room #");
            String input = scanner.nextLine().trim();

            //allow user to cancel booking
            if (input.equalsIgnoreCase("q")) {
                throw new BookingCancelledException("Booking cancelled by user.");
            }

            //try parsing user input as integer
            try {
                roomNumber = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number or 'q' to quit. Try again.");
                continue;
            }

            //ensure room number is within valid range
            if (roomNumber < 0 || roomNumber > maxRoomNumber) {
                System.out.println("Invalid room number. Try again.");
                continue;
            }

            //check if room is available
            if (!RoomManager.isRoomAvailable(roomNumber,hotels)) {

                System.out.println("This room is not available. Choose another.");
                continue;
            }

            break;
        //loop until valid room number is entered
        } while (true); 

        return roomNumber;
    }

    //validates and returns a correct month
    private int getValidMonth(Scanner scanner) {
        int month = -1;
        do {
            System.out.println("What month would you like to stay? (1 - 12)");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                throw new BookingCancelledException("Booking cancelled by user.");
            }

            try {
                month = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number or 'q' to quit.");
            }
        //keep asking until valid month
        } while (month < 1 || month > 12); 

        return month;
    }

    //validates and returns a valid day of the selected month
    private int getValidDay(Scanner scanner, int month) {
        int day = -1;
        do {
            System.out.println("What day of the month? (1 – " + DAYS_IN_MONTH[month] + ")");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                throw new BookingCancelledException("Booking cancelled by user.");
            }

            try {
                day = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number or 'q' to quit.");
            }
        } while (day < 1 || day > DAYS_IN_MONTH[month]);

        return day;
    }

    //Validates and returns duration of the stay in days
    private int getValidDuration(Scanner scanner) {
        int duration = -1;
        do {
            System.out.println("How many days would you like to stay?");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                throw new BookingCancelledException("Booking cancelled by user.");
            }

            try {
                duration = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number or 'q' to quit.");
            }
        } while (duration < 1);

        return duration;
    }

    //Validates and returns a valid arrival time between 8:00–19:00
    private int getValidArrivalTime(Scanner scanner) {
        int time = -1;
        do {
            System.out.println("Arrival time (24hr format, between 8:00 – 19:00):");
            String input = scanner.nextLine().trim();
               if (input.equalsIgnoreCase("q")) {
                throw new BookingCancelledException("Booking cancelled by user.");
            }
            try {
            //split HH:00 so can read hours
            if (input.contains(":")) {
                String[] parts = input.split(":");
                if (parts.length == 2 && parts[1].equals("00")) {
                    time = Integer.parseInt(parts[0]);
                } else {
                    System.out.println("Please enter time in HH:00 format.");
                }
            } else {
                time = Integer.parseInt(input);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Enter a number or time in HH:00 format.");
        }

    } while (time < 8 || time > 19);


        return time;
    }
  
    //Validates and returns number of guests staying in the room
    private int getValidGuests(Scanner scanner, int roomNumber)  throws BookingCancelledException {
        int guests = -1;
        int maxGuests = RoomManager.getMaxGuestsForRoom(roomNumber); // Get max allowed guests for the selected room
        do {
            System.out.println("How many guests? Max for Room  #"+roomNumber+": "+maxGuests+" guests.");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                throw new BookingCancelledException("Booking cancelled by user.");
            }

            try {
                guests = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number or 'q' to quit.");
            }
        //Ensure guests are within valid range
        } while (guests < 1 || guests > maxGuests); 

        return guests;
    }

    //Calculates the checkout date by adding duration to the start date
    private int[] calculateEndDate(int day, int month, int duration) {
        int endDay = day + duration;
        int endMonth = month;

        //Loop through months if day exceeds current month's limit
        while (endDay > DAYS_IN_MONTH[endMonth]) {
            endDay -= DAYS_IN_MONTH[endMonth];
            endMonth++;
            if (endMonth > 12) {
                //loop around to January
                endMonth = 1; 
            }
        }
         //Return [day, month] of checkout
        return new int[]{endDay, endMonth};
    }
}
