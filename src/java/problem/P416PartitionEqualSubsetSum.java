package problem;

import org.junit.Assert;
import org.junit.Test;

public class P416PartitionEqualSubsetSum {
    @Test
    public void test() {
        // Assert.assertFalse(canPartition(new int[]{1}));
        Assert.assertFalse(canPartition(new int[]{1,5}));
        Assert.assertTrue(canPartition(new int[]{1,5,11,5}));
        Assert.assertFalse(canPartition(new int[]{1,2,3,5}));
    }

    /**
     * 动态规划
     * 相当于找到一个元素子集，满足和为总体元素和的一半
     * 设dp[i][j]为在[0..i]区间内选择的元素和是否为j
     * 则有dp[i+1][j]||=dp[i][j]，以及dp[i+1][k]=true（dp[i][j]=true且k=j+nums[i+1]）
     */
    public boolean canPartition(int[] nums) {
        if (nums.length == 1) {
            return false;
        }

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int target = sum >> 1;
        // 和为奇数，不可能能拆分出要求的子集
        if (sum != (target << 1)) {
            return false;
        }

        boolean[][] dp = new boolean[nums.length][target + 1];
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            dp[i][0] = true;
            if (num <= target) {
                dp[i][num] = true;
            }
            
            if (i == 0) {
                continue;
            }

            for (int j = 1; j <= target; j++) {
                if (dp[i - 1][j]) {
                    dp[i][j] = true;
                    if (j + num <= target) {
                        dp[i][j + num] = true;
                    }
                }
            }
        }
        return dp[nums.length - 1][target];
    }
}
