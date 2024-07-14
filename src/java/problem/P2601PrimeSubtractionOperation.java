package problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class P2601PrimeSubtractionOperation {
    @Test
    public void test() {
        Set<Integer> set;
        set.remove
    }

    private static class Solution1 {
        private static final List<Integer> PRIMES = new ArrayList<>();

        static {
            PRIMES.add(2);
            for (int i = 3; i <= 1000; i += 2) {
                boolean isPrime = true;
                for (int j = 2; j <= Math.sqrt(i); j++) {
                    if ((i % j) == 0) {
                        isPrime = false;
                        break;
                    }
                }
                if (isPrime) {
                    PRIMES.add(i);
                }
            }
            System.out.println(PRIMES);
        }

        /**
         * 回溯
         */
        public boolean primeSubOperation(int[] nums) {
            if (nums.length <= 1) {
                return true;
            }
            return primeSubOperation(nums, nums.length - 2);
        }

        private boolean primeSubOperation(int[] nums, int i) {
            if (i < 0) {
                return true;
            }

            int val = nums[i];
            if (val < nums[i + 1]) {
                return primeSubOperation(nums, i - 1);
            }
            for (int prime : PRIMES) {
                if (prime >= val) {
                    break;
                }
                if (val - prime >= nums[i + 1]) {
                    continue;
                }
                nums[i] = val - prime;
                if (primeSubOperation(nums, i - 1)) {
                    return true;
                }
            }
            return false;
        }
    }
}
