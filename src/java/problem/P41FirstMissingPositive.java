package problem;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import util.ArrayUtil;
import util.RandomUtil;

public class P41FirstMissingPositive {
    private int firstMissingPositiveBySort(int[] nums) {
        int[] newNums = Arrays.copyOf(nums, nums.length);
        Arrays.sort(newNums);
        int res = 1;
        for (int i = 0; i < newNums.length; i++) {
            if (newNums[i] <= 0) continue;
            if (newNums[i] != res) {
                return res;
            }
            res++;
        }
        return newNums[newNums.length - 1] + 1;
    }

    @Test
    public void test() {
        final int testCases = 100;
        for (int i = 0; i < testCases; i++) {
            int length = RandomUtil.nextInt(1, 100000);
            int[] array = ArrayUtil.randomIntArray(length, -99999999, 999999999);
            Assert.assertEquals(firstMissingPositiveBySort(array), firstMissingPositive(array));
        }
    }

    public int firstMissingPositive(int[] nums) {
        final int len = nums.length;
        // 把值为num的元素放置到num-1的位置
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            while (0 < num && num <= len && num != nums[num - 1]) {
                swap(nums, i, num - 1);
                num = nums[i];
            }
        }
        // 原位置不是那个数的即是丢失的最小正整数
        for (int i = 0; i < len; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return len + 1;
    }

    private void swap(int[] nums, int i, int j) {
        if (i != j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }
}
