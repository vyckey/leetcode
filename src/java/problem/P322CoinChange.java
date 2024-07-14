package problem;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import util.ArrayUtil;
import util.RandomUtil;

public class P322CoinChange {
    @Test
    public void test() {
        SlowSolution solution1 = new SlowSolution();
        Assert.assertEquals(0, solution1.coinChange(new int[]{1}, 0));
        Assert.assertEquals(-1, solution1.coinChange(new int[]{2}, 3));
        Assert.assertEquals(3, solution1.coinChange(new int[]{1,2,5}, 11));
        Assert.assertEquals(20, solution1.coinChange(new int[]{186,419,83,408}, 6249));

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
         * 动态规划
         * 缓存minCoins[amount]
         */
        public int coinChange(int[] coins, int amount) {
            Map<Integer, Integer> minCoins = new HashMap<>();
            minCoins.put(0, 0);
            return minCoinChange(coins, minCoins, amount);
        }

        private int minCoinChange(int[] coins, Map<Integer, Integer> minCoins, int amount) {
            if (minCoins.containsKey(amount)) {
                return minCoins.get(amount);
            }
            int min = -1;
            for (int c : coins) {
                if (amount - c < 0) {
                    continue;
                }
                int res = minCoinChange(coins, minCoins, amount - c);
                if (res >= 0) {
                    ++res;
                    min = (min < 0) ? res : Math.min(min, res);
                }
            }
            minCoins.put(amount, min);
            return min;
        }
    }
}
