package problem;

import org.junit.Assert;
import org.junit.Test;

public class P31NextPermutation {

    @Test
    public void test() {
        Assert.assertArrayEquals(new int[]{1,3,2}, retNextPermutation(new int[]{1,2,3}));
        Assert.assertArrayEquals(new int[]{1,2,3}, retNextPermutation(new int[]{3,2,1}));
        Assert.assertArrayEquals(new int[]{1,5,1}, retNextPermutation(new int[]{1,1,5}));
        Assert.assertArrayEquals(new int[]{1,1,5}, retNextPermutation(new int[]{5,1,1}));
        Assert.assertArrayEquals(new int[]{2,3,1,2,2,2,4,5,7}, retNextPermutation(new int[]{2,2,7,5,4,3,2,2,1}));
    }

    public int[] retNextPermutation(int[] nums) {
        nextPermutation(nums);
        return nums;
    }

    public void nextPermutation(int[] nums) {
        int i = nums.length - 1;
        // 找到最右边的第一个制高点位置i
        while (i > 0 && nums[i - 1] >= nums[i]) {
            i--;
        }

        // 把制高点左侧位置i-1和下一个比它大的值进行交换
        int j = nums.length - 1;
        if (i > 0) {
            while (nums[j] <= nums[i - 1]) {
                j--;
            }
            swap(nums, i - 1, j);
        }
        
        // 对制高点右侧的单调递减序列进行翻转
        int k = nums.length - 1;
        while (i < k) {
            swap(nums, i++, k--);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}