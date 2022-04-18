import java.util.ArrayList;
import java.util.Collections;

public class ThreadClass extends Thread implements Comparable<ThreadClass> {

    private int lower;
    private int upper;
    private int POP_SIZE;
    private ArrayList<Chromosome> currentPop;

    public ThreadClass(int lower, int upper, ArrayList<Chromosome> currentPop, int POP_SIZE) {
        this.lower = lower;
        this.upper = upper;
        this.POP_SIZE = POP_SIZE;
        this.currentPop = currentPop;
    }

    public void run() {

        // Method taken mostly from GeneticAlgorithm.java
        for (int i = lower; i < upper; i++) { // loops according to amount between lower and upper bounds

            // Creates a new arrayList of chromosome for next generation and fills it in with current population
            ArrayList<Chromosome> nextGen = new ArrayList<>(currentPop);

            // randomly pair off parents and do .crossover method to create a child and add the child to nextGen
            Collections.shuffle(currentPop);
            for (int j = 1; j < POP_SIZE / 2; j++) {
                Chromosome randomParent1;
                Chromosome randomParent2;

                randomParent1 = currentPop.get((j * 2) - 1);
                randomParent2 = currentPop.get((j * 2));

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
            for (int j = 0; j < POP_SIZE; j++) {
                currentPop.add(nextGen.get(j));
            }
        }

    }

    // Returns the best chromosome after sorting
    public Chromosome getBestChromosome() {
        Collections.sort(currentPop);
        return currentPop.get(0);
    }

    // Custom compareTo method to sort the arrayList according to fitness
    @Override
    public int compareTo(ThreadClass o) {
        return Integer.compare(this.getBestChromosome().getFitness(), o.getBestChromosome().getFitness());
    }

}
