import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MultiThreadedMain {

    // NOTE: having any more than 4 threads produces diminishing returns because of the over-head it takes to create
    // the threads

    /* THREADS AND TIMES
     * 1 thread -> 24854 milliseconds, Fitness: 7600
     * 2 threads -> 13281 milliseconds, Fitness: 7600
     * 3 threads -> 9517 milliseconds, Fitness: 7600
     * 4 threads -> 7430 milliseconds, Fitness: 7600
     * 5 threads -> 6061 milliseconds, Fitness: 7600
     * 6 threads -> 5571 milliseconds, Fitness: 7600
     * 7 threads -> 5059 milliseconds, Fitness: 7600
     * 8 threads -> 4906 milliseconds, Fitness: 7600
     */

    // Controls the values of the key parameters
    public static final int POP_SIZE = 100;
    public static final int NUM_EPOCHS = 5000;
    public static final int NUM_THREADS = 4;
    public static ArrayList<ThreadClass> threads = new ArrayList<>();

    // New main method for the multi-threading
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        // calls the readData on the more_items.txt file
        ArrayList<Item> items = readData("more_items.txt");

        ArrayList<Chromosome> currentPop = initializePopulation(items, POP_SIZE);

        int epochsPerThread = NUM_EPOCHS / NUM_THREADS;
        int lower = 1;
        int upper = epochsPerThread;
        final int incrementer = epochsPerThread;

        // starts timer
        long start = System.currentTimeMillis();

        // Loops based off of how many threads there are then creates a new thread, increments its lower and
        // upper bound (ex. if epochs is 1000 with 10 threads, it will be split 1-100, 101-200, ... etc using
        // the lower and upper bounds which increment each loop), and starts the thread
        for (int i = 0; i < NUM_THREADS; i++) {
            // each thread is passed a new ArrayList which is a COPY of the currentPop
            threads.add(new ThreadClass(lower, upper, new ArrayList<>(currentPop), POP_SIZE));
            lower += incrementer;
            upper += incrementer;
            threads.get(i).start();
        }

        // loops through all the threads and joins each one back together
        for (Thread thread : threads) {
            thread.join();
        }

        // ends timer to calculate total time
        long end = System.currentTimeMillis();
        System.out.println(end - start + " milliseconds");

        // Sorts the threads based off of the compare method in ThreadClass and prints out the fittest individual
        Collections.sort(threads);
        System.out.println("The fittest individual was: " + threads.get(threads.size()-1).getBestChromosome().toString() +
                " with a fitness of " + threads.get(threads.size()-1).getBestChromosome().getFitness());
    }

    // readData method taken from original GeneticAlgorithm class (Project 1)
    public static ArrayList<Item> readData(String filename) throws FileNotFoundException {

        ArrayList<Item> items = new ArrayList<>(); // new arraylist to store items
        Scanner readIn = new Scanner(new File(filename));

        // while the file still has lines, read in the line and split it into the core pieces:
        // name, weight, and value
        while (readIn.hasNextLine()) {
            String line = readIn.nextLine();
            String[] splitString = line.split(", "); // splits the string after each comma
            items.add(new Item(splitString[0], Double.parseDouble(splitString[1]), Integer.parseInt(splitString[2])));
            // adds the items into the arrayList

        }

        // returns the arrayList of Items
        return items;
    }

    public static ArrayList<Chromosome> initializePopulation(ArrayList<Item> items, int populationSize) {

        ArrayList<Chromosome> initialPopulation = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            initialPopulation.add(new Chromosome(items));
        }

        return initialPopulation;
    }

}
