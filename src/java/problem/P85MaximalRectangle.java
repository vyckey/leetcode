package problem;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

public class P85MaximalRectangle {
    private static char[][] split(String string) {
        String[] strs = string.split("\n");
        if (strs.length == 0) {
            throw new IllegalArgumentException("invalid string");
        }
        char[][] matrix = new char[strs.length][strs[0].length()];
        for (int i = 0; i < matrix.length; i++) {
            if (strs[i].length() != strs[0].length()) {
                throw new IllegalArgumentException("invalid string");
            }
            for (int j = 0; j < strs[0].length(); j++) {
                matrix[i][j] = strs[i].charAt(j);
            }
        }
        return matrix;
    }

    @Test
    public void test() {
        Assert.assertEquals(0, maximalRectangle(split("0")));
        Assert.assertEquals(1, maximalRectangle(split("1")));
        Assert.assertEquals(6, maximalRectangle(split("10100\n10111\n11111\n10010")));
        Assert.assertEquals(3, maximalRectangle(split("001\n111")));
    }

    /**
     * 单调栈
     */
    public int maximalRectangle(char[][] matrix) {
        final int m = matrix.length, n = matrix[0].length;
        int maxRect = 0;
        // 最后的高度补0
        int[] heights = new int[n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    heights[j] += 1;
                } else {
                    heights[j] = 0;
                }
            }
            maxRect = Math.max(maxRect, maximalRectangle(heights));
        }
        return maxRect;
    }

    private int maximalRectangle(int[] heights) {
        int maxRect = 0;
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < heights.length; i++) {
            while (!deque.isEmpty() && heights[deque.peek()] >= heights[i]) {
                int height = heights[deque.pop()];
                int lastIdx = deque.isEmpty() ? -1 : deque.peek();
                int width = i - lastIdx - 1;
                maxRect = Math.max(maxRect, width * height);
            }
            deque.push(i);
        }
        return maxRect;
    }
}