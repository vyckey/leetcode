package problem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class P62UniquePaths {
    @Test
    public void test() {
        Assert.assertEquals(28, uniquePaths(3, 7));
        Assert.assertEquals(3, uniquePaths(3, 2));
    }

    /**
     * 动态规划
     * f(m,n)=f(m,n-1)+f(m-1,n)
     */
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[2][n];
        for (int j = 0; j < n; j++) {
            dp[1][j] = 1;
        }
        for (int i = 1; i < m; i++) {
            int k =(i + 1) % 2;
            dp[k][0] = 1;

            for (int j = 1; j < n; j++) {
                dp[k][j] = dp[k][j - 1] + dp[(k + 1) % 2][j];
            }
        }
        return dp[m % 2][n - 1];
    }
}
