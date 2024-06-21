package problem;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class P48RotateImage {
    @Test
    public void test() {
        int[][] matrix1 = new int[][]{
            {1,2,3},{4,5,6},{7,8,9}
        };
        rotate(matrix1);
        Assert.assertTrue(Arrays.deepEquals(new int[][]{
            {7,4,1},{8,5,2},{9,6,3}
        }, matrix1));

        int[][] matrix2 = new int[][]{
            {5,1,9,11},{2,4,8,10},{13,3,6,7},{15,14,12,16}
        };
        rotate(matrix2);
        Assert.assertTrue(Arrays.deepEquals(new int[][]{
            {15,13,2,5},{14,3,4,1},{12,6,8,9},{16,7,10,11}
        }, matrix2));
    }

    public void rotate(int[][] matrix) {
        int n = matrix.length, m = (n >> 1);
        int k = m + (n & 1);
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < m; j++) {
                rotate(matrix, n, i, j);
            }
        }
    }

    private void rotate(int[][] matrix, int n, int i, int j) {
        int num1 = matrix[i][j];
        int num2 = matrix[j][n - i - 1];
        int num3 = matrix[n - i - 1][n - j - 1];
        int num4 = matrix[n - j - 1][i];
        matrix[i][j] = num4;
        matrix[j][n - i - 1] = num1;
        matrix[n - i - 1][n - j - 1] = num2;
        matrix[n - j - 1][i] = num3;
    }
}
