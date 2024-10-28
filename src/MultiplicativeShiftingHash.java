import java.util.Random;

public class MultiplicativeShiftingHash implements HashFactory<Long> {
    final private Random rand;
    public MultiplicativeShiftingHash() {
        this.rand = new Random();
    }

    @Override
    public HashFunctor<Long> pickHash(int k) {
        return new Functor(k);
    }

    public class Functor implements HashFunctor<Long> {
        final public static long WORD_SIZE = 64;
        final private long a;
        final private long k;

        public Functor(int k) {
            // Generate random value for 'a' between 2 and Integer.MAX_VALUE
            this.a = rand.nextInt(2, Integer.MAX_VALUE);
            this.k = k;
        }
        public int hash(Long key) {
            return (int) (a*key) >>> (WORD_SIZE-k);
        }

        public long a() {
            return a;
        }

        public long k() {
            return k;
        }
    }
}