package problem;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class P64MinimumPathSum {
    @Test
    public void test() {

    }

    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Map<Integer, Integer> minSumMap = new HashMap<>();
        minSumMap.put(m * n - 1, grid[m - 1][n - 1]);
        return minPathSum(grid, minSumMap, 0, 0);
    }

    private int minPathSum(int[][] grid, Map<Integer, Integer> minSumMap, int i, int j) {
        int m = grid.length, n = grid[0].length;
        int pos = i * n + j;
        if (minSumMap.containsKey(pos)) {
            return minSumMap.get(pos);
        }

        int minSum = Integer.MAX_VALUE;
        if (i + 1 < m) {
            minSum = Math.min(minSum, minPathSum(grid, minSumMap, i + 1, j));
        }
        if (j + 1 < n) {
            minSum = Math.min(minSum, minPathSum(grid, minSumMap, i, j + 1));
        }
        if (minSum != Integer.MAX_VALUE) {
            minSum += grid[i][j];
        }
        minSumMap.put(pos, minSum);
        return minSum;
    }
}
