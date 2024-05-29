package problem;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import util.ArrayUtil;

public class P73SetMatrixZeroes {

    private static int[][] cloneMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] cloneMatrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cloneMatrix[i][j] = matrix[i][j];
            }
        }
        return cloneMatrix;
    }

    @Test
    public void test1() {
        int[][] matrix = ArrayUtil.partition(4, 
        1, 2, 3, 4,
            5, 0, 7, 8,
            0, 10, 11, 12,
            13, 14, 15, 0
        );
        setZeroes(matrix);
        int[][] target = ArrayUtil.partition(4, 
            0, 0, 3, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
        );
        Assert.assertTrue(ArrayUtil.deepEquals(target, matrix));
    }

    @Test
    public void test2() {
        final int testCaseCnt = 10;
        Random rand = new Random();
        for (int c = 0; c < testCaseCnt; c++) {
            // create a random matrix
            int m = rand.nextInt(1, 201), n = rand.nextInt(1, 201);
            int[][] matrix = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (rand.nextBoolean()) {
                        matrix[i][j] = rand.nextInt();
                    }
                }
            }

            int[][] cloneMatrix = cloneMatrix(matrix);

            setZeroes1(matrix);
            setZeroes(cloneMatrix);
            boolean equals = ArrayUtil.deepEquals(matrix, cloneMatrix);
            Assert.assertTrue("fail", equals);
        }
    }

    /**
     * 使用m+n长度的标志位数组存储行列是否应该为0
     */
    public void setZeroes1(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[] zeroes = new boolean[m + n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    zeroes[i] = true;
                    zeroes[m + j] = true;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (zeroes[i] || zeroes[m + j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean firstColumn = false;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    if (j == 0) {
                        firstColumn = true;
                    } else {
                        matrix[0][j] = 0;
                    }
                }
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        if (matrix[0][0] == 0) {
            for (int j = 1; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
        if (firstColumn) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}
