import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome> {

    // Used for random number generation
    private static Random rng = new Random();

    // dummy taken from project 4 pdf
    public static long dummy = 0;

    // This no-arg constructor can be empty
    public Chromosome() {
    }

    // Adds a copy of each of the items passed in to this Chromosome. Uses random number to decide whether
    // each item's included field is set to true or false
    public Chromosome(ArrayList<Item> items) {

        Collections.shuffle(items);

        // For each item in items, add item to "this" and randomly set its included value to T or F
        for (Item item : items) {
            Item newItem = new Item(item); // creates a copy item
            newItem.setIncluded(rng.nextBoolean());
            this.add(item);

            // System.out.println(this); // print statement for testing
        }
    }

    // Creates and returns a new child chromosome by performing the crossover operation on this chromosome
    // and other one that is passed in (for each item, use a random number to decide which parent's item should
    // be copied and added to the child)
    public Chromosome crossover(Chromosome other) {

        int randNum = rng.nextInt(10); // random int between 0-10 (exclusive)
        Chromosome child = new Chromosome();

        // If the random num is between 0-5, it picks from parent 1, if its between 6-11 (exclusive)
        // it picks from parent2
        for (int i=0; i<this.size(); i++) {
            if (randNum > 0 && randNum <= 5) {
                child.add(new Item(this.get(i)));
            } else {
                child.add(new Item(other.get(i)));
            }
        }

        // returns the child created from crossover
        return child;
    }

    // Performs the mutation operation on this chromosome (for each item in this chromosome, use a random number
    // to decide whether to flip it's included field from true to false or vice versa)
    public void mutate() {
        for (Item s: this) {
            int randNum = rng.nextInt(10); // creates a random integer 0-10 (exclusive)

            // If the random number is 0, it sets its included value to whatever its current included value isn't
            if (randNum == 0) {
                s.setIncluded(!s.isIncluded());
            }
        }
    }

    // Returns the fitness of this chromosome. If the sum of all the included items' weights are greater than 10,
    // the fitness is 0. Otherwise, the fitness is equal to the sum of all the included items' values
    public int getFitness() {

        int value = 0;
        double weight = 0;
        dummy = 0;

        for (int i=0; i<this.size() * 1000; i++) {
            dummy += i;
        }


        // for every item in items, if its included - increase its total value and weight
        for (Item s: this) {
            if (s.isIncluded()) {
                value += s.getValue();
                weight += s.getWeight();
            }
        }

        // if the weight of all the items is over 10, 0 is returned
        // if the weight of all the items is under 10, the value is returned
        if(weight > 10) {
            return 0;
        } else {
            return value;
        }

    }

    // Returns -1 if this chromosome's fitness is greater than the other's fitness, +1 if this chromosome's fitness
    // is less than the other one's, and 0 if their fitness is the same
    public int compareTo(Chromosome other) {

        // Compares others fitness with this fitness
        return Integer.compare(other.getFitness(), this.getFitness());

    }

    // Over-written toString method that prints an items included T and F values
    public String toString() {

        String returnString = "";
        for (Item s: this) {
            if(!s.isIncluded()) {
                returnString += "F";
            } else {
                returnString += "T";
            }
        }

        return returnString;

    }

}
