import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class ProbingHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;

    private Pair[] hashArray;
    private int size;
    private final Pair DELETED_ENTRY = new Pair("deleted entry","deleted entry"); // Unique deleted value

    public ProbingHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ProbingHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.hashArray = new Pair[capacity];
        this.size = 0;
    }

    public V search(K key) {
        int hashVal = hashFunc.hash(key);
        while(hashArray[hashVal] != null) {
            if (hashArray[hashVal].first().equals(key))
                return (V) hashArray[hashVal].second();
            hashVal = (hashVal+1) % capacity;
        }
        return null;
    }
    public void insert(K key, V value) {
        if (((double) size + 1) / capacity >= maxLoadFactor)
            rehash();

        int hashVal = hashFunc.hash(key);
        Pair entry = new Pair(key, value);

        // Linearly probe until an empty slot is found
        while (hashArray[hashVal] != null && hashArray[hashVal] != DELETED_ENTRY)
            hashVal = (hashVal + 1) % capacity;

        if(hashArray[hashVal] == null)
            size++;

        hashArray[hashVal] = entry;
    }
    public boolean delete(K key) {
        int hashVal = hashFunc.hash(key);

        while(hashArray[hashVal] != null) {
            if (hashArray[hashVal].second().equals(key)) {
                hashArray[hashVal] = DELETED_ENTRY;
                return true;
            }
            hashVal = (hashVal + 1) % capacity;
        }
        return false;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
    private void rehash() {

        int newCapacity = capacity << 1;
        this.hashFunc = hashFactory.pickHash(Integer.numberOfTrailingZeros(newCapacity));

        Pair[] newHashArray = new Pair[newCapacity];

        for (int i = 0; i < capacity; i++)
            if (hashArray[i] != null && hashArray[i].first() != DELETED_ENTRY )
                insertRehash(newHashArray, (K) hashArray[i].first(), (V) hashArray[i].second());

        this.hashArray = newHashArray;
        this.capacity = newCapacity;

    }
    private void insertRehash(Pair[] newHashArray, K key, V value){
        int hashVal = hashFunc.hash(key);
        Pair entry = new Pair(key,value);

        while(newHashArray[hashVal] != null)
            hashVal = (hashVal+1) % capacity;

        newHashArray[hashVal] = entry;
    }
    public double getLoadFactor(){
        return (double)size/capacity;
    }

    public void printHashTable() {
        int index = 0;
        for (Pair o : hashArray) {
            if(o.equals(DELETED_ENTRY))
                System.out.println(index++ +" " + "Deleted");

            else if(o.first() == null)
                System.out.println(index++ + " " + "null, null");

            else
                System.out.println(index++ + " " +o.first().toString() + ", " + o.second().toString());
        }
    }
    public int size() { return size; }
}
