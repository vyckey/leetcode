package problem;

import org.junit.Assert;
import org.junit.Test;

public class P53MaximumSubarray {
    @Test
    public void test() {
        Assert.assertEquals(1, maxSubArray(new int[]{1}));
        Assert.assertEquals(23, maxSubArray(new int[]{5,4,-1,7,8}));
        Assert.assertEquals(6, maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
    }

    /**
     * 动态规划
     * 设maxPrefixSum(i)表示以i结尾的最大前缀和，则有
     * maxPrefixSum(i)=nums[i] if maxPrefixSum(i-1)<0 else maxPrefixSum(i-1)+nums[i]
     */
    public int maxSubArray(int[] nums) {
        int maxSum = nums[0], maxPrefixSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (maxPrefixSum >= 0) {
                maxPrefixSum += nums[i];
            } else {
                maxPrefixSum = nums[i];
            }
            maxSum = Math.max(maxSum, maxPrefixSum);
        }
        return maxSum;
    }
}
