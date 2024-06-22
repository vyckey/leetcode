package problem;

import org.junit.Test;

import util.ArrayUtil;
import util.RandomUtil;

public class P322CoinChange {
    @Test
    public void test() {
        SlowSolution solution1 = new SlowSolution();
        for (int i = 0; i < 100; i++) {
            int amount = RandomUtil.nextInt(1, 10000);
            int size = RandomUtil.nextInt(1, 13);
            int[] coins = ArrayUtil.randomIntArray(size, 1, 2000);
            int count1 = solution1.coinChange(coins, amount);
            System.out.println("cost " + count1 + " coins for amount " + amount);
        }
    }
    

    private static class SlowSolution {
        /**
         * O(n^2)时间复杂度的解决方案
         */
        public int coinChange(int[] coins, int amount) {
            return minCoinChange(coins, amount, 0);
        }
    
        private int minCoinChange(int[] coins, int amount, int start) {
            if (amount == 0) {
                return 0;
            }
            while (start < coins.length && coins[start] > amount) {
                start++;
            }
            if (start >= coins.length) {
                return -1;
            }
    
            int minCoins = -1;
            int coin = coins[start], maxCoins = amount / coin;
            for (int c = maxCoins; c >= 0; c--) {
                int ret = minCoinChange(coins, amount - c * coin, start + 1);
                if (ret >= 0) {
                    int count = c + ret;
                    minCoins = minCoins < 0 ? count : Math.min(minCoins, count);
                }
            }
            return minCoins;
        }
    }
    
}
