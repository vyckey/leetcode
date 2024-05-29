package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class P78Subsets {

    @Test
    public void test() {
        int[][] testCases = new int[][]{
            new int[]{},
            new int[]{2},
            new int[]{1,2,3}
        };
        for (int[] testCase : testCases) {
            System.out.println("case:" + Arrays.toString(testCase));
            List<List<Integer>> subsets = subsets(testCase);
            System.out.println("subsets:" + subsets);
        }
    }

    /**
     * 递归
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();
        subsets(nums, nums.length, subsets);
        return subsets;
        
    }

    private void subsets(int[] nums, int end, List<List<Integer>> subsets) {
        if (end == 0) {
            subsets.add(new ArrayList<>());
            return;
        }

        subsets(nums, end - 1, subsets);

        int num = nums[end - 1];
        List<List<Integer>> newSubsets = new ArrayList<>(subsets.size());
        for (List<Integer> subset : subsets) {
            List<Integer> newSubset = new ArrayList<>(subset.size() + 1);
            newSubset.addAll(subset);
            newSubset.add(num);
            newSubsets.add(newSubset);
        }
        subsets.addAll(newSubsets);
    }
}
