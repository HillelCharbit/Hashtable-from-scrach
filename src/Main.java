public class Main {
    public static void main(String[] args) {
        // ModularHash for Integer keys and MultiplicativeShiftingHash for Long keys
        HashFactory<Integer> modularHashFactory = new ModularHash();
        HashFactory<Long> multiplicativeHashFactory = new MultiplicativeShiftingHash();

        // Initialize ChainedHashTable with ModularHash
        System.out.println("Testing ChainedHashTable with ModularHash:");
        ChainedHashTable<Integer, String> chainedHashTable = new ChainedHashTable<>(modularHashFactory);
        testHashTable(chainedHashTable);

        // Initialize ProbingHashTable with ModularHash
        System.out.println("\nTesting ProbingHashTable with ModularHash:");
        ProbingHashTable<Integer, String> probingHashTable = new ProbingHashTable<>(modularHashFactory);
        testHashTable(probingHashTable);

        // Initialize ChainedHashTable with MultiplicativeShiftingHash
        System.out.println("\nTesting ChainedHashTable with MultiplicativeShiftingHash:");
        ChainedHashTable<Long, String> chainedHashTableLong = new ChainedHashTable<>(multiplicativeHashFactory);
        testHashTableLong(chainedHashTableLong);

        // Initialize ProbingHashTable with MultiplicativeShiftingHash
        System.out.println("\nTesting ProbingHashTable with MultiplicativeShiftingHash:");
        ProbingHashTable<Long, String> probingHashTableLong = new ProbingHashTable<>(multiplicativeHashFactory);
        testHashTableLong(probingHashTableLong);
    }

    private static void testHashTable(HashTable<Integer, String> hashTable) {
        // Inserting key-value pairs
        System.out.println("Inserting key-value pairs:");
        for (int i = 0; i < 5; i++) {
            hashTable.insert(i, "Value" + i);
            System.out.println("Inserted (" + i + ", Value" + i + ")");
        }

        // Searching for existing and non-existing keys
        System.out.println("\nSearching for keys:");
        for (int i = 0; i < 6; i++) {
            String result = hashTable.search(i);
            System.out.println("Search key " + i + ": " + (result != null ? result : "Not found"));
        }

        // Deleting a key
        System.out.println("\nDeleting key 2:");
        hashTable.delete(2);
        System.out.println("After deletion, search key 2: " + (hashTable.search(2) != null ? hashTable.search(2) : "Not found"));

        // Printing the hash table structure
        System.out.println("\nHashTable structure:");
    }

    private static void testHashTableLong(HashTable<Long, String> hashTable) {
        // Inserting key-value pairs
        System.out.println("Inserting key-value pairs:");
        for (long i = 0; i < 5; i++) {
            hashTable.insert(i, "Value" + i);
            System.out.println("Inserted (" + i + ", Value" + i + ")");
        }

        // Searching for existing and non-existing keys
        System.out.println("\nSearching for keys:");
        for (long i = 0; i < 6; i++) {
            String result = hashTable.search(i);
            System.out.println("Search key " + i + ": " + (result != null ? result : "Not found"));
        }

        // Deleting a key
        System.out.println("\nDeleting key 2:");
        hashTable.delete(2L);
        System.out.println("After deletion, search key 2: " + (hashTable.search(2L) != null ? hashTable.search(2L) : "Not found"));

        // Printing the hash table structure
        System.out.println("\nHashTable structure:");
    }
}
