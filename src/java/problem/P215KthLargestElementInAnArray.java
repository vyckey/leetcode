package problem;

import org.junit.Assert;
import org.junit.Test;

public class P215KthLargestElementInAnArray {
    @Test
    public void test() {
        Assert.assertEquals(5, findKthLargest(new int[]{3,2,1,5,6,4}, 2));
        Assert.assertEquals(4, findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 4));
    }

    public int findKthLargest(int[] nums, int k) {
        return findKthLargest(nums, 0, nums.length, nums.length - k + 1);
    }

    private int findKthLargest(int[] nums, int left, int right, int k) {
        if (right - left == 1) {
            return nums[left];
        }
        int index = partition(nums, left, right);
        int leftCount = index - left + 1;
        if (leftCount == k) {
            return nums[index];
        } else if (leftCount > k) {
            return findKthLargest(nums, left, index, k);
        } else {
            return findKthLargest(nums, index + 1, right, k - leftCount);
        }
    }

    private int partition(int[] nums, int left, int right) {
        int pivot = nums[left];
        int index = left + 1;// 存放下一个小于pivot的数
        for (int i = left + 1; i < right; i++) {
            if (nums[i] < pivot) {
                swap(nums, i, index);
                index++;
            }
        }
        index--;
        swap(nums, index, left);
        return index;
    }

    private static void swap(int[] nums, int i, int j) {
        if (i != j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }
}
