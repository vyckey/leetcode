package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class P54SpiralMatrix {
    @Test
    public void test() {
        Assert.assertEquals(Arrays.asList(1,2,3,6,9,8,7,4,5), spiralOrder(new int[][]{
            {1,2,3},{4,5,6},{7,8,9}
        }));

        Assert.assertEquals(Arrays.asList(1,2,3,6,9,12,11,10,7,4,5,8), spiralOrder(new int[][]{
            {1,2,3},{4,5,6},{7,8,9},{10,11,12}
        }));

        Assert.assertEquals(Arrays.asList(1,2,3,4,8,12,11,10,9,5,6,7), spiralOrder(new int[][]{
            {1,2,3,4},{5,6,7,8},{9,10,11,12}
        }));
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int circle = Math.min((m + 1) >> 1, (n + 1) >> 1);

        List<Integer> result = new ArrayList<>(m * n);
        for (int c = 0; c < circle; c++) {
            for (int j = 0; j < n - 2 * c; j++) {
                result.add(matrix[c][c + j]);
            }
            for (int i = 0; i < m - 2 * (c + 1); i++) {
                result.add(matrix[c + i + 1][n - c - 1]);
            }
            if (c < m - c - 1) {
                for (int j = n - 2 * c - 1; j >= 0; j--) {
                    result.add(matrix[m - c - 1][c + j]);
                }
            }
            if (n - c -1 > c) {
                for (int i = m - 2 * (c + 1) - 1; i >= 0; i--) {
                    result.add(matrix[c + i + 1][c]);
                }
            }
        }
        return result;
    }
}
