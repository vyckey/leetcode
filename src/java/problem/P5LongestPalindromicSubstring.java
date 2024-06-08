package problem;

import org.junit.Assert;
import org.junit.Test;

public class P5LongestPalindromicSubstring {
    @Test
    public void test() {
        Assert.assertEquals("aba", longestPalindrome("aba"));
        Assert.assertEquals("bbb", longestPalindrome("kbbbl"));
        Assert.assertEquals("bb", longestPalindrome("cbbd"));
        Assert.assertEquals("aca", longestPalindrome("aacabdkacaa"));
    }

    /**
     * 动态规划
     * dp(i,j)=dp(i+1,j-1)&s[i]=s[j]
     */
    public String longestPalindrome(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
        }
        int maxLen = 1, from = 0;
        for (int len = 2; len <= s.length(); len++) {
            for (int i = 0, j = i + len - 1; j < s.length(); i++, j = i + len - 1) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = (i + 1 >= j - 1) || dp[i + 1][j - 1];

                    if (len > maxLen && dp[i][j]) {
                        maxLen = len;
                        from = i;
                    }
                }
            }
        }
        return s.substring(from, from + maxLen);
    }
}
