
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 
/**
 *
 * @author billw
 */
public class DateCollector {
    private int time;
    private int day;
    private int month;
    private int endDay;
    private int endMonth;
    private String name;
    
    private static final String FILE_NAME = "./resources/CustomerInfo.txt";
    private final Map<String, Date> userInfo = new HashMap<>();
    
    private static final int[] DAYS_IN_MONTH = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    
    class Date{
        Integer time;
        Integer day;
        Integer month;
        Integer endDay;
        Integer endMonth;
    
        Date(Integer time, Integer day, Integer month, Integer endDay, Integer endMonth){
            this.time = time;
            this.day = day;
            this.month = month;
            this.endDay = endDay;
            this.endMonth = endMonth;
        }
        
        @Override
        public String toString() {
            return "Check-in Time: " + time + ":00, Start Date: " + month + "/" + day +
                   ", End Date: " + endMonth + "/" + endDay;
        }
    }
    
    public DateCollector(){
        
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("What month would you like to stay? (1-12)");
            month = scanner.nextInt();
        } while (month < 1 || month > 12);

        do {
            System.out.println("What day of the month would you like to stay? (1-" + DAYS_IN_MONTH[month] + ")");
            day = scanner.nextInt();
        } while (day < 1 || day > DAYS_IN_MONTH[month]);

        int duration;
        do {
            System.out.println("How many days would you like to stay?");
            duration = scanner.nextInt();
        } while (duration < 1);

        do {
            System.out.println("What time of the day would you like to arrive? (24hr Time, between 8:00-19:00)");
            time = scanner.nextInt();
        } while (time < 8 || time > 19);

        endDay = day + duration;
        endMonth = month;

        while (endDay > DAYS_IN_MONTH[endMonth]) {
            endDay -= DAYS_IN_MONTH[endMonth];
            endMonth++;
            //December rolls over to January
            if (endMonth > 12) { 
                endMonth = 1;
            }
        }
        do{
            System.out.println("What is the name you would like the room to be under?\n");
            name = scanner.nextLine().trim();
        } while (!name.matches("^[a-zA-Z]+(?: [a-zA-Z]+)*$"));
        
        
        userInfo.put(name, new Date(time, day, month, endDay, endMonth));
        
        System.out.println("Your booking information is: " + userInfo.get(name));
        
        saveDate();
        
        scanner.close();
    }
    private void saveDate() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, Date> entry : userInfo.entrySet()) {
                writer.println(entry.getKey() + ": " + entry.getValue());
            }
            System.out.println("Booking information saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving date records: " + e.getMessage());
        }
    }
}



