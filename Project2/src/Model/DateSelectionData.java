/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.time.LocalDate;

/**
 *
 * @author George
 */

public class DateSelectionData {
    private final int day;
    private final int month;
    private final int time;
    private final int lengthOfStay;
    private final int endDay;
    private final int endMonth;

    // Constructor to set all values
    public DateSelectionData(int day, int month, int time, int lengthOfStay, int endDay, int endMonth) {
        this.day = day;
        this.month = month;
        this.time = time;
        this.lengthOfStay = lengthOfStay;
        this.endDay = endDay;
        this.endMonth = endMonth;
    }

    // Getters for all fields
    public int getDay() { return day; }
    public int getMonth() { return month; }
    public int getTime() { return time; }
    public int getLengthOfStay() { return lengthOfStay; }
    public int getEndDay() { return endDay; }
    public int getEndMonth() { return endMonth; }
}