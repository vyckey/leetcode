package problem;

import org.junit.Assert;
import org.junit.Test;

public class P134GasStation {
    @Test
    public void test() {
        Assert.assertEquals(3, canCompleteCircuit(new int[]{1,2,3,4,5}, new int[]{3,4,5,1,2}));
        Assert.assertEquals(-1, canCompleteCircuit(new int[]{2,3,4}, new int[]{3,4,3}));
    }

    /**
     * 贪心算法
     * O(n)时间复杂度
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        final int n = gas.length;
        int i = 0;
        while (i < n) {
            // 从i出发能达到的最远位置j
            int l = 0, j = i, g = 0;
            while (l < n && g + gas[j] >= cost[j]) {
                g = g + gas[j] - cost[j];
                l++;
                j = (i + l) % n;
            }
            if (l == n) {
                return i;
            } else if (i + l < n) {
                // 更新i到j
                i = (i == j) ? i + 1 : j;
            } else {
                break;
            }
        }
        return -1;
    }
}