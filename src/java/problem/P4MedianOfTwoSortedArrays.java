package problem;

import org.junit.Assert;
import org.junit.Test;

public class P4MedianOfTwoSortedArrays {
    @Test
    public void test() {
        final double delta = 10^-1;
        Solution3 solution = new Solution3();
        Assert.assertEquals(1.5, solution.findMedianSortedArrays(new int[]{1,2}, new int[]{-1,3}), delta);
        Assert.assertEquals(7.0, solution.findMedianSortedArrays(new int[]{}, new int[]{7}), delta);
        Assert.assertEquals(6.0, solution.findMedianSortedArrays(new int[]{5, 6, 7}, new int[]{}), delta);
        Assert.assertEquals(8.5, solution.findMedianSortedArrays(new int[]{}, new int[]{7, 8, 9, 11}), delta);
        Assert.assertEquals(7.5, solution.findMedianSortedArrays(new int[]{3,5}, new int[]{7, 8, 9, 11}), delta);
        Assert.assertEquals(8.0, solution.findMedianSortedArrays(new int[]{7, 8, 9, 11, 14}, new int[]{3,4}), delta);
        Assert.assertEquals(7.5, solution.findMedianSortedArrays(new int[]{4,9,14}, new int[]{5,6,11}), delta);
        Assert.assertEquals(3.5, solution.findMedianSortedArrays(new int[]{1,2,3}, new int[]{4,5,6}), delta);
        Assert.assertEquals(3.5, solution.findMedianSortedArrays(new int[]{4}, new int[]{1,2,3,5,6}), delta);
    }    
}
class Solution {
    /**
     * 使用额外的数组
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] nums = new int[nums1.length + nums2.length];
        for (int k = 0, i = 0, j = 0; k < nums.length; k++) {
            if (j >= nums2.length || i < nums1.length && nums1[i] <= nums2[j]) {
                nums[k] = nums1[i++];
            } else {
                nums[k] = nums2[j++];
            }
        }
        int halflen = nums.length >> 1;
        if ((nums.length & 1) == 1) {
            return nums[halflen];
        }
        return (nums[halflen - 1] + nums[halflen]) / 2.0;
    }
}
class Solution3 {
    /**
     * 查找第k个数字
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int size = nums1.length + nums2.length;
        Slice slice1 = new Slice(nums1), slice2 = new Slice(nums2);

        int k = (size + 1) >> 1;
        double median = findKthNumber(slice1, slice2, k);
        if ((size & 1) == 0) {
            median = (median + findKthNumber(slice1, slice2, k + 1)) / 2.0;
        }
        return median;
    }

    private int findKthNumber(Slice slice1, Slice slice2, int k) {
        // 保证slice1的长度大于等于slice2的长度
        if (slice1.length() < slice2.length()) {
            Slice slice0 = slice1;
            slice1 = slice2;
            slice2 = slice0;
        }
        if (slice2.length() == 0) {
            return slice1.numAt(k - 1);
        }
        
        int halfk = k >> 1;
        halfk = Math.min(halfk, slice2.length());
        if (halfk == 0) {
            return Math.min(slice1.numAt(0), slice2.numAt(0));
        }
        if (slice1.numAt(halfk - 1) < slice2.numAt(halfk - 1)) {
            return findKthNumber(slice1.create(halfk, slice1.length()), slice2, k - halfk);
        }
        return findKthNumber(slice1, slice2.create(halfk, slice2.length()), k - halfk);
    }

    static class Slice {
        int[] nums;
        int left;
        int right;

        Slice(int[] nums) {
            this(nums, 0, nums.length);
        }

        Slice(int[] nums, int left, int right) {
            this.nums = nums;
            this.left = left;
            this.right = right;
        }

        Slice create(int from, int to) {
            return new Slice(nums, left + from, left + to);
        }

        int length() {
            return right - left;
        }

        int numAt(int i) {
            return nums[left + i];
        }
    }
}