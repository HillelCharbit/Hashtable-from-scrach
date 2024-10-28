//import java.util.Collections; // can be useful
import java.util.Iterator;
//import java.util.LinkedList;
import java.util.List;

public class HashingExperimentUtils {
    final private static int k = 16;
    public static Pair<Double, Double> measureOperationsChained(double maxLoadFactor) {
        HashFactory<Integer> modHash = new ModularHash();
        HashingUtils hashingUtils = new HashingUtils();

        Integer[] testArray = hashingUtils.genUniqueIntegers((int)(3*((1 << k) * maxLoadFactor)/2));

        long costPerInsertSum = 0, costPerSearchSum = 0;
        for(int j = 0; j < 30; j++){
            ChainedHashTable<Integer,Integer> chainedHashTable = new ChainedHashTable<>(modHash, k, maxLoadFactor);
            long sum1 = 0, sum2 = 0;

            long start1 = System.nanoTime();
            for(int i = 0; i < 2*testArray.length/3; i++) {
                chainedHashTable.insert(testArray[i], i);
//                if (chainedHashTable.size() == 2*testArray.length/3-3)
//                    System.out.println();
            }
            long end1 = System.nanoTime();

            long start2 = System.nanoTime();
            for(int i = testArray.length/3; i < testArray.length ; i++)
                chainedHashTable.search(testArray[i]);
            long end2 = System.nanoTime();

            sum1 += (end1 - start1)/(2L*testArray.length/3);
            sum2 += (end2 - start2)/(2L*testArray.length/3);
            costPerInsertSum += sum1;
            costPerSearchSum += sum2;
        }

        return new Pair<>((double) costPerInsertSum/30,(double) costPerSearchSum/30);
    }

    public static Pair<Double, Double> measureOperationsProbing(double maxLoadFactor) {
        HashFactory<Integer> modHash = new ModularHash();
        HashingUtils hashingUtils = new HashingUtils();

        Integer[] testArray = hashingUtils.genUniqueIntegers((int)(3*((1 << k) * maxLoadFactor)/2));
        long costPerInsertSum = 0, costPerSearchSum = 0;

        for(int j = 0; j < 30; j++){
            long sum1 = 0, sum2 = 0;
            ProbingHashTable<Integer,Integer> probingHashTable = new ProbingHashTable<Integer,Integer>(modHash, k, maxLoadFactor);

            long start1 = System.nanoTime();
            for(int i = 0; i < (2*testArray.length/3)-1; i++) {
                probingHashTable.insert(testArray[i], i);
//                if (probingHashTable.size() == 2 * testArray.length / 3 - 3)
//                    System.out.println();
            }
            long end1 = System.nanoTime();

            long start2 = System.nanoTime();
            for(int i = testArray.length/3; i < testArray.length-2 ; i++)
                probingHashTable.search(testArray[i]);
            long end2 = System.nanoTime();

            sum1 += (end1 - start1)/(2L*testArray.length/3);
            sum2 += (end2 - start2)/(2L*testArray.length/3);
            costPerInsertSum += sum1;
            costPerSearchSum += sum2;
        }
        return new Pair<>((double) costPerInsertSum/30,(double) costPerSearchSum/30);
    }


    public static Pair<Double, Double> measureLongOperations() {

        HashFactory<Long> multHash = new MultiplicativeShiftingHash();
        HashingUtils hashingUtils = new HashingUtils();

        Long[] testArray = hashingUtils.genUniqueLong((3*(1 << k)/2));

        long costPerInsertSum = 0, costPerSearchSum = 0;

        for(int j = 0; j < 10; j++){
            ChainedHashTable<Long,Integer> chainedHashTable = new ChainedHashTable<>(multHash, k, 1);
            long sum1 = 0, sum2 = 0;

            long start1 = System.nanoTime();
            for(int i = 0; i < (2*testArray.length/3); i++) {
                chainedHashTable.insert(testArray[i], i);
//                if(chainedHashTable.size() ==  (2*testArray.length/3)-3 )
//                    System.out.println();
            }
            long end1 = System.nanoTime();

            long start2 = System.nanoTime();
            for(int i = testArray.length/3; i < (testArray.length); i++)
                chainedHashTable.search(testArray[i]);

            long end2 = System.nanoTime();

            sum1 += (end1 - start1)/(2L*testArray.length/3);
            sum2 += (end2 - start2)/(2L*testArray.length/3);
            costPerInsertSum += sum1;
            costPerSearchSum += sum2;
        }

        return new Pair<>((double) costPerInsertSum/10,(double) costPerSearchSum/10);
    }

    public static Pair<Double, Double> measureStringOperations() {

        HashFactory<String> strHash = new StringHash();
        HashingUtils hashingUtils = new HashingUtils();

        List<String> testList = hashingUtils.genUniqueStrings((3*(1 << k)/2), 10, 20);
        ChainedHashTable<String,Integer> chainedHashTable = new ChainedHashTable<>(strHash, k, 1);
        Iterator<String> iterator = testList.iterator();

        long costPerInsertSum = 0, costPerSearchSum = 0;

        for(int j = 0; j < 10; j++){
            long sum1 = 0, sum2 = 0;
            int counter = 0;
            long start1 = System.nanoTime();
            while(iterator.hasNext() && counter < 2*testList.size()/3) {
                String s = iterator.next();
                chainedHashTable.insert(s, counter);
//                if (chainedHashTable.size() == 2*testList.length/3-3)
//                    System.out.println();
                counter++;
            }
            long end1 = System.nanoTime();

            deleteThirdOfInsertedElements(testList);
            iterator = testList.iterator();

            long start2 = System.nanoTime();
            while(iterator.hasNext()) {
                String s = iterator.next();
                chainedHashTable.search(s);
            }
            long end2 = System.nanoTime();

            sum1 += (end1 - start1)/(2L*testList.size()/3);
            sum2 += (end2 - start2)/(2L*testList.size()/3);
            costPerInsertSum += sum1;
            costPerSearchSum += sum2;

        }

        return new Pair<>((double) costPerInsertSum/10,(double) costPerSearchSum/10);

    }
    private static void deleteThirdOfInsertedElements(List<String> testList) {
        Iterator<String> iterator = testList.iterator();
        int counter = 0;
        while(iterator.hasNext() && counter < testList.size()/3) {
            iterator.next();
            iterator.remove();
            counter++;
        }
    }

    public static void main(String[] args) {
        double[] loadFactorValues = {0.5, 0.75, 0.875, 0.9375};
        for (double loadFactor : loadFactorValues) {
            Pair<Double, Double> probingHashTableTimes = measureOperationsProbing(loadFactor);
            System.out.println(probingHashTableTimes.first() + " " + probingHashTableTimes.second());
        }

        System.out.println();

        double[] loadFactorValues2 = {0.5, 0.75, 1, 1.5, 2};
        for (double loadFactor : loadFactorValues2) {
            Pair<Double, Double> chainedHashTableTimes = measureOperationsChained(loadFactor);
            System.out.println(chainedHashTableTimes.first() + " " + chainedHashTableTimes.second());
        }


        Pair<Double, Double> longHashTableTimes = measureLongOperations();
        System.out.println("Long hash table: " + longHashTableTimes.first() + " nano-seconds, " + longHashTableTimes.second() + " nano-seconds");

        Pair<Double, Double> stringHashTableTimes = measureStringOperations();
        System.out.println("String hash table: " + stringHashTableTimes.first() + " nano-seconds, " + stringHashTableTimes.second() + " nano-seconds");
    }


}
