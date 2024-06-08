package problem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class P46Permutations {
    private static boolean checkPermuations(int[] nums, List<List<Integer>> result) {
        int count = 1;
        for (int i = nums.length; i > 0; --i) {
            count *= i;
        }
        if (result == null || result.size() != count) {
            return false;
        }

        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        for (List<Integer> permut: result) {
            if (permut == null || permut.size() != numSet.size()) {
                return false;
            }
            Set<Integer> set = new HashSet<>(permut);
            set.removeAll(numSet);
            if (!set.isEmpty()) {
                return false;
            }
        }
        long distinct = result.stream()
            .map(list -> list.stream().map(Object::toString).collect(Collectors.joining(",")))
            .distinct().count();
        return distinct == count;
    }

    @Test
    public void test() {
        int[][] testCases = new int[][]{
            {-10},
            {1,3,10},
            {8,-10,9,2,7,3}
        };
        for (int[] testCase : testCases) {
            Assert.assertTrue(checkPermuations(testCase, permute(testCase)));
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Set<Integer> remain = new HashSet<>();
        for (int num : nums) {
            remain.add(num);
        }

        permute(result, new ArrayList<>(nums.length), remain);
        return result;
    }

    private void permute(List<List<Integer>> result, List<Integer> selected, Set<Integer> remain) {
        if (remain.isEmpty()) {
            result.add(new ArrayList<>(selected));
            return;
        }
        Integer[] remainArr = remain.toArray(new Integer[0]);
        for (Integer val : remainArr) {
            selected.add(val);
            remain.remove(val);
            permute(result, selected, remain);
            remain.add(val);
            selected.remove(selected.size() - 1);
        }
    }
}
