package problem;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class P930BinarySubArraysWithSum {
    @Test
    public void test() {
        Assert.assertEquals(4, numSubarraysWithSum(new int[]{1,0,1,0,1}, 2));
        Assert.assertEquals(15, numSubarraysWithSum(new int[]{0,0,0,0,0}, 0));
    }

    public int numSubarraysWithSum(int[] nums, int goal) {
        Map<Integer, Integer> prefixCountMap = new HashMap<>();
        int total = 0, prefixSum = 0;
        for (int i = 0; i < nums.length; i++) {
            prefixCountMap.put(prefixSum, prefixCountMap.getOrDefault(prefixSum, 0) + 1);

            // 更新前缀和
            prefixSum += nums[i];
            
            int sub = prefixSum - goal;
            if (prefixCountMap.containsKey(sub)) {
                total += prefixCountMap.get(sub);
            }
        }
        return total;
    }
}