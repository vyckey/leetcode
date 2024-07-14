package problem;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class P75SortColors {
    private static boolean isOrdered(int[] nums) {
        if (nums.length <= 1) return true;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] > nums[i]) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test() {
        List<int[]> numsList = Arrays.asList(
            new int[]{2},
            new int[]{2,1,2},
            new int[]{2,1,1},
            new int[]{2,0,1},
            new int[]{2,0,2,1,1,0}
        );
        for (int[] nums : numsList) {
            sortColors(nums);
            Assert.assertTrue(isOrdered(nums));
        }
    }

    /**
     * 桶排序
     */
    public void sortColors(int[] nums) {
        if (nums.length <= 1) return;

        int count0 = 0, count1 = 0;
        for (int num : nums) {
            if (num == 0) {
                count0++;
            } else if (num == 1) {
                count1++;
            }
        }

        int i = 0;
        for (; i < count0; i++) {
            nums[i] = 0;
        }
        for (; i < count0 + count1; i++) {
            nums[i] = 1;
        }
        for (; i < nums.length; i++) {
            nums[i] = 2;
        }
    }
}
