package util;

import java.util.Random;

public class ArrayUtil {
    private ArrayUtil() {
    }

    public static int[] randomIntArray(int length, int fromIncluded, int toExclusive) {
        if (length < 0) {
            throw new IllegalArgumentException("length cannot be negtive");
        }
        
        Random random = new Random();
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = random.nextInt(fromIncluded, toExclusive);
        }
        return arr;
    }

    public static int[][] partition(int size, int... ints) {
        int row = ints.length / size;
        if (size <= 0 || ints.length != row * size) {
            throw new IllegalArgumentException("invalid size value");
        }
        int[][] arr = new int[row][size];
        for (int i = 0; i < ints.length; i++) {
            arr[i / size][i % size] = ints[i];
        }
        return arr;
    }

    public static boolean deepEquals(int[][] matrix1, int[][] matrix2) {
        if (matrix1.length != matrix2.length) {
            return false;
        }
        if (matrix1[0].length != matrix2[0].length) {
            return false;
        }
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                if (matrix1[i][j] != matrix2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
