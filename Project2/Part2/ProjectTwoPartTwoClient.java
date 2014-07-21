
package Project2.Part2;

import java.util.Scanner;

/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 2: Wrapper class where each part can be selected 
 * Daniel Kerr and Charles So
 * Date: 07/06/2014
 * File: ProjectTwoPartTwoClient.java
 */
public class ProjectTwoPartTwoClient {

    /**
     * Prints the main menu for the client
     */
    public static void printMenu(){
        System.out.println("Welcome to the Project Two by Daniel Kerr and Charles So.");
        System.out.println("Please pick a knapsack algorithm to run:");
        System.out.println("Type 'bf' for Brute Force Knapsack");
        System.out.println("Type 'gr' for Greedy Knapsack");
        System.out.println("Type 'ga' for Genetic Algorithm Knapsack");
        System.out.println("Type 'q' to quit.");
    }
    
    /**
     * prints the menu asking for the item count to use
     */
    public static void printHowManyMenu(){
        System.out.println("How many items do you want to choose from?");
        System.out.println("We can do '10', '20', '50' and '100'");
    }
    
    /**
     * Prints the menu asking what to set the max weight to
     * @param weights 
     */
    public static void printMaxWeightQuestion(String weights){
        System.out.println("What should the max weight be? For the number of objects"
                + " you are including we suggest:");
        if(weights.equals("weights10.txt")){
            System.out.println("86.412, 144.020, or 190.106");
        }
        else if(weights.equals("weights20.txt")){
            System.out.println("170.487, 284.145, and 375.071");
        }
        else if(weights.equals("weights50.txt")){
            System.out.println("396.553, 660.922, and 872.418");
        }
        else if(weights.equals("weights100.txt")){
            System.out.println("757.402, 1262.337, and 1666.286");
        }
        else{
            System.out.println("Daniel, your code derped somewhere");
            
        }
    }
    
    /**
     * Runs the Genetic algorithm
     * @param values
     * @param weights 
     */
    public static void runGA(String values, String weights){
        Scanner in = new Scanner(System.in);
        boolean loop = true;
        double maxWeight = 0.0;
        String trash = "";
        while(loop){
            printMaxWeightQuestion(weights);            
            if(in.hasNextDouble()){
                maxWeight = in.nextDouble();
                loop = false;
            }
            else{
                System.out.println("I don't think that is a valid max weight.");
                trash = in.nextLine();
            }
        }
        trash = in.nextLine();
        Store store = new Store(values, weights, maxWeight);
        Thread t = new Thread(new Part2C(store));
        t.start();
        System.out.println("Press enter to quit.");
        String quitString = in.nextLine();
        t.interrupt();
        in.close();
    }
    
    /**
     * runs the greedy algorithm
     * @param values
     * @param weights 
     */
    public static void runGR(String values, String weights){
        Scanner in = new Scanner(System.in);
        boolean loop = true;
        double maxWeight = 0.0;
        String trash = "";
        while(loop){
            printMaxWeightQuestion(weights);
            if(in.hasNextDouble()){
                maxWeight = in.nextDouble();
                loop = false;
            }
            else{
                System.out.println("I don't think that is a valid max weight.");
                trash = in.nextLine();
            }
        }
        Store store = new Store(values, weights, maxWeight);
        Part2B greedyKnapsack = new Part2B(store);
        greedyKnapsack.getGreedyKnapsack();
        in.close();
    }
    
    /**
     * runs the normal brute force algorithm
     * @param values
     * @param weights 
     */
    public static void runBF(String values, String weights){
        Scanner in = new Scanner(System.in);
        boolean loop = true;
        double maxWeight = 0.0;
        String trash = "";
        while(loop){
            printMaxWeightQuestion(weights);
            if(in.hasNextDouble()){
                maxWeight = in.nextDouble();
                loop = false;
            }
            else{
                System.out.println("I don't think that is a valid max weight.");
                trash = in.nextLine();
            }
        }
        
        Store store = new Store(values, weights, maxWeight);
        Part2A bruteKnapSack = new Part2A(store);
        bruteKnapSack.run();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /**
         * Simple text client  
         * 
         */
        ProjectTwoPartTwoClient.printMenu();
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        input.toLowerCase();
        while(!input.equalsIgnoreCase("q")){
            switch(input){
                case "bf":
                    ProjectTwoPartTwoClient.printHowManyMenu();
                    input = in.nextLine();
                    switch(input){
                        case "10":
                            ProjectTwoPartTwoClient.runBF("values10.txt", "weights10.txt");
                            break;
                        case "20":
                            ProjectTwoPartTwoClient.runBF("values20.txt", "weights20.txt");
                            break;
                        case "50":
                            ProjectTwoPartTwoClient.runBF("values50.txt", "weights50.txt");
                            break;
                        case "100":
                            ProjectTwoPartTwoClient.runBF("values100.txt", "weights100.txt");
                            break;
                            
                        default:
                            System.out.println("I don't understand that.");
                    }
                    break;
                case "gr":
                    ProjectTwoPartTwoClient.printHowManyMenu();
                    input = in.nextLine();
                    switch(input){
                        case "10":
                            ProjectTwoPartTwoClient.runGR("values10.txt", "weights10.txt");
                            break;
                        case "20":
                            ProjectTwoPartTwoClient.runGR("values20.txt", "weights20.txt");
                            break;
                        case "50":
                            ProjectTwoPartTwoClient.runGR("values50.txt", "weights50.txt");
                            break;
                        case "100":
                            ProjectTwoPartTwoClient.runGR("values100.txt", "weights100.txt");
                            break;
                            
                        default:
                            System.out.println("I don't understand that.");
                    }
                    break;
                    
                case "ga":
                    ProjectTwoPartTwoClient.printHowManyMenu();
                    input = in.nextLine();
                    switch(input){
                        case "10":
                            ProjectTwoPartTwoClient.runGA("values10.txt", "weights10.txt");
                            break;
                        case "20":
                            ProjectTwoPartTwoClient.runGA("values20.txt", "weights20.txt");
                            break;
                        case "50":
                            ProjectTwoPartTwoClient.runGA("values50.txt", "weights50.txt");
                            break;
                        case "100":
                            ProjectTwoPartTwoClient.runGA("values100.txt", "weights100.txt");
                            break;
                            
                        default:
                            System.out.println("I don't understand that.");
                    }
                    break;
                default: 
                    System.out.println("I don't recognize that command");
            }
            ProjectTwoPartTwoClient.printMenu();
            input = in.nextLine();
        }
    }
    
}
