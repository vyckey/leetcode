package problem;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

import util.ArrayUtil;
import util.RandomUtil;

public class P42TrappingRainWater {
    @Test
    public void test1() {
        Assert.assertEquals(2, trap(new int[]{2,0,2}));
        Assert.assertEquals(9, trap(new int[]{4,2,0,3,2,5}));
        Assert.assertEquals(6, trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
    }

    @Test
    public void test2() {
        for (int i = 0; i < 100; i++) {
            int len = RandomUtil.nextInt(1, 2 * 10^4);
            int[] height = ArrayUtil.randomIntArray(len, 0, 10^5);

            int water = trap(height);
            Assert.assertEquals(water, trap2(height));
            Assert.assertEquals(water, trap3(height));
        }
    }

    /**
     * 单调栈（单调递减）
     * 栈内保持元素下标索引
     */
    public int trap(int[] height) {
        int water = 0;
        Deque<Integer> deque = new LinkedList<>();
        int i = 0;
        while (i < height.length) {
            // 把大于等于当前高度的都弹出栈，保持单调递减
            while (!deque.isEmpty() && height[i] >= height[deque.peek()]) {
                // stack[peek-1]与i之间就是可以求的雨水量
                int last = deque.pop();
                if (!deque.isEmpty()) {
                    int w = i - deque.peek() - 1;
                    int h = Math.min(height[i], height[deque.peek()]) - height[last];
                    water += w * h;
                }
            }
            deque.push(i++);
        }
        return water;
    }

    /**
     * 双指针
     */
    public int trap2(int[] height) {
        int[] result = trapIncr(height);
        int water = result[0], back = result[1];
        if (back == height.length - 1) {
            return water;
        }

        int[] height2 = new int[height.length - back];
        for (int i = 0; i < height2.length; i++) {
            height2[i] = height[height.length - i - 1];
        }
        water += trapIncr(height2)[0];
        return water;
    }
    
    private int[] trapIncr(int[] height) {
        int water = 0;
        int front = 1, back = 0, s = 0;
        while (front < height.length) {
            while (front < height.length && height[front] < height[back]) {
                s += height[front];
                front++;
            }
            if (front < height.length) {
                int w = front - back - 1;
                int h = height[back];
                water += (w * h - s);
                back = front;
                front += 1;
                s = 0;
            }
        }
        return new int[]{water, back};
    }

    /**
     * 动态规划
     * leftMaxHeight[i]=max(height[i],leftMaxHeight[i-1])
     * rightMaxHeight[i]=max(height[i],rightMaxHeight[i+1])
     * water[i]=min(leftMaxHeight[i],rightMaxHeight[i])-height[i]
     */
    public int trap3(int[] height) {
        int[] leftMaxHeight = new int[height.length];
        int[] rightMaxHeight = new int[height.length];

        leftMaxHeight[0] = height[0];
        rightMaxHeight[height.length - 1] = height[height.length - 1];
        for (int i = 1; i < height.length; i++) {
            int j = height.length - i - 1;
            leftMaxHeight[i] = Math.max(leftMaxHeight[i - 1], height[i]);
            rightMaxHeight[j] = Math.max(rightMaxHeight[j + 1], height[j]);
        }

        int water = 0;
        for (int i = 0; i < rightMaxHeight.length; i++) {
            int w = Math.min(leftMaxHeight[i], rightMaxHeight[i]) - height[i];
            water += w;
        }
        return water;
    }
}
