package problem;

import org.junit.Assert;
import org.junit.Test;

public class P152MaximumProductSubarray {
    @Test
    public void test() {
        Assert.assertEquals(2, maxProduct(new int[]{2}));
        Assert.assertEquals(0, maxProduct(new int[]{-2,0,-1}));
        Assert.assertEquals(6, maxProduct(new int[]{2,3,-2,4}));
    }

    /**
     * 动态规划
     * maxs[i]保存以i位置结尾的子数组的最大乘积值，mins[i]反之为最小
     */
    public int maxProduct(int[] nums) {
        int[] maxs = new int[nums.length];
        int[] mins = new int[nums.length];
        maxs[0] = mins[0] = nums[0];
        for (int i = 1; i < mins.length; i++) {
            int x = maxs[i - 1] * nums[i];
            int y = mins[i - 1] * nums[i];
            maxs[i] = Math.max(nums[i], Math.max(x, y));
            mins[i] = Math.min(nums[i], Math.min(x, y));
        }
        int max = maxs[0];
        for (int m : maxs) {
            max = Math.max(max, m);
        }
        return max;
    }
}
