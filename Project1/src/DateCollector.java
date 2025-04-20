//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Scanner;
//import java.util.Map;
//
///**
// *
// * @author William Bindon
// */
//
//public class DateCollector {
//    private int time;
//    private int day;
//    private int month;
//    private int endDay;
//    private int endMonth;
//    private String name;
//    
//    private static final String FILE_NAME = "./resources/CustomerInfo.txt";
//    private final Map<String, Date> userInfo = new HashMap<>();
//    
//    private static final int[] DAYS_IN_MONTH = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
//    
//    class Date {
//        Integer time;
//        Integer day;
//        Integer month;
//        Integer endDay;
//        Integer endMonth;
//    
//        Date(Integer time, Integer day, Integer month, Integer endDay, Integer endMonth) {
//            this.time = time;
//            this.day = day;
//            this.month = month;
//            this.endDay = endDay;
//            this.endMonth = endMonth;
//        }
//        
//        @Override
//        public String toString() {
//            return "Check-in Time: " + time + ":00, Start Date: " + month + "/" + day +
//                   ", End Date: " + endMonth + "/" + endDay;
//        }
//    }
//    
//    public DateCollector() {
//        loadExistingData();
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("\nEnter your name:");
//        name = scanner.nextLine().trim();
//        
//        if (userInfo.containsKey(name)) {
//            System.out.println("Welcome back, " + name + "! Here is your booking:");
//            System.out.println(userInfo.get(name));
//        } else {
//            collectBookingDetails(scanner);
//            userInfo.put(name, new Date(time, day, month, endDay, endMonth));
//            saveDate();
//            System.out.println("Your booking has been saved: " + userInfo.get(name));
//        }
//    }
//    
//    private void collectBookingDetails(Scanner scanner) {
//        do {
//            System.out.println("What month would you like to stay? (1-12)");
//            month = scanner.nextInt();
//        } while (month < 1 || month > 12);
//
//        do {
//            System.out.println("What day of the month would you like to stay? (1-" + DAYS_IN_MONTH[month] + ")");
//            day = scanner.nextInt();
//        } while (day < 1 || day > DAYS_IN_MONTH[month]);
//
//        int duration;
//        do {
//            System.out.println("How many days would you like to stay?");
//            duration = scanner.nextInt();
//        } while (duration < 1);
//
//        do {
//            System.out.println("What time of the day would you like to arrive? (24hr Time, between 8:00-19:00)");
//            time = scanner.nextInt();
//        } while (time < 8 || time > 19);
//
//        endDay = day + duration;
//        endMonth = month;
//
//        while (endDay > DAYS_IN_MONTH[endMonth]) {
//            endDay -= DAYS_IN_MONTH[endMonth];
//            endMonth++;
//            if (endMonth > 12) { 
//                endMonth = 1;
//            }
//        }
//    }
//    
//    private void loadExistingData() {
//    File file = new File(FILE_NAME);
//    if (!file.exists()) return;
//
//    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
//        String line;
//        while ((line = reader.readLine()) != null) {
//            String[] parts = line.split(": ");
//            if (parts.length == 2) {
//                String[] data = parts[1].split(",");
//                if (data.length == 5) {
//                    userInfo.put(parts[0], new Date(
//                        Integer.parseInt(data[0]), Integer.parseInt(data[1]),
//                        Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4])
//                    ));
//                }
//            }
//        }
//    } catch (IOException e) {
//        System.out.println("Error loading existing data: " + e.getMessage());
//    }
//    }
//    
//    private void saveDate() {
//    try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
//        Date date = userInfo.get(name);
//        writer.println(name + ": " + date.time + "," + date.day + "," + date.month + "," + date.endDay + "," + date.endMonth);
//        System.out.println("Booking information saved successfully.");
//    } catch (IOException e) {
//        System.out.println("Error saving date records: " + e.getMessage());
//    }
//}
//
//}
