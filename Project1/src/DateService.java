/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author will
 */
import java.util.Scanner;

public class DateService {
    private static final int[] DAYS_IN_MONTH = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public Booking collectBookingDetails(Scanner scanner) {
        int month, day, time, duration;

        do {
            System.out.println("What month would you like to stay? (1-12)");
            month = scanner.nextInt();
        } while (month < 1 || month > 12);

        do {
            System.out.println("What day of the month would you like to stay? (1-" + DAYS_IN_MONTH[month] + ")");
            day = scanner.nextInt();
        } while (day < 1 || day > DAYS_IN_MONTH[month]);

        do {
            System.out.println("How many days would you like to stay?");
            duration = scanner.nextInt();
        } while (duration < 1);

        do {
            System.out.println("Arrival time (24hr, between 8:00-19:00):");
            time = scanner.nextInt();
        } while (time < 8 || time > 19);

        int endDay = day + duration;
        int endMonth = month;

        while (endDay > DAYS_IN_MONTH[endMonth]) {
            endDay -= DAYS_IN_MONTH[endMonth];
            endMonth++;
            if (endMonth > 12) endMonth = 1;
        }

        return new Booking(time, day, month, endDay, endMonth);
    }
}

