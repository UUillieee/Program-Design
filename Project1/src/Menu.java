
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
   
    public static void main(String[] args ){
        Scanner s = new Scanner(System.in);
        
        CollectInfo collectInfo = new CollectInfo();
        
       listHotels();
    }
    
    public static void listHotels() {
        // Read File T02_Scores
       // HashMap<String, Integer> records = new HashMap<>();
        try {
            FileReader fr = new FileReader("./resources/Hotels.txt");
            BufferedReader inStream = new BufferedReader(fr);
            String line = null;
            while ((line = inStream.readLine()) != null) {
                String str[] = line.split(" ");
                //records.put(str[0], Integer.parseInt(str[1]));
                System.out.print("Hotel: " +str[0]);
                System.out.print(" Location: " +str[1]);
                System.out.print(" Rooms Available: " +str[2] + "\n");
            }
            inStream.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(Hotel.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        
    }
}
