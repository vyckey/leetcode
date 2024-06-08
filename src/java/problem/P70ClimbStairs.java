package problem;

import org.junit.Assert;
import org.junit.Test;

public class P70ClimbStairs {
    @Test
    public void test() {
        Assert.assertEquals(1, climbStairs(1));
        Assert.assertEquals(2, climbStairs(2));
        Assert.assertEquals(8, climbStairs(5));
    }

    // f(n)=f(n-1)+f(n-2)
    public int climbStairs(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[] ways = new int[2];
        ways[0] = 1;
        ways[1] = 2;
        for (int i = 3; i <= n; i++) {
            int way = ways[0] + ways[1];
            ways[0] = ways[1];
            ways[1] = way;
        }
        return ways[1];
    }
}
