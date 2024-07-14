package problem;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class P123BestTimeToBuyAndSellStockIII {
    @Test
    public void test() {
        Solution1 solution = new Solution1();
        Assert.assertEquals(6, solution.maxProfit(new int[]{3,3,5,0,0,3,1,4}));
        Assert.assertEquals(4, solution.maxProfit(new int[]{1,2,3,4,5}));
        Assert.assertEquals(0, solution.maxProfit(new int[]{7,6,4,3,1}));
        Assert.assertEquals(13, solution.maxProfit(new int[]{1,2,4,2,5,7,2,4,9,0}));
    }

    private static class Solution1 {
        /**
         * 动态规划
         * 可能的状态有：未执行任何操作、买入过一次、买入并卖出过一次、买入两次卖出一次、买入卖出两次
         * 第一个状态为0，在第i天结束后，剩余的状态将其最大利润设为buy1[i]、sell1[i]、buy2[i]、sell2[i]，则有
         * buy1[i]=max(buy1[i-1],price[i])
         * sell1[i]=max(sell1[i-1],buy1[i]+price[i])
         * buy2[i]=max(buy2[i-1],sell1[i-1]-price[i])
         * sell2[i]=max(sell2[i-1],buy2[i-1]+price[i])
         */
        public int maxProfit(int[] prices) {
            return maxProfit(prices, 2);
        }
    
        private int maxProfit(int[] prices, int maxTimes) {
            int[] buy = new int[maxTimes], sell = new int[maxTimes];
            for (int j = 0; j < maxTimes; j++) {
                buy[j] = -prices[0];
                sell[j] = 0;
            }
            for (int i = 1; i < prices.length; i++) {
                for (int j = 0; j < maxTimes; j++) {
                    if (j > 0) {
                        buy[j] = Math.max(buy[j], sell[j - 1] - prices[i]);
                    } else {
                        buy[j] = Math.max(buy[j], -prices[i]);
                    }
                    sell[j] = Math.max(sell[j], buy[j] + prices[i]);
                }
            }
            return sell[maxTimes - 1];
        }
    
    }

    private static class Solution2 {
        public int maxProfit(int[] prices) {
            List<int[]> incrPriceList = incrPriceList(prices);
            // System.out.println(incrPriceList.stream().map(a -> Arrays.toString(a)).collect(Collectors.toList()));
            return maxProfit(incrPriceList);
        }

        /**
         * (lowPrice, highPrice)列表，降低计算空间
         * prices:[3,3,5,0,0,3,1,4]
         * output:[[3, 5], [0, 3], [1, 4], [4, 4]]
         */
        private List<int[]> incrPriceList(int[] prices) {
            final int n = prices.length;
            List<int[]> incrPriceList = new ArrayList<>();
            for (int i = 0; i < n;) {
                int j = i;
                while (j + 1 < n && prices[j + 1] >= prices[j]) {
                    j++;
                }
                incrPriceList.add(new int[]{prices[i], prices[j]});

                while (j + 1 < n && prices[j + 1] <= prices[j]) {
                    j++;
                }
                i = Math.max(i + 1, j);
            }
            return incrPriceList;
        }

        /**
         * 动态规划
         */
        private int maxProfit(List<int[]> incrPriceList) {
            final int len = incrPriceList.size();
            // rightMaxProfits[i]=max(rightMaxProfits[i+1],rightMaxPrices[i]-price[i])
            int[] maxPrices = new int[len];
            maxPrices[len - 1] = incrPriceList.get(len - 1)[1];
            for (int i = len - 2; i >= 0; i--) {
                maxPrices[i] = Math.max(maxPrices[i + 1], incrPriceList.get(i)[1]);
            }
            int[] rightMaxProfits = new int[len];
            rightMaxProfits[len - 1] = incrPriceList.get(len - 1)[1] - incrPriceList.get(len - 1)[0];
            for (int i = len - 2; i >= 0; i--) {
                int startPrice = incrPriceList.get(i)[0];
                rightMaxProfits[i] = Math.max(rightMaxProfits[i + 1], maxPrices[i] - startPrice);
            }
            
            // leftMaxProfits[i]=max(leftMaxProfits[i-1],leftMinPrices[i]-price[i])
            int[] minPrices = new int[len];
            minPrices[0] = incrPriceList.get(0)[0];
            for (int i = 1; i < len; i++) {
                minPrices[i] = Math.min(minPrices[i - 1], incrPriceList.get(i)[0]);
            }
            int[] leftMaxProfits = new int[len];
            for (int i = 0; i < len; i++) {
                int profit = incrPriceList.get(i)[1] - minPrices[i];
                leftMaxProfits[i] = Math.max(i == 0 ? 0 : leftMaxProfits[i - 1], profit);
            }

            int maxProfit = 0;
            for (int i = 0; i < len; i++) {
                int leftProfit = i == 0 ? 0 : leftMaxProfits[i - 1];
                int rightProfit = rightMaxProfits[i];
                maxProfit = Math.max(maxProfit, leftProfit + rightProfit);
            }
            return maxProfit;
        }
    }
}
