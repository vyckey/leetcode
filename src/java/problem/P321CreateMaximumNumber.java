package problem;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

public class P321CreateMaximumNumber {
    @Test
    public void test() {
        Assert.assertArrayEquals(new int[]{9,8,6,5,3}, maxNumber(new int[]{3,4,6,5}, new int[]{9,1,2,5,8,3}, 5));
        Assert.assertArrayEquals(new int[]{6,7,6,0,4}, maxNumber(new int[]{6,7}, new int[]{6,0,4}, 5));
        Assert.assertArrayEquals(new int[]{9,8,9}, maxNumber(new int[]{3,9}, new int[]{8,9}, 3));
        Assert.assertArrayEquals(new int[]{9,8,6,5,3}, maxNumber(new int[]{9,1,2,5,8,3}, new int[]{3,4,6,5}, 5));
        Assert.assertArrayEquals(new int[]{7,3,8,2,5,6,4,4,0,6,5,7,6,2,0}, maxNumber(new int[]{2,5,6,4,4,0}, new int[]{7,3,8,0,6,5,7,6,2}, 15));
    }

    /**
     * 单调栈
     * 等价于选择l1长度的最大nums1子数组，以及l2长度的最大nums2子数组，且k=l1+l2
     * l1长度的最大nums1子数组等价于删除len-l1个元素的最大子数组
     */
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int[] maxNumber = null;
        for (int l1 = 0; l1 <= nums1.length; l1++) {
            int l2 = k - l1;
            if (l2 < 0 || l2 > nums2.length) {
                continue;
            }

            int[] maxNumber1 = maxNumberAfterRemoveKNumbers(nums1, nums1.length - l1);
            int[] maxNumber2 = maxNumberAfterRemoveKNumbers(nums2, nums2.length - l2);
            // 合并l1长度和l2长度的子数组为最大数组
            int[] curMaxNumber = mergeToMaxNumber(maxNumber1, maxNumber2);

            if (maxNumber == null || compare(maxNumber, curMaxNumber) < 0) {
                maxNumber = curMaxNumber;
            }
        }
        return maxNumber;
    }

    private int[] maxNumberAfterRemoveKNumbers(int[] nums, int k) {
        if (k <= 0) return nums;
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            while (k > 0 && !deque.isEmpty() && deque.peek() < nums[i]) {
                deque.pop();
                k--;
            }
            deque.push(nums[i]);
        }
        while (k > 0) {
            deque.pop();
            k--;
        }

        int[] maxNumber = new int[deque.size()];
        for (int i = maxNumber.length - 1; i >= 0; i--) {
            maxNumber[i] = deque.pop();
        }
        return maxNumber;
    }

    private int[] mergeToMaxNumber(int[] nums1, int[] nums2) {
        int i = 0, j = 0;
        int[] maxNumber = new int[nums1.length + nums2.length];
        for (int k = 0; k < maxNumber.length; k++) {
            boolean use1;
            if (i < nums1.length && j < nums2.length) {
                if (nums1[i] == nums2[j]) {
                    use1 = compare(nums1, nums2, i, j) >= 0;
                } else {
                    use1 = nums1[i] > nums2[j];
                }
            } else {
                use1 = j >= nums2.length;
            }
            maxNumber[k] = use1 ? nums1[i++] : nums2[j++];
        }
        return maxNumber;
    }

    private int compare(int[] nums1, int[] nums2) {
        return compare(nums1, nums2, 0, 0);
    }

    private int compare(int[] nums1, int[] nums2, int i, int j) {
        int l1 = nums1.length - i, l2 = nums2.length - j;
        for (int k = 0; k < Math.min(l1, l2); k++) {
            if (nums1[i + k] == nums2[j + k]) {
                continue;
            }
            return nums1[i + k] - nums2[j + k];
        }
        return l1 - l2;
    }
}
