package problem;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class P80RemoveDuplicatesFromSortedArrayII {
    public int[] removeDuplicatesArr(int[] nums) {
        int size = removeDuplicates(nums);
        return Arrays.copyOf(nums, size);
    }

    @Test
    public void test() {
        Assert.assertArrayEquals(new int[]{1,1,2,2,3}, removeDuplicatesArr(new int[]{1,1,1,2,2,3}));
        Assert.assertArrayEquals(new int[]{0,0,1,1,2,3,3}, removeDuplicatesArr(new int[]{0,0,1,1,1,1,1,2,3,3}));
    }

    public int removeDuplicates(int[] nums) {
        int c = 2;
        // 数组中的一号和二号元素肯定不用删除
        for (int j = 2; j < nums.length; j++) {
            if (nums[j] != nums[c - 2]) {
                nums[c++] = nums[j];
            }
        }
        return c;
    }

    public int removeDuplicatesByCount1(int[] nums) {
        int i = 0, j = 0;
        while (j < nums.length) {
            int k = j;
            while (k + 1 < nums.length && nums[k + 1] == nums[j]) {
                k++;
            }

            nums[i++] = nums[j];
            if (k > j) {
                nums[i++] = nums[j];
            }
            j = k + 1;
        }
        return i;
    }

    public int removeDuplicatesByCount2(int[] nums) {
        int i = 0, c = 1;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] == nums[i]) {
                c++;
            } else {
                if (c > 1) {
                    nums[++i] = nums[i - 1];
                }
                nums[++i] = nums[j];
                c = 1;
            }
        }
        if (c > 1) {
            nums[++i] = nums[i - 1];
        }
        return i + 1;
    }
}