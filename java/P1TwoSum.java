import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class P1TwoSum {
    @Test
    public void test() {
        int[] nums = new int[]{3,2,4};
        int[] result = twoSum(nums, 6);
		System.out.println(Arrays.toString(result));
        Assert.assertArrayEquals(result, new int[]{1,2});
    }

    public int[] twoSum(int[] nums, int target) { 
        Map<Integer, Integer> numMap = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			numMap.put(nums[i], i);
		}

		for (int i = 0; i < nums.length; i++) {
			int num2 = target - nums[i];
			if (numMap.containsKey(num2)) {
				int idx2 = numMap.get(num2);
				if (i != idx2) {
					return new int[]{i, idx2};
				}
			}
		}
        return new int[0];
    }
}