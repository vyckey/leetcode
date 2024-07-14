package problem;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class P128LongestConsecutiveSequence {
    @Test
    public void test() {
        Assert.assertEquals(0, longestConsecutive(new int[]{}));
        Assert.assertEquals(4, longestConsecutive(new int[]{100,4,200,1,3,2}));
        Assert.assertEquals(9, longestConsecutive(new int[]{0,3,7,2,5,8,4,6,0,1}));
    }

    /**
     * 使用Set来判断元素是否存在
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> numSet = new HashSet<>(nums.length);
        for (int num : nums) {
            numSet.add(num);
        }

        int maxLen = 0;
        for (int start : numSet) {
            // 只有到达左边界的时候才检查长度
            if (!numSet.contains(start - 1)) {
                int end = start;
                while (numSet.contains(end + 1)) {
                    end++;
                }
                maxLen = Math.max(maxLen, end - start + 1);
            }
        }
        return maxLen;
    }
}
