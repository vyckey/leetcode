package problem;

import org.junit.Assert;
import org.junit.Test;

public class P300LongestIncreasingSubsequence {
    @Test
    public void test() {
        Assert.assertEquals(5, lengthOfLIS(new int[]{11,12,13,0,5,-1,6,7,8}));
        Assert.assertEquals(4, lengthOfLIS(new int[]{0,1,0,3,2,3}));
        Assert.assertEquals(4, lengthOfLIS(new int[]{10,9,2,5,3,7,101,18}));
        Assert.assertEquals(1, lengthOfLIS(new int[]{7,7,7,7,7,7,7}));
    }

    /**
     * 动态规划
     * dp[i]表示以i位置结尾的最大长度，则有
     * dp[i]=max(dp[j])+1 (0<=j<i 且nums[j]<nums[i])
     */
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length + 1];
        dp[0] = 1;
        for (int i = 1; i <= nums.length; i++) {
            int num = (i == nums.length) ? Integer.MAX_VALUE : nums[i];
            int max = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (nums[j] < num) {
                    max = Math.max(max, dp[j]);
                }
            }
            dp[i] = max + 1;
        }
        return dp[nums.length] - 1;
    }

    /**
     * 贪心算法+二分查找
     */
    public int lengthOfLIS2(int[] nums) {
        // incr[i+1] 保存的是长度为i的最长递增子序列末尾元素的最小值
        int[] incr = new int[nums.length];
        incr[0] = nums[0];
        int len = 1;
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            if (num > incr[len - 1]) {
                incr[len++] = num;
                continue;
            }
            int l = 0, r = len;
            // 2 4 5  1_3
            while (l + 1 < r) {
                int mid = (l + r) >> 1;
                if (incr[mid] > num) {
                    r = mid;
                } else {
                    l = mid;
                }
            }
            incr[(num <= incr[l]) ? l : l + 1] = num;
        }
        return len;
    }
}
