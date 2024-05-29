package problem;

import org.junit.Assert;
import org.junit.Test;

public class P560SubArraySumEqualsK {
    @Test
    public void test() {
        int[] nums = new int[]{1, 2, 3};
        int result = subarraySum(nums, 3);
        Assert.assertEquals(2, result);
    }

    public int subarraySum(int[] nums, int k) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k) {
                    count++;
                }
            }
        }
        return count;
    }
}
