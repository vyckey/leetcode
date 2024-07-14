package problem;

import org.junit.Assert;
import org.junit.Test;

public class P55JumpGame {
    @Test
    public void test() {
        Assert.assertTrue(canJump(new int[]{2,3,1,1,4}));
        Assert.assertFalse(canJump(new int[]{3,2,1,0,4}));
    }

    /**
     * 贪心算法
     */
    public boolean canJump(int[] nums) {
        // 单独处理
        if (nums.length == 1) {
            return true;
        }

        int farthest = 0;
        for (int i = 1; i < nums.length; i++) {
            farthest = Math.max(farthest, i + nums[i - 1]);
            // 需要判断是否最远停留在原地
            if (farthest <= i) {
                return false;
            }
        }
        return farthest >= nums.length;
    }

    /**
     * 动态规划
     * f(i)表示从位置i是否能跳跃到最后一个下标位置，则有
     * f(i)=anyTrue f(j) 0<j-i<=step[i]
     */
    public boolean canJump2(int[] nums) {
        boolean[] dp = new boolean[nums.length];
        dp[nums.length - 1] = true;
        for (int i = nums.length - 2; i >= 0; i--) {
            int maxStep = nums[i];
            int j = 1;
            while (j <= maxStep && !dp[i + j]) {
                j++;
            }
            dp[i] = j <= maxStep && dp[i + j];
        }
        return dp[0];
    }
}
