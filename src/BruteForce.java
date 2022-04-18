import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BruteForce {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Item> items = readData("items.txt");

        System.out.println("Best subset: " + getOptimalSet(items));
        System.out.println("Fitness: " + getFitness(getOptimalSet(items)));
    }

    public static ArrayList<Item> getOptimalSet(ArrayList<Item> items) {

        ArrayList<Item> copyItems = new ArrayList<>(items);
        ArrayList<Item> bestSubset = new ArrayList<>(copyItems);

        // Throws IllegalArgumentException if the size of the list is greater than 10
        if (items.size() > 10) {
            throw new IllegalArgumentException();
        }

        // Base case
        if (items.size() <= 1) {
            return bestSubset;
        }

        int bestFitness = getFitness(bestSubset);

        for (int i=0; i<items.size(); i++) {
            ArrayList<Item> copyCopyItems = new ArrayList<>(copyItems); // creates a 2nd copy of the copy of items ArrayList
            copyCopyItems.remove(i); // removes the ith item for each iteration

            // Recursive step
            ArrayList<Item> subset = getOptimalSet(copyCopyItems);

            int currentFitness = getFitness(subset);

            // if the currentFitness is greater than the bestFitness, it updates the bestFitness to the value
            // of currentFitness
            if (currentFitness > bestFitness) {
                bestFitness = currentFitness;
                bestSubset = subset;
            }

        }

        // returns the bestSubset of the power-set of all combinations
        return bestSubset;
    }

    // getFitness method taken mostly from original Chromosome class
    public static int getFitness(ArrayList<Item> items) {
        int value = 0;
        double weight = 0;

        // for every item in items, increase its total value and weight
        for (Item item: items) {
            value += item.getValue();
            weight += item.getWeight();
        }

        // if the weight of all the items is over 10, 0 is returned
        // if the weight of all the items is under 10, the value is returned
        if(weight > 10) {
            return 0;
        } else {
            return value;
        }

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
        //System.out.println(items); // print statement for testing

        // returns the arrayList of Items
        return items;
    }
}
