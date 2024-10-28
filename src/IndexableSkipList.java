public class IndexableSkipList extends AbstractSkipList {
    final protected double probability;
    public IndexableSkipList(double probability) {
        super();
        this.probability = probability;
    }

    @Override
    public Node find(int val) {
        Node curr = head;
        for (int i = head.height(); i >= 0; i--) {
            while (curr.getNext(i).key() <= val) {
                curr = curr.getNext(i);
            }
        }
        return curr;
    }

    @Override
    public int generateHeight() {

        int height = 0;
        double coinFlipResult = Math.random();
        while (coinFlipResult > probability ) {
            height = height + 1;
            coinFlipResult = Math.random();
        }
        return height;
    }

    public int rank(int val) {
        return findRank(val);
    }

    public int select(int index) {
        Node curr = head;
        int level = head.height();
        while (index > 0) {
            while (curr.nodesSkip.get(level) <= index) {
                int skip = curr.nodesSkip.get(level);
                curr = curr.getNext(level);
                index = index - skip;
                if (index == 0) {
                    return curr.key();
                }
            }
            level = level - 1;

        }
        return curr.key();

    }
}
