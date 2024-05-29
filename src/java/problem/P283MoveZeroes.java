package problem;

import org.junit.Assert;
import org.junit.Test;

public class P283MoveZeroes {
    @Test
    public void test() {
        int[][] testCases = new int[][]{
            new int[]{0},
            new int[]{0, 2, 0},
            new int[]{1, 2, 0, 0, 3, 0, 4},
            new int[]{0, 1, 0, 3, 12}
        };
        for (int[] testCase : testCases) {
            moveZeroes(testCase);
        }
        Assert.assertArrayEquals(new int[]{0}, testCases[0]);
        Assert.assertArrayEquals(new int[]{2, 0, 0}, testCases[1]);
        Assert.assertArrayEquals(new int[]{1, 2, 3, 4, 0, 0, 0}, testCases[2]);
        Assert.assertArrayEquals(new int[]{1, 3, 12, 0, 0}, testCases[3]);
    }

    public void moveZeroes1(int[] nums) {
        int i = 0, j = 0;
        while (j < nums.length) {
            while (i < nums.length && nums[i] != 0) {
                i++;
            }
            if (i > j) {
                j = i;
            }
            while (j < nums.length && nums[j] == 0) {
                j++;
            }
            if (j < nums.length) {
                nums[i] = nums[j];
                nums[j] = 0;
            }
        }
    }

    /**
     * 快排思想，
     * 不等于0的放左边（小于某个数），等于0的放右边（大于等于某个数）
     */
    public void moveZeroes(int[] nums) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j++] = tmp;
            }
        }
    }
}
