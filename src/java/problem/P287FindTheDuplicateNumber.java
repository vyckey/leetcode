package problem;

import org.junit.Assert;
import org.junit.Test;

public class P287FindTheDuplicateNumber {
    @Test
    public void test() {
        Assert.assertEquals(9, findDuplicate(new int[]{2,5,9,6,9,3,8,9,7,1}));
        Assert.assertEquals(3, findDuplicate(new int[]{3,1,3,4,2}));
        Assert.assertEquals(17, findDuplicate(new int[]{18,13,14,17,9,19,7,17,4,6,17,5,11,10,2,15,8,12,16,17}));
    }

    /**
     * 采用二进制标记位
     */
    public int findDuplicateByBinary(int[] nums) {
        int bytes = (nums.length + 8) >> 3;
        byte[] bin = new byte[bytes];
        for (int i = 0; i < nums.length; i++) {
            int offset = nums[i];
            int index = offset >> 3;
            int mask = 0x1 << (offset & 0x7);
            if ((bin[index] & mask) != 0) {
                return nums[i];
            }
            bin[index] |= mask;
        }
        return -1;
    }

    /**
     * 二分查找
     */
    public int findDuplicateByBinSearch(int[] nums) {
        int left = 1;
        int right = nums.length;
        while (left < right) {
            int middle = (left + right) >> 1;
            int count = 0;
            for (int num : nums) {
                if (num <= middle) {
                    count++;
                }
            }
            if (count > middle) {
                right = (right == middle) ? (middle - 1) : middle;
            } else {
                left = (left == middle) ? (middle + 1) : middle;
            }
        }
        return left;
    }

    /**
     * 快慢指针找环点
     */
    public int findDuplicate(int[] nums) {
        if (nums.length < 2) {
            return -1;
        }
        int slow = 0;
        int fast = slow;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        fast = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

}
