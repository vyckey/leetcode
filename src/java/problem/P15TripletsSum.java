package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class P15TripletsSum {
    @Test
    public void test() {
        List<List<Integer>> result = threeSum(new int[]{3,0,-2,-1,1,2});
        System.out.println(result);
    }

    /**
     * 方法：哈希表
     * 哈希表统计每个整型值出现的次数，然后对有序列表进行双指针遍历
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        Map<Integer, Integer> numberCountMap = new HashMap<>();
        for (int num : nums) {
            numberCountMap.put(num, numberCountMap.getOrDefault(num, 0) + 1);
        }

        List<List<Integer>> triplets = new ArrayList<>();
        for (int i = 0; i + 2 < nums.length; i++) {
            final int first = nums[i];
            if (i > 0 && nums[i - 1] == first) {
                continue;
            }
            numberCountMap.put(first, numberCountMap.get(first) - 1);
            for (int j = i + 1; j + 1 < nums.length; j++) {
                final int second = nums[j];
                int third = - (first + second);
                if (third < second || lastContains(triplets, first, second)) {
                    continue;
                }

                if (second == third && numberCountMap.get(second) >= 2
                    || second != third && numberCountMap.getOrDefault(third, 0) >= 1) {
                        triplets.add(Arrays.asList(first, second, third));
                }
            }
            numberCountMap.put(first, numberCountMap.get(first) + 1);
        }
        return triplets;
    }

    private boolean lastContains(List<List<Integer>> triplets, int first, int second) {
        if (triplets.isEmpty()) {
            return false;
        }
        List<Integer> triplet = triplets.get(triplets.size() - 1);
        return triplet.get(0) == first && triplet.get(1) == second;
    }
}
