import java.util.Random;

public class ModularHash implements HashFactory<Integer> {
    final private Random rand;
    final private HashingUtils hashingUtils;

    public ModularHash() {
        this.rand = new Random();
        this.hashingUtils = new HashingUtils();
    }

    @Override
    public HashFunctor<Integer> pickHash(int k) {
        return new Functor(k);
    }

    public class Functor implements HashFunctor<Integer> {
        final private int a;
        final private int b;
        final private long p;
        final private int m;

        public Functor(int k) {
            this.m = (int) Math.pow(2, k); // Calculate the value of 'm' as 2^k
            this.a = rand.nextInt(m); // Generate a random value for 'a' between 0 and m-1
            this.b = rand.nextInt(m); // Generate a random value for 'b' between 0 and m-1

            long suspect = hashingUtils.genLong((long)Integer.MAX_VALUE + 1, Long.MAX_VALUE); // Generate a prime number greater than Integer.MAX_VALUE

            while(suspect % 2 == 0 || !hashingUtils.runMillerRabinTest(suspect, 50))
                suspect = hashingUtils.genLong((long) Integer.MAX_VALUE + 1, Long.MAX_VALUE);

            this.p = suspect;
        }
        @Override
        public int hash(Integer key) {
            return (int) ((((long)a*key +b) % p) % m);
        }
        public int a() {
            return a;
        }

        public int b() {
            return b;
        }

        public long p() {
            return p;
        }

        public int m() {
            return m;
        }
    }
}