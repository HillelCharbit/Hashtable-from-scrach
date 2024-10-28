import java.util.ArrayList;
import java.util.Collections;

public class SkipListExperimentUtils {
    public static double measureLevels(double p, int x) {

        IndexableSkipList skipList = new IndexableSkipList(p);
        double sum = 0, averageLevel = 0;

        for (int i = 0; i < x; i++)
            sum = sum + skipList.generateHeight();

        averageLevel = (sum / x ) + 1;

        return averageLevel;
    }

    /*
     * The experiment should be performed according to these steps:
     * 1. Create the empty Data-Structure.
     * 2. Generate a randomly ordered list (or array) of items to insert.
     *
     * 3. Save the start time of the experiment (notice that you should not
     *    include the previous steps in the time measurement of this experiment).
     * 4. Perform the insertions according to the list/array from item 2.
     * 5. Save the end time of the experiment.
     *
     * 6. Return the DS and the difference between the times from 3 and 5.
     */
    public static Pair<AbstractSkipList, Double> measureInsertions(double p, int size) {
        IndexableSkipList output = new IndexableSkipList(p);
        ArrayList <Integer> toInsert = new ArrayList <Integer> ();
        Integer i = 0;
        while (i <= 2 * size) {
            toInsert.add(i);
            i = i + 2;
        }
        Collections.shuffle(toInsert);

        double sum = 0;
        // experiment :
        while (toInsert.size() > 0) {
            Integer toAdd = toInsert.remove(toInsert.size() - 1);
            long startTime = System.nanoTime();
            output.insert(toAdd);
            long endTime = System.nanoTime();
            sum = sum + endTime - startTime;
        }
        sum = sum / (size + 1);


        Pair<AbstractSkipList, Double> outputPair = new Pair<AbstractSkipList, Double> (output, sum);
        return outputPair;

    }

    public static double measureSearch(AbstractSkipList skipList, int size) {

        ArrayList <Integer> toSearch = new ArrayList <Integer> ();
        Integer i = 0;
        while (i <= 2 * size) {
            toSearch.add(i);
            i = i + 1;
        }
        Collections.shuffle(toSearch);
        double sum = 0;

        // experiment :
        while (toSearch.size() > 0) {
            Integer search = toSearch.remove(toSearch.size() - 1);
            long startTime = java.lang.System.nanoTime();
            skipList.search(search);
            long endTime = java.lang.System.nanoTime();
            sum = sum + Math.abs(startTime - endTime);
        }
        sum = sum  / ((2 * size) + 1);
        return sum;
    }

    public static double measureDeletions(AbstractSkipList skipList, int size) {
        ArrayList <Integer> toDelete = new ArrayList <Integer> ();
        Integer i = 0;
        while (i <= 2 * size) {
            toDelete.add(i);
            i = i + 2;
        }
        Collections.shuffle(toDelete);
        double sum = 0;
        double deletions = 0;

        // experiment :
        while (toDelete.size() > 0) {
            Integer toDel = toDelete.remove(toDelete.size() - 1);
            AbstractSkipList.Node delete = skipList.search(toDel);
            if (delete != null) {
                long startTime = java.lang.System.nanoTime();
                skipList.delete(delete);
                long endTime = java.lang.System.nanoTime();
                sum = sum + Math.abs(startTime - endTime);
                deletions = deletions + 1;
            }
        }
        if (deletions != 0) {
            sum = sum / deletions;
        }
        return sum;
    }

    public static void main(String[] args) {

        System.out.println( "Section 2.2.2");
        int [] numOfTests = {10, 100, 1000, 10000};
        double [] prob = {0.33, 0.5, 0.75, 0.9};

        // p loop
        for (int i = 0; i < prob.length; i++ ) {
            double sum = 0;
            double p = prob[i];
            double averageLevel = 0 ;
            double averageDelta = 0;
            int col = 0;
            String [] headline = { "x", "L1", "L2", "L3", "L4", "L5", "Expected Level", "Average delta" };
            double[][] table = new double [4][8];
            int row = 0;
            double expectedLevel = 1/p  ;
            System.out.print( " for p =" + p + "\n");

            // x loop
            for (int j = 0; j < numOfTests.length; j++) {
                col = 0;
                sum = 0;
                int x = numOfTests[j];
                int runNumber = 1;
                table[row][col] = x ;
                col++ ;

                // run number
                while (runNumber <= 5) {
                    averageLevel = measureLevels(p, x);
                    table[row][col] = averageLevel;
                    sum = sum + Math.abs(averageLevel - expectedLevel);
                    col++;
                    runNumber++;
                }
                table[row][col] = expectedLevel;
                col++;
                averageDelta = sum / 5 ;
                table [row][col] = averageDelta;
                row ++;
            }


            System.out.println(String.join("\t", headline));
            for (int rows = 0; rows < 4; rows++) {
                for (int cols = 0; cols < 8; cols++) {
                    System.out.print(table[rows][cols] + "\t");

                }
                System.out.println("\t");
            }
            System.out.println("\t");
        }

        System.out.println("Section 2.2.3");
        double[][] table = new double [7][4];
        double [] pArray = {0.33, 0.5, 0.75, 0.9};
        int [] xArray = {1000, 2500, 5000, 10000, 15000, 20000, 50000};

        for (int i = 0; i < 4; i++ ) {
            double p = pArray[i];
            System.out.print( " for p =" + p + "\n");
            for (int row = 0; row < 7; row =  row + 1) {
                int col = 0;
                int x = xArray[row];
                table [row][col] = x;
                col++ ;

                // insertions
                int numOfTest = 1;
                double sumInsertions = 0;
                double sumSearch = 0;
                double sumDeletions = 0;
                while (numOfTest <= 30) {
                    Pair<AbstractSkipList, Double> test = measureInsertions(p,x);
                    sumInsertions = sumInsertions +  test.second();
                    AbstractSkipList experimentsSkipList = test.first();
                    numOfTest = numOfTest +1;
                    sumSearch = sumSearch + measureSearch(experimentsSkipList, x);
                    sumDeletions = sumDeletions + measureDeletions(experimentsSkipList, x);
                }

                // insertions
                double averageInsertion = sumInsertions / 30;
                table [row][col] = averageInsertion;
                col++;

                // search
                double averageSearch = sumSearch / 30;
                table [row][col] = averageSearch;
                col++;

                // deletions
                double averageDeletions = sumDeletions / 30;
                table [row][col] = averageDeletions;
            }
            String[] headline = {"x", "Average Insertion", "Average Search", "Average Deletion"};
            System.out.println(String.join("\t", headline));
            for (int rows = 0; rows < 7 ; rows++) {
                for (int cols = 0; cols < 4; cols++) {
                    System.out.print(table[rows][cols] + "\t");

                }
                System.out.println("\t");
            }
            System.out.println("\t");
        }


    }
}
