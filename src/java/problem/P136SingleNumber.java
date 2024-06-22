package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import util.RandomUtil;

public class P136SingleNumber {
    private static int[] newSingleNumberArray(int target) {
        Set<Integer> numSet = new HashSet<>();
        for (int i = 0; i < RandomUtil.nextInt(1, 10 ^ 4); i++) {
            numSet.add(RandomUtil.nextInt(- 3 * 10 ^ 4, 3 * 10 ^ 4));
        }
        numSet.remove(target);
        List<Integer> numList = new ArrayList<>(numSet.size() * 2 + 1);
        numList.addAll(numSet);
        numList.addAll(numSet);
        numList.add(target);
        return numList.stream().sorted((i1, i2) -> RandomUtil.nextBoolean() ? 1 : 0)
            .mapToInt(Integer::intValue).toArray();
    }

    @Test
    public void test() {
        Assert.assertEquals(5, singleNumber(new int[]{5}));
        Assert.assertEquals(3, singleNumber(new int[]{3,2,2}));

        for (int i = 0; i < 100; i++) {
            int target = RandomUtil.nextInt(- 3 * 10 ^ 4, 3 * 10 ^ 4);
            int[] nums = newSingleNumberArray(target);
            System.out.println(Arrays.toString(nums));
            Assert.assertEquals(target, singleNumber(nums));
        }
    }

    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }
}
