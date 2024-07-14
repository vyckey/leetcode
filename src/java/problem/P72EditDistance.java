package problem;

import org.junit.Assert;
import org.junit.Test;

public class P72EditDistance {
    @Test
    public void test() {
        Assert.assertEquals(3, minDistance("horse", "ros"));
        Assert.assertEquals(5, minDistance("intention", "execution"));
        Assert.assertEquals(1, minDistance("", "a"));
        Assert.assertEquals(3, minDistance("", "abc"));
        Assert.assertEquals(6, minDistance("plasma", "altruism"));
    }

    /**
     * 动态规划
     * if s[i]==s[j]
     *   f(i,j)=min(f(i-1,j-1),f(i-1,j)+1,f(i,j-1)+1)
     * else
     *   f(i,j)=1+min(f(i-1,j-1),f(i-1,j),f(i,j-1))
     */
    public int minDistance(String word1, String word2) {
        final int len1 = word1.length(), len2 = word2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 1; i <= len1; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j <= len2; j++) {
            dp[0][j] = j;
            for (int i = 1; i <= len1; i++) {
                int d = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
                int s = word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1;
                d = Math.min(d, dp[i - 1][j - 1] + s);
                dp[i][j] = d;
            }
        }
        return dp[len1][len2];
    }
}
