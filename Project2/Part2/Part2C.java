
package Project2.Part2;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 2: Part 2C - Knapsack solved via Genetic Algorithm
 * Daniel Kerr and Charles So
 * Date: 07/06/2014
 * File: Part2C.java
 */
public class Part2C implements Runnable{
   private static int MAX_POPULATION;
   private static double FITNESS_THRESHOLD; 
   private static int GENERATION_COUNT = 0;
   private PriorityQueue<Gene> population;
   private boolean isDone;
   private Gene mostFit;
   private Store store;
   
   /**
    * Constructor for the Genetic algorithm. Generates a random population, the size 
    * of which is based on the item pool size, generates a fitness threshold for
    * stopping, based on the max results of the bruteforce or greedy algorithm of the
    * same item pool size.
    * @param store 
    */
   public Part2C(Store store){
       this.store = store;
       setMaxPopulation(store);
       population = new PriorityQueue<Gene>(MAX_POPULATION);
       isDone = false;
       FITNESS_THRESHOLD = getStopCondition(store);
       System.out.println("This will run until the total value of the knap sack is over " + FITNESS_THRESHOLD);
   }
   
   /**
    * Getter for the population 
    * @return 
    */
   public PriorityQueue<Gene> getPopulation(){
       return population;
   }
   
   /**
    * Setter for the population
    * @param population 
    */
   public void setPopulation(PriorityQueue<Gene> population){
       this.population = population;
   }
   
   /**
    * Getter for the boolean that the run method uses to determine if it is time
    * to stop
    * @return 
    */
   public boolean getIsDone(){
       return isDone;
   }
   
   /**
    * Setter for the boolean that the run method uses to determine if it is time
    * to stop
    * @param endCondition 
    */
   public void setIsDone(boolean endCondition){
       this.isDone = endCondition;
   }
   
   /**
    * Getter for the current, most fit gene
    * @return 
    */
   public Gene getMostFit(){
       return mostFit;
   }
   
   /** 
    * Setter for the most fit gene
    * @param mostFit 
    */
   public void setMostFit(Gene mostFit){
       this.mostFit = mostFit;
   }
   
   /**
    * Resets the Generation counter between trials
    */
   public void resetGenerationCount(){
       GENERATION_COUNT = 0;
   }
   
   /**
    * Getter for the amount of generations for this GA run
    * @return 
    */
   public int getGenerationCount(){
       return GENERATION_COUNT;
   }
   
   /**
    * This is where the work is done. Done in a run method so it can run in its own thread
    * It starts by spawning an initial population, then it loops though the mating 
    * process, stopping when either the user interrupts, the fitness condition is met
    * or it has converged on the same answer, and is unlikely to generate a new one
    */
   @Override
   public void run() {
       long start = System.currentTimeMillis();
       spawnInitial();
       while(!isDone){
           Gene child = mate();
           addToPool(child);
           GENERATION_COUNT++;
           setMostFit(findFittest());
           if(mostFit.getCode().equals(population.peek().getCode())){
               System.out.println("Premature convergence...stopping.");
               isDone = true;
           }
           if(Thread.interrupted()){
               isDone = true;
           }
           if(mostFit.getTotalValue() >= FITNESS_THRESHOLD && mostFit.getFitness() > 0){
               isDone = true;
           }
           
       }
       long stop = System.currentTimeMillis();
       printStatistics(start, stop);
   }

   /**
    * randomly generates an initial pool of genes
    */
   private void spawnInitial() {
       System.out.println("Initializing initial Gene pool...");
       for(int i = 0; i < MAX_POPULATION; i++){
           Gene initialPool = new Gene(store);
           population.add(initialPool);
       }
       setMostFit(findFittest());
       
   }

   /**
    * Mates two genes, on is the best chosen from half the population, the other 
    * is randomly chosen from the other half. It was done this way to prevent 
    * premature convergence, which was happening a lot for the smaller item sets.
    * We settled on a 0.1% chance to mutate on any given gene bit
    * @return 
    */
   private Gene mate() {
       Iterator<Gene> iterator = population.iterator();
       
       ArrayList<Gene> fMate = new ArrayList<Gene>();
       while(iterator.hasNext()){
           fMate.add((Gene)iterator.next());
       }
       Collections.shuffle(fMate);
       Gene parentOne = fMate.get(0);
       
       Gene parentTwo = fMate.get(population.size()/2);
       
       for(int i = 1; i < (population.size()/2); i++){
           if(fMate.get(i).getFitness() > parentOne.getFitness()){
               parentOne = fMate.get(i);
           }
          
       }
       
       BitSet code = new BitSet(parentOne.getGeneSize());
       Random random = new Random();
       for(int i = 0; i < parentOne.getGeneSize(); i++){
           if(random.nextBoolean()){
               if(parentOne.getCode().get(i) == true){
                   code.set(i, true);
               }
               else{
                   code.set(i, false);
               }
               
           }
           else{
               if(parentTwo.getCode().get(i) == true){
                   code.set(i, true);
               }
               else{
                   code.set(i, false);
               }
               
           }
           if(random.nextDouble()*1000.000 <= 1.0){
               code.flip(i);
               
           }
       }
       return new Gene(code, store);
   }

