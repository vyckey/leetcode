package problem;

import java.util.Stack;

import org.junit.Assert;
import org.junit.Test;

public class P84LargestRectangleInHistogram {
    @Test
    public void test() {
        Assert.assertEquals(2, largestRectangleArea(new int[]{2}));
        Assert.assertEquals(3, largestRectangleArea(new int[]{2,1,2}));
        Assert.assertEquals(5, largestRectangleArea(new int[]{5,1}));
        Assert.assertEquals(10, largestRectangleArea(new int[]{2,1,5,6,2,3}));
    }

    /**
     * 单调栈
     */
    public int largestRectangleArea(int[] heights) {
        // 往左右两边补充height=0元素
        int[] newHeights = new int[heights.length + 2];
        for (int i = 0; i < heights.length; i++) {
            newHeights[i + 1] = heights[i];
        }
        int largestRectArea = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < newHeights.length; i++) {
            if (!stack.isEmpty()) {
                while (newHeights[stack.peek()] > newHeights[i]) {
                    int height = newHeights[stack.pop()];
                    int width = (i - stack.peek() - 1);
                    int curRectArea = height * width;
                    largestRectArea = Math.max(largestRectArea, curRectArea);
                }
            }
            stack.push(i);
        }
        return largestRectArea;
    }
}
