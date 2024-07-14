package problem;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class P2567MinimumScoreByChangingTwoElements {
    @Test
    public void test() {
        Solution1 solution = new Solution1();
        Assert.assertEquals(0, solution.minimizeSum(new int[]{1,4,3}));
        Assert.assertEquals(3, solution.minimizeSum(new int[]{1,4,7,8,5}));
        Assert.assertEquals(39, solution.minimizeSum(new int[]{21,13,21,72,35,52,74}));
    }

    private static class Solution1 {
        /**
         * 排序+双指针
         */
        public int minimizeSum(int[] nums) {
            final int n = nums.length;
            Arrays.sort(nums);
            int highScore = Math.abs(nums[n - 1] - nums[0]);
            for (int i = 0, j = n - 3; j < n; i++, j++) {
                highScore = Math.min(highScore, Math.abs(nums[i] - nums[j]));
            }
            return highScore;
        }
    }

    private static class Solution2 {
        public int minimizeSum(int[] nums) {
            int[] minNums = Arrays.copyOfRange(nums, 0, 3), maxNums = Arrays.copyOfRange(nums, 0, 3);
            sort(minNums);
            sort(maxNums);
    
            for (int i = 3; i < nums.length; i++) {
                if (nums[i] < minNums[2]) {
                    minNums[2] = nums[i];
                    sort(minNums);
                }
                if (nums[i] > maxNums[0]) {
                    maxNums[0] = nums[i];
                    sort(maxNums);
                }
            }
    
            int highScore = maxNums[2] - minNums[0];
            for (int i = 0; i < minNums.length; i++) {
                highScore = Math.min(highScore, maxNums[i] - minNums[i]);
            }
            return highScore;
        }
    
        private void sort(int[] nums) {
            for (int i = nums.length; i >= 1; i--) {
                for (int j = 1; j < i; j++) {
                    if (nums[j] < nums[j - 1]) {
                        swap(nums, j, j - 1);
                    }
                }
            }
        }
    
        private void swap(int[] nums, int i, int j) {
            if (i != j) {
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
            }
        }
    }
}
