import java.util.ArrayList;
import java.util.List;

public class MyDataStructure {
    private final IndexableSkipList skipList;
    private final ChainedHashTable<Integer, AbstractSkipList.Node> chainedHashTable;


    public MyDataStructure(int N) {
        skipList = new IndexableSkipList(0.5);
        ModularHash modularHash = new ModularHash();
        chainedHashTable = new ChainedHashTable<Integer,AbstractSkipList.Node>(modularHash, Integer.numberOfTrailingZeros(N), 1);
    }

    /*
     * In the following functions,
     * you should REMOVE the place-holder return statements.
     */
    public boolean insert(int value) {
        if (contains(value))
            return false;

        AbstractSkipList.Node node = skipList.insert(value);

        chainedHashTable.insert(value, node);
        return true;
    }

    public boolean delete(int value) {
        if (!contains(value))
            return false;

        skipList.delete((AbstractSkipList.Node) chainedHashTable.search(value));
        chainedHashTable.delete(value);

        return true;
    }

    public boolean contains(int value) {
        return chainedHashTable.search(value) != null;
    }

    public int rank(int value) {

        return skipList.rank(value);
    }

    public int select(int index) {

        return skipList.select(index);
    }

    public List<Integer> range(int low, int high) {
        if(!contains(low))
            return null;

        List<Integer> output = new ArrayList<>();

        AbstractSkipList.Node node = (AbstractSkipList.Node) chainedHashTable.search(low);
        while(node.key() <= high) {
            output.add(node.key());
            node = node.getNext(0);
        }

        return output;
    }
    public void print() {
        System.out.println(skipList);
        System.out.println();
        chainedHashTable.printHashTable();
    }
    public int size () {
        return skipList.size;
    }
}