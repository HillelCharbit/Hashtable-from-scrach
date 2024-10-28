import java.util.Random;

public class StringHash implements HashFactory<String> {
    final private Random rand;
    final private HashingUtils hashingUtils;

    public StringHash() {
        this.rand = new Random();
        this.hashingUtils = new HashingUtils();
    }

    @Override
    public HashFunctor<String> pickHash(int k) {
        return new Functor(k);
    }

    public class Functor implements HashFunctor<String> {
        final private HashFunctor<Integer> carterWegmanHash;
        final private int c;
        final private int q;

        public Functor(int k){
            this.carterWegmanHash = new ModularHash().pickHash(k);

            int suspect = rand.nextInt((Integer.MAX_VALUE/2)+1,Integer.MAX_VALUE);

            while(!hashingUtils.runMillerRabinTest(suspect,50))
                suspect = rand.nextInt((Integer.MAX_VALUE/2)+1,Integer.MAX_VALUE);
            this.q = suspect;

            this.c = rand.nextInt(2, q);
        }
        @Override
        public int hash(String key) {
            long sum = 0;
            int charIndex = 0;
            int strLength = key.length();

            while(charIndex < key.length()) {
                sum += key.charAt(charIndex) * (hashingUtils.modPow(c, strLength - charIndex, q));
                charIndex++;
            }
            return carterWegmanHash.hash((int)(sum % (long)q));
        }

        public int c() {
            return c;
        }

        public int q() {
            return q;
        }

        public HashFunctor carterWegmanHash() {
            return carterWegmanHash;
        }
    }
}