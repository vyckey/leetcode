package problem;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class P560SubArraySumEqualsK2 {
    @Test
    public void test() {
        int[] nums = new int[]{1, 2, 3};
        int result = subarraySum(nums, 5);
        Assert.assertEquals(2, result);
    }

    /**
     * 前缀和+哈希表
     * 累计和：acc[i]=sum(nums[0..i])
     * 前缀和：presum[i+1]=sum(nums[0..i])
     * 以下标i结尾的子数组和为k的总个数：precnt[i]=count(presum[j]==acc[i]-k)，其中j<i
     * 那么nums的子数组和为k的总个数为sum(precnt[i])，其中i为[0,n)
     */
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        int acc = 0;
        // key为前缀和，value为同一前缀和的个数
        Map<Integer, Integer> hashtable = new HashMap<>();
        hashtable.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            acc += nums[i];
            int cnt = hashtable.getOrDefault(acc - k, 0);
            count += cnt;
            hashtable.put(acc, hashtable.getOrDefault(acc, 0) + 1);
        }
        return count;
    }
}
