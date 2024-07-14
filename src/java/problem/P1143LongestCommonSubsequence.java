package problem;

import org.junit.Assert;
import org.junit.Test;

public class P1143LongestCommonSubsequence {
    @Test
    public void test() {
        Solution1 solution = new Solution1();
        Assert.assertEquals(3, solution.longestCommonSubsequence("abcde", "ace"));
        Assert.assertEquals(3, solution.longestCommonSubsequence("abc", "abc"));
        Assert.assertEquals(0, solution.longestCommonSubsequence("abc", "def"));

        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb1.append('a');
        }
        Assert.assertEquals(998, solution.longestCommonSubsequence(sb1.toString(), sb1.substring(2)));
    }

    private static class Solution1 {
        /**
         * 设dp(i,j)表示text1[0..i]与text2[0..j]的最长公共子序列，则有
         * dp(i,j)=1+dp(i-1,j-1) if text1[i]=text2[j]
         *        =max(dp(i-1,j),dp(i,j-1)) otherwise
         */
        public int longestCommonSubsequence(String text1, String text2) {
            // 滚动数组保留中间结果 O(m+n)复杂度
            int[][] dp = new int[2][text2.length()];
            for (int i = 0; i < text1.length(); i++) {
                for (int j = 0; j < text2.length(); j++) {
                    if (text1.charAt(i) == text2.charAt(j)) {
                        dp[(i + 1) % 2][j] = 1 + (j == 0 ? 0 : dp[i % 2][j - 1]);
                    } else {
                        dp[(i + 1) % 2][j] = Math.max(dp[i % 2][j], 
                            j == 0 ? 0 : dp[(i + 1) % 2][j - 1]);
                    }
                }
            }
            return dp[text1.length() % 2][text2.length() - 1];
        }
    }

    private static class Solution2 {
        /**
         * 设dp(i,j)表示text1[0..i]与text2[0..j]的最长公共子序列，则有
         * dp(i,j)=1+dp(i-1,j-1) if text1[i]=text2[j]
         *        =max(dp(i-1,j),dp(i,j-1)) otherwise
         */
        public int longestCommonSubsequence(String text1, String text2) {
            int[][] dp = new int[text1.length()][text2.length()];
            for (int i = 0; i < text1.length(); i++) {
                if (text1.charAt(i) == text2.charAt(0)) {
                    dp[i][0] = 1;
                } else if (i > 0) {
                    dp[i][0] = dp[i - 1][0];
                }
            }
            for (int j = 0; j < text2.length(); j++) {
                if (text2.charAt(j) == text1.charAt(0)) {
                    dp[0][j] = 1;
                } else if (j > 0) {
                    dp[0][j] = dp[0][j - 1];
                }
            }
            for (int i = 1; i < text1.length(); i++) {
                for (int j = 1; j < text2.length(); j++) {
                    if (text1.charAt(i) == text2.charAt(j)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
            return dp[text1.length() - 1][text2.length() - 1];
        }
    }

    private static class Solution3 {
        /**
         * 递归
         */
        public int longestCommonSubsequence(String text1, String text2) {
            return longest(text1, text2, 0, 0);
        }
    
        private int longest(String str1, String str2, int i, int j) {
            if (i >= str1.length() || j >= str2.length()) {
                return 0;
            }
    
            int k = i;
            while (k < str1.length() && str1.charAt(k) != str2.charAt(j)) {
                k++;
            }
            int len1 = longest(str1, str2, k + 1, j + 1);
            int len2 = longest(str1, str2, i, j + 1);
            if (k < str1.length()) {
                len1++;
            }
            return Math.max(len1, len2);
        }
    }
}