   /**
    * Dumps the least fit gene (at the head of the Priority Queue) in favor of the 
    * child.
    * @param child 
    */
   private void addToPool(Gene child) {
       Gene evolutionaryDeadEnd = population.poll();
       population.add(child);
   }
    
   /**
    * Searches through the pool for the fittest member
    * @return 
    */
   private Gene findFittest(){
       Gene[] findFit = new Gene[population.size()];
       findFit = population.toArray(findFit);
       Gene fittest = findFit[0];
       for(int i = 1; i < findFit.length; i++){
           if(findFit[i].getFitness() > fittest.getFitness()){
               fittest = findFit[i];
           }
       }
       return fittest;
   }

   /**
    * Prints the results to the screen
    * @param start start time in seconds
    * @param stop stop time in seconds
    */
    private void printStatistics(long start, long stop) {
        System.out.println("The genetic algorithm ran for " + GENERATION_COUNT + " generations.");
        System.out.println("This took " + ((stop - start) / 1000.0) + " seconds to do.");
        System.out.println("The best combination is now:");
        System.out.println(store.toString());
        System.out.println(mostFit.toString());
        System.out.println("Press enter to continue...");
        resetGenerationCount();
        System.out.flush();
    }

    /**
     * Sets the stop condition based on the max (or near max) of the brute force
     * or greedy results. We chose 90% of the max to be the end condition. For 
     * possible item count/ max weights we didn't have data on, it computes it 
     * to stop at 80% of double the max weight (since the total value tended to be 
     * roughly around twice the max weight
     * @param store
     * @return 
     */
    private double getStopCondition(Store store) {
        if(store.getValues().length == 10){
            if(store.getMaxWeight() >= 86 && store.getMaxWeight() <=87){
                //90% of optimal
                return 177.56;
            }
            else if(store.getMaxWeight() >= 144 && store.getMaxWeight() <= 145){
                //90% of optimal
                return 298.881;
            }
            else if(store.getMaxWeight() >= 190 && store.getMaxWeight() <= 191){
                //90% of optimal
                return 321.147;
            }
            else{
                // 80% of twice the max weight 
                return (0.8 * (store.getMaxWeight() * 2) );
            }
        }
        else if(store.getValues().length == 20){
            if(store.getMaxWeight() >= 170 && store.getMaxWeight() <= 171){
                //90% of optimal
                return 423.846;
            }
            else if(store.getMaxWeight() >= 284 && store.getMaxWeight() <= 285){
                //90% of optimal
                return 518.076;
            }
            else if(store.getMaxWeight() >= 375 && store.getMaxWeight() <= 376){
                //90% of optimal
                return 588.096;
            }
            else{
                // 80% of twice the max weight
                return (0.8 * (store.getMaxWeight() * 2) );
            }
        }
        else if(store.getValues().length == 50){
            if(store.getMaxWeight() >= 396 && store.getMaxWeight() <= 397){
                //90% of greedy optimal
                return 934.398;
            }
            else if(store.getMaxWeight() >= 660 && store.getMaxWeight() <= 661){
                //90% of greedy optimal
                return 1234.332;
            }
            else if(store.getMaxWeight() >= 872 && store.getMaxWeight() <= 873){
                //90% of greedy optimal
                return 1415.961;
            }
            else{
                // 80% of twice the max weight
                return (0.8 * (store.getMaxWeight() * 2) );
            }
        }
        else{
            if(store.getMaxWeight() >= 757 && store.getMaxWeight() <= 758){
                //90% of greedy optimal
                return 1939.968;
            }
            else if(store.getMaxWeight() >= 1262 && store.getMaxWeight() <= 1263){
                //90% of greedy optimal
                return 2489.706;
            }
            else if(store.getMaxWeight() >= 1666 && store.getMaxWeight() <= 1667){
                //90% of greedy optimal
                return 2857.896;
            }
            else{
                // 80% of twice the max weight
                return (0.8 * (store.getMaxWeight() * 2) );
            }
        }
    }

    /**
     * At first we were having troubles with the population converging at too
     * low a number too quickly. Setting the population size based on item set size
     * reduced this problem (and seemingly eliminated it for the larger item set sizes)
     * @param store 
     */
    private void setMaxPopulation(Store store) {
        MAX_POPULATION = 20 * store.getValues().length;
            
        
    }
   
   

}
