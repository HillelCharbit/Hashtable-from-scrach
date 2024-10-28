import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;


abstract public class AbstractSkipList {
    final protected Node head;
    final protected Node tail;


    protected int size;


    public AbstractSkipList() {
        head = new Node(Integer.MIN_VALUE);
        tail = new Node(Integer.MAX_VALUE);
        increaseHeight();
        this.size = 0;
    }

    public void increaseHeight() {
        head.addLevel(tail, null);
        tail.addLevel(null, head);

        head.nodesSkip.add(size + 1);
        tail.nodesSkip.add(null);
    }

    abstract Node find(int key);

    abstract public int generateHeight();

    public Node search(int key) {
        Node curr = find(key);

        return curr.key() == key ? curr : null;
    }

    public Node insert(int key) {
        int nodeHeight = generateHeight();

        while (nodeHeight > head.height()) {
            increaseHeight();
        }

        Node prevNode = find(key);
        if (prevNode.key() == key) {
            return null;
        }

        Integer currRank = findRank(key);


        Node newNode = new Node(key);


        for (int level = 0; level <= nodeHeight && prevNode != null; ++level) {
            Node nextNode = prevNode.getNext(level);

            newNode.addLevel(nextNode, prevNode);
            prevNode.setNext(level, newNode);
            nextNode.setPrev(level, newNode);

            Integer prevOldSkip = prevNode.nodesSkip.get(level);
            Integer prevRank = findRank(prevNode.key());
            Integer prevNodeNewSkip = currRank - prevRank;
            prevNode.nodesSkip.set(level,prevNodeNewSkip);
            Integer currSkip = prevOldSkip + 1 - prevNode.nodesSkip.get(level);
            newNode.nodesSkip.add(currSkip);


            while (prevNode != null && prevNode.height() == level) {
                prevNode = prevNode.getPrev(level);
            }
        }

        int currLevel = nodeHeight;
        while (prevNode != null && currLevel < head.height() ) {
            while (currLevel < prevNode.height() ) {
                currLevel = currLevel + 1;
                Integer prevOldSkip = prevNode.nodesSkip.get(currLevel);
                prevNode.nodesSkip.set(currLevel, (prevOldSkip + 1));
            }
            prevNode = prevNode.getPrev(currLevel);
        }


        size = size + 1;
        return newNode;
    }


    public Integer findRank(int val) {
        Node curr = head;
        Integer currRank = 0;
        for (int i = head.height(); i >= 0; i--) {
            while (curr.getNext(i).key() <= val) {
                currRank = currRank + curr.nodesSkip.get(i);
                curr = curr.getNext(i);
            }
        }
        if (curr.key != val) {
            currRank = currRank + 1;
        }
        return currRank;
    }



    public boolean delete(Node node) {
        for (int level = 0; level <= node.height(); ++level) {
            Node prev = node.getPrev(level);
            Node next = node.getNext(level);
            prev.setNext(level, next);
            next.setPrev(level, prev);

            Integer deleteSkip = node.nodesSkip.get(level);
            Integer prevSkip = prev.nodesSkip.get(level);
            prev.nodesSkip.set(level, (deleteSkip + prevSkip -1));

        }

        int currLevel = node.height;
        Node prevNode = node.getPrev(currLevel);
        while (prevNode != null && currLevel < head.height() ) {
            while (currLevel < prevNode.height() ) {
                currLevel = currLevel + 1;
                Integer prevOldSkip = prevNode.nodesSkip.get(currLevel);
                prevNode.nodesSkip.set(currLevel, (prevOldSkip - 1));
            }
            prevNode = prevNode.getPrev(currLevel);
        }

        size = size - 1;
        return true;
    }

    public int predecessor(Node node) {
        return node.getPrev(0).key();
    }

    public int successor(Node node) {
        return node.getNext(0).key();
    }

    public int minimum() {
        if (head.getNext(0) == tail) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return head.getNext(0).key();
    }

    public int maximum() {
        if (tail.getPrev(0) == head) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return tail.getPrev(0).key();
    }

    private void levelToString(StringBuilder s, int level) {
        s.append("H    ");
        Node curr = head.getNext(level);

        while (curr != tail) {
            s.append(curr.key);
            s.append("    ");

            curr = curr.getNext(level);
        }

        s.append("T\n");
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int level = head.height(); level >= 0; --level) {
            levelToString(str, level);
        }

        return str.toString();
    }

    public static class Node {
        final private List<Node> next;
        final private List<Node> prev;
        public List<Integer> nodesSkip;
        private int height;
        final private int key;

        public Node(int key) {
            next = new ArrayList<>();
            prev = new ArrayList<>();
            ////////////////////////////////////////////////////////////////////
            nodesSkip = new ArrayList<>();
            ////////////////////////////////////////////////////////////////////
            this.height = -1;
            this.key = key;
        }

        public Node getPrev(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return prev.get(level);
        }

        public Node getNext(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return next.get(level);
        }

        public void setNext(int level, Node next) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.next.set(level, next);
        }

        public void setPrev(int level, Node prev) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.prev.set(level, prev);
        }

        public void addLevel(Node next, Node prev) {
            ++height;
            this.next.add(next);
            this.prev.add(prev);
        }

        public int height() { return height; }
        public int key() { return key; }
    }
}
