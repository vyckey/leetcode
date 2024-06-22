package util;

import java.util.Random;

public class RandomUtil {
    private static final Random RANDOM = new Random();

    private RandomUtil() {
    }

    public static int nextInt(int startInclusive, int endExclusive) {
        if (endExclusive < startInclusive) {
            throw new IllegalArgumentException("Start value must be smaller or equal to end value.");
        }
        if (endExclusive > 0 && endExclusive - startInclusive < 0) {
            throw new IllegalArgumentException("Illegal range value.");
        }
        return startInclusive == endExclusive ? startInclusive : 
            startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
    }

    public static int nextInt() {
        return nextInt(0, Integer.MAX_VALUE);
    }

    public static long nextLong() {
        return nextLong(Long.MAX_VALUE);
    }

    private static long nextLong(long n) {
        long bits;
        long val;
        do {
            bits = RANDOM.nextLong() >>> 1;
            val = bits % n;
        } while(bits - val + (n - 1L) < 0L);

        return val;
    }

    public static boolean nextBoolean() {
        return RANDOM.nextInt(1) == 1;
    }
}
