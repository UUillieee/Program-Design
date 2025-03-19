
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 
/**
 *
 * @author billw
 */
public class CollectInfo {
    private int time;
    private int day;
    private int month;
    private int endDay;
    private int endMonth;
    private int people;
    
    public CollectInfo(){
        Scanner scanner = new Scanner(System.in);
        month = scanner.nextInt();
        System.out.println("What month would you like to stay? (1-12)");
        day = scanner.nextInt();
        System.out.println("What day of the month would you like stay? (1-30)");
        time = scanner.nextInt();
        System.out.println("What time of the day would you like to arrive? (8am - 7pm))");
        
    }
}
