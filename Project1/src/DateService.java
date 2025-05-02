/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author William Bindon
 */
import java.util.Scanner;

public class DateService {

    private static final int[] DAYS_IN_MONTH = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int MAX_GUESTS = 5;

    public Booking collectBookingDetails(Scanner scanner) throws BookingCancelledException {
        int roomNumber = getValidRoomNumber(scanner);
        int month = getValidMonth(scanner);
        int day = getValidDay(scanner, month);
        int duration = getValidDuration(scanner);
        int time = getValidArrivalTime(scanner);
        int guests = getValidGuests(scanner, roomNumber);
        int[] endDate = calculateEndDate(day, month, duration);

        return new Booking(time, day, month, endDate[0], endDate[1], roomNumber, guests,0);
    }

    private int getValidRoomNumber(Scanner scanner) {
        int roomNumber = -1;
        int maxRoomNumber = RoomManager.getMaxRoomNumber();
        System.out.println("Max rooms " + maxRoomNumber);
        RoomManager.displayHotelRooms();
        do {
            System.out.println("What Room Would you like to book? Enter Room #");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("q")) {
                throw new BookingCancelledException("Booking cancelled by user.");
            }
            //Catch invalid input
            try {
                roomNumber = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number or 'q' to quit. Try again.");
                //scanner.next(); // clear scanner to prevent infinite loop
                continue;
            }
            //Check roomnumber is in bounds
            if (roomNumber < 0 || roomNumber > maxRoomNumber) {
                System.out.println("Invalid room number. Try again.");
                continue;
            }
            //Condition to check availabiity of room
            if (!RoomManager.isRoomAvailable(roomNumber)) {
                System.out.println("This room is not available. Choose another.");
                continue;
            }

            break;
        } while (true); // fix double booking
        return roomNumber;
    }

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
        } while (month < 1 || month > 12);
        return month;
    }

    private int getValidDay(Scanner scanner, int month) {
        int day = -1;
        do {
            System.out.println("What day of the month? (1–" + DAYS_IN_MONTH[month] + ")");
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

    private int getValidArrivalTime(Scanner scanner) {
        int time = -1;
        do {
            System.out.println("Arrival time (24hr format, between 8:00–19:00):");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("q")) {
                throw new BookingCancelledException("Booking cancelled by user.");
            }
            try {
                time = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number or 'q' to quit.");
            }
        } while (time < 8 || time > 19);
        return time;
    }

    private int getValidGuests(Scanner scanner, int roomNumber) throws BookingCancelledException {
        int guests = 1;
        int maxGuests = RoomManager.getMaxGuestsForRoom(roomNumber); // Dynamic Variable to update according to max guests in each individual room
        do {
            System.out.println("How many guests?");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("q")) {
                throw new BookingCancelledException("Booking cancelled by user.");
            }
            try {
                guests = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number or 'q' to quit.");
            }
        } while (guests < 1 || guests >= maxGuests);
        return guests;
    }

    private int[] calculateEndDate(int day, int month, int duration) {
        int endDay = day + duration;
        int endMonth = month;
        while (endDay > DAYS_IN_MONTH[endMonth]) {
            endDay -= DAYS_IN_MONTH[endMonth];
            endMonth++;
            if (endMonth > 12) {
                endMonth = 1;
            }
        }
        return new int[]{endDay, endMonth};
    }
}
