package problem;

import org.junit.Assert;
import org.junit.Test;

public class P279PerfectSquares {
    @Test
    public void test() {
        Assert.assertEquals(1, numSquares(1));
        Assert.assertEquals(1, numSquares(9));
        Assert.assertEquals(1, numSquares(81));
        Assert.assertEquals(2, numSquares(8));
        Assert.assertEquals(2, numSquares(13));
    }

    /**
     * f(i)=1+min_{j=1}^{\sqrt{i}}(f(i-j^2))
     */
    public int numSquares(int n) {
        int[] nums = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int num = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                num = Math.min(num, nums[i - j * j]);
            }
            nums[i] = num + 1;
        }
        return nums[n];
    }
}
