import java.util.Iterator;
import java.util.LinkedList;


public class ChainedHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 2;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private LinkedList<Pair>[] hashArray;
    private int size;

    public ChainedHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ChainedHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.size = 0;
        this.hashArray = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++)
            hashArray[i] = new LinkedList<>();
    }

    public V search(K key) {
        int hashVal = hashFunc.hash(key);
        LinkedList<Pair> list = hashArray[hashVal];

        for(Pair entry: list)
            if(entry.first().equals(key))
                return (V) entry.second();

        return null;
    }

    public void insert(K key, V value) {
        if(((double)size + 1)/capacity >= maxLoadFactor)
            rehash();
        int hashVal = hashFunc.hash(key);
        Pair entry = new Pair(key, value);

        hashArray[hashVal].addFirst(entry);
        size++;
    }

    public boolean delete(K key){
        int hashVal = hashFunc.hash(key);
        LinkedList<Pair> list = hashArray[hashVal];
        Iterator<Pair> iter = list.iterator();

        while(iter.hasNext()) {
            Pair entry = iter.next();
            if (entry.first().equals(key)) {
                iter.remove();
                size--;
                return true;
            }
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

        LinkedList<Pair>[] newHashArray = new LinkedList[newCapacity];
        for (int i = 0; i < newCapacity; i++)
            newHashArray[i] = new LinkedList<>();


        for (int i = 0; i < capacity; i++) {
            Iterator<Pair> iter = hashArray[i].iterator();

            while (iter.hasNext()) {
                Pair entry = iter.next();
                insertRehash(newHashArray, (K) entry.first(), (V) entry.second());
            }
        }
            this.hashArray = newHashArray;
            this.capacity = newCapacity;

    }
    private void insertRehash(LinkedList<Pair>[] newHashArray, K key, V value){
        Pair entry = new Pair(key, value);
        newHashArray[hashFunc.hash(key)].addFirst(entry);
    }
    public double getLoadFactor(){
        return (double)size/capacity;
    }

    public void printHashTable() {
        int index = 0;
        for (LinkedList l : hashArray) {

            if(l.size() == 0)
                System.out.println(index++ + " " + "empty");

            else {
                System.out.print(index++);
                for (Pair entry : (LinkedList<Pair>) l) {
                    System.out.print(" -> " + entry.first() + " " + entry.second());
                }
                System.out.println();
            }
        }
    }
    public int size() { return size; }
}