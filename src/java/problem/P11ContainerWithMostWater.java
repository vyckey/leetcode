package problem;

import org.junit.Assert;
import org.junit.Test;

public class P11ContainerWithMostWater {
    @Test
    public void test() {
        Assert.assertEquals(0, maxArea(new int[]{3}));
        Assert.assertEquals(49, maxArea(new int[]{1,8,6,2,5,4,8,3,7}));
        Assert.assertEquals(1, maxArea(new int[]{1,1}));
    }

    /**
     * 双指针
     * S[i..j]=(j-i)*min(height[i], height[j])
     * 收缩短板可能高度会增加带来总面积增加，收缩长板一定会导致面积缩小，所以每次只收缩短板
     */
    public int maxArea(int[] height) {
        int i = 0, j = height.length - 1;
        int maxArea = 0;
        do {
            int area = (j - i) * Math.min(height[i], height[j]);
            maxArea = Math.max(maxArea, area);
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        } while (i < j);
        return maxArea;
    }
}
