import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GeneticAlgorithm {

    // Reads in data file with the format (on rubric) and creates and returns an ArrayList of Item objects
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

    // Creates and returns an ArrayList of populationSize Chromosome objects that each contain the items, with
    // their included field randomly set to true
    public static ArrayList<Chromosome> initializePopulation(ArrayList<Item> items, int populationSize) {

        ArrayList<Chromosome> initialPopulation = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            initialPopulation.add(new Chromosome(items));
        }

        return initialPopulation;
    }

    // Reads the data about the items in from a file called items.txt and performs the steps described in the
    // "Running the Genetic Algorithm" section on .pdf
    public static void main(String[] args) throws FileNotFoundException {
        // Starts timer
        long start = System.currentTimeMillis();

        // calls the readData on the more_items.txt file
        ArrayList<Item> items = readData("more_items.txt");

        int populationSize = 100; // initializes the population size
        ArrayList<Chromosome> currentPop = initializePopulation(items, populationSize);

        for (int i = 0; i < 5000; i++) { // loops according to amount of epochs

            // Creates a new arrayList of chromosome for next generation and fills it in with current population
            ArrayList<Chromosome> nextGen = new ArrayList<>(currentPop);

            // randomly pair off parents and do .crossover method to create a child and add the child to nextGen
            Collections.shuffle(currentPop);
            for (int j=1; j<populationSize/2; j++) {
                Chromosome randomParent1;
                Chromosome randomParent2;

                randomParent1 = currentPop.get((j*2)-1);
                randomParent2 = currentPop.get((j*2));

                nextGen.add(randomParent1.crossover(randomParent2));
            }

            // randomly choose 10% of population IN NEXT GEN to expose to mutation
            int tenPercent = (int) (nextGen.size() * .10);

            Collections.shuffle(nextGen); // shuffles list before picking 10% for random factor
            for (int j = 0; j < tenPercent; j++) {
                nextGen.get(j).mutate();
            }

            // Sorts the nextGen according to fitness and clears the current population
            Collections.sort(nextGen);
            currentPop.clear();


            // Add top (populationSize) amount of people from next gen back to currentGen to keep
            // population size consistent
            for (int j = 0; j < populationSize; j++) {
                currentPop.add(nextGen.get(j));
            }

            //System.out.println(currentPop); // print statement for testing
        } // loop to iterate over steps 2-6 in the rubric 20 times

        // Ends timer
        long end = System.currentTimeMillis();
        System.out.println(end - start + " milliseconds");

        // Sorts the current population by fitness and displays the fittest individual to the console
        Collections.sort(currentPop);
        System.out.print("The fittest individual was " + currentPop.get(0) + " ");
        System.out.println("with a fitness of " + currentPop.get(0).getFitness());

    }

}
