package problem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import util.ArrayUtil;
import util.RandomUtil;

public class P23SearchInRatatedSortedArray {
    @Test
    public void test1() {
        Assert.assertEquals(0, search(new int[]{5,1,3}, 5));
        Assert.assertEquals(1, search(new int[]{1,3}, 3));
        Assert.assertEquals(-1, search(new int[]{1,3}, 0));
        Assert.assertEquals(0, search(new int[]{3,1}, 3));
        Assert.assertEquals(-1, search(new int[]{1}, 0));
        Assert.assertEquals(-1, search(new int[]{4,5,6,7,0,1,2}, 3));
        Assert.assertEquals(4, search(new int[]{4,5,6,7,0,1,2}, 0));
    }

    @Test
    public void test2() {
        final int testCases = 100;
        for (int i = 0; i < testCases; i++) {
            List<Integer> list = Arrays.stream(ArrayUtil.randomIntArray(1000, -10000, 10000))
                .sorted().distinct().mapToObj(n -> n).collect(Collectors.toList());
            int k = RandomUtil.nextInt(0, list.size());
            for (int j = 0; j < k; j++) {
                list.add(0, list.removeLast());
            }

            int[] arr = list.stream().mapToInt(n -> n).toArray();
            int index = RandomUtil.nextInt(0, arr.length);
            Assert.assertEquals(index, search(arr, arr[index]));
        }
    }

    /**
     * 二分查找
     */
    public int search(int[] nums, int target) {
        int kIndex = findK(nums);
        return find(nums, target, kIndex);
    }

    private int findK(int[] nums) {
        int left = 0, right = nums.length;
        while (left + 1 < right) {
            int mid = (left + right) >> 1;
            if (mid == right - 1) {
                return nums[left] <= nums[mid] ? left : mid;
            } else if (nums[mid] >= nums[right - 1]) {
                left = mid;
            } else {
                right = mid + 1;
            }
        }
        return left;
    }

    private int find(int[] nums, int target, int k) {
        System.out.println("k="+k);
        int left = 0, right = nums.length;
        while (left + 1 < right) {
            int mid = (left + right) >> 1;
            int midval = nums[(k + mid) % nums.length];
            if (midval == target) {
                return (k + mid) % nums.length;
            } else if (target < midval) {
                right = mid;
            } else {
                left = mid;
            }
        }
        int index = (k + left) % nums.length;
        return nums[index] == target ? index : -1;
    }
}
