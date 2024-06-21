package problem;

import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import util.ArrayUtil;

public class P74SearchA2DMatrix {
    @Test
    public void test() {
        Random random = new Random();
        for (int c = 0; c < 100; c++) {
            int size = random.nextInt(1, 10000);
            int[] arr = ArrayUtil.randomIntArray(size, -10^4, 10^4);
            Arrays.sort(arr);
            int n = random.nextInt(1, size);
            int m = (size + n - 1) / n;

            int[][] matrix = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i * n + j >= size) {
                        matrix[i][j] = arr[size - 1];
                    } else {
                        matrix[i][j] = arr[i * n + j];
                    }
                }
            }

            if (random.nextBoolean()) {
                int target = arr[random.nextInt(0, size)];
                Assert.assertTrue(searchMatrix(matrix, target));
            } else {
                int target = arr[arr.length - 1] + 1;
                Assert.assertFalse(searchMatrix(matrix, target));
            }
        }
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int left = 0, right = m * n;
        while (left + 1 < right) {
            int mid = (left + right) >> 1;
            int midval = getVal(matrix, mid);
            if (target < midval) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return getVal(matrix, left) == target;
    }

    private int getVal(int[][] matrix, int k) {
        int n = matrix[0].length;
        return matrix[k / n][k % n];
    }
}
