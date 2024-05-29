package problem;

import org.junit.Assert;
import org.junit.Test;

public class P198HouseRobber {
    @Test
    public void test() {
        Assert.assertEquals(0, rob(new int[]{}));
        Assert.assertEquals(7, rob(new int[]{7}));
        Assert.assertEquals(6, rob(new int[]{5, 6}));
        Assert.assertEquals(4, rob(new int[]{1,2,3,1}));
        Assert.assertEquals(12, rob(new int[]{2,7,9,3,1}));
    }

    /**
     * f(i)表示在必须偷第i家的情况下，偷前[0,i]家获得的最大收益
     * f(i)=max(f(i-1), f(i-2)+m(i))
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        } else if (nums.length == 1) {
            return nums[0];
        }

        int[] mounts = new int[nums.length];
        mounts[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            mounts[i] = Math.max(mounts[i - 1], (i < 2 ? 0 : mounts[i - 2]) + nums[i]);
        }
        return Math.max(mounts[nums.length - 2], mounts[nums.length - 1]);
    }
}
