
package Project3.Part3;

import Project3.CityInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 3: ExpediaishDriver driver and user interface for part three
 * Daniel Kerr and Charles So
 * Date: 08/01/2014
 * File: ExpediaishDriver.java
 */
public class ExpediaishDriver {

    
    public ExpediaishDriver(){
        
    }
    
    // Prints the city choices. 
    public static void printChoices(CityInfo[] cityInfo){
        for(CityInfo city : cityInfo){
            System.out.println("\t" + city.getCityName() + " (" + city.getCityCode() + ")");
        }
    }
    
    // Prints the available city choices, overloaded to not print the city chosen
    // to start in
    public static void printChoices(CityInfo[] cityInfo, String start){
        for(CityInfo city : cityInfo){
            if(CityInfo.CitySet.getCityOrdinal(city.getCityCode()) != CityInfo.CitySet.getCityOrdinal(start))
            System.out.println("\t" + city.getCityName() + " (" + city.getCityCode() + ")");
        }
    }
    
    // Simple input verification
    public static boolean verifyInput(String input, CityInfo[] cityInfo){
        String lower = input.toLowerCase();
        HashSet<String> options = new HashSet<String>();
        for(CityInfo city : cityInfo){
            options.add(city.getCityCode().toLowerCase());
            options.add(city.getCityName().toLowerCase());
        }
        if(options.contains(lower)){
            return false;
        }
        else{
            System.out.println("Sorry, I didn't understand that...");
            return true;
        }
    }
    
    // Simple input verification, overloaded to check for someone trying to travel to 
    // the same city they started in
    public static boolean verifyInput(String input, CityInfo[] cityInfo, String start){
        String lower = input.toLowerCase();
        HashSet<String> options = new HashSet<String>();
        for(CityInfo city : cityInfo){
            options.add(city.getCityCode().toLowerCase());
            options.add(city.getCityName().toLowerCase());
        }
        if(options.contains(lower)){
            if(lower.equals(start.toLowerCase())){
                System.out.println("You can't travel to your starting city!");
                return true;
            }
            else{
                return false;
            }
        }
        else{
            System.out.println("Sorry, I didn't understand that...");
            return true;
        }
    }
    
    //Simple user interface, loops through until you tell it to quit
    public static void main(String[] args) throws IOException {
        PartThree partThree = new PartThree();
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String quitString = "";
        do{
            System.out.println("Welcome to Expedia-ish! Your spot for low-cost-ish airfare.");
            System.out.print("Please choose, from the following list, a city from"
                    + " which to start your travels:\n");
            String startCity = "";
            do{
            ExpediaishDriver.printChoices(partThree.getCityInfo());
            startCity = bufferRead.readLine();
            }
            while(ExpediaishDriver.verifyInput(startCity, partThree.getCityInfo()));
            System.out.println("Excellent, now, where do you want to go?");
            String endCity = "";
            do{
            ExpediaishDriver.printChoices(partThree.getCityInfo(), startCity);
            endCity = bufferRead.readLine();
            }
            while(ExpediaishDriver.verifyInput(endCity, partThree.getCityInfo(), startCity));
            partThree.findBestThree(startCity, endCity);
            
            System.out.println("Type 'quit' or 'q' to quit, anything else to plan another trip.");
            quitString = bufferRead.readLine();
        }
        while(!"quit".equals(quitString.toLowerCase()) && !"q".equals(quitString.toLowerCase()));
    }
    
}
