package problem;

import org.junit.Assert;
import org.junit.Test;

public class P413ArithmeticSclices {
    @Test
    public void test() {
        Assert.assertEquals(0, numberOfArithmeticSlices(new int[]{1}));
        Assert.assertEquals(0, numberOfArithmeticSlices(new int[]{1,2}));
        Assert.assertEquals(0, numberOfArithmeticSlices(new int[]{1,2,4}));
        Assert.assertEquals(3, numberOfArithmeticSlices(new int[]{1,2,3,4}));
        Assert.assertEquals(1, numberOfArithmeticSlices(new int[]{1,0,0,0}));
        Assert.assertEquals(12, numberOfArithmeticSlices(new int[]{1,3,5,7,9,14,15,16,17,18}));
    }

    /**
     * 贪心算法
     * 先找到最长的等差数列，再统计子数组的个数
     */
    public int numberOfArithmeticSlices(int[] nums) {
        if (nums.length < 3) return 0;

        int count = 0;
        int d = nums[1] - nums[0], len = 2;
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] == d) {
                len++;
            } else {
                if (len >= 3) {
                    count += count(len);
                }
                d = nums[i] - nums[i - 1];
                len = 2;
            }
        }
        if (len >= 3) {
            count += count(len);
        }
        return count;
    }

    /**
     * count=\sum_{k=3}^{k=n}(n-k+1)
     */
    private int count(int n) {
        return (n + 1) * (n - 2) - (n * (n + 1) / 2 - 3);
    }
}
