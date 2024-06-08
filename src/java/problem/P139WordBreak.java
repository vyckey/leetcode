package problem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class P139WordBreak {
    @Test
    public void test1() {
        Assert.assertTrue(wordBreak("leetcode", Arrays.asList("leet", "code")));
        Assert.assertTrue(wordBreak("goodmorning", Arrays.asList("morning", "good")));
        Assert.assertTrue(wordBreak("applepenapple", Arrays.asList("apple", "pen")));
        Assert.assertFalse(wordBreak("catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat")));
        Assert.assertTrue(wordBreak("aaaaaaa", Arrays.asList("aaa", "aaaa")));
    }

    @Test
    public void test2() {
        List<String> wordDict = Arrays.asList("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 188; i++) {
            sb.append('a');
        }
        sb.append('b');
        Assert.assertFalse(wordBreak(sb.toString(), wordDict));
    }

    /**
     * dp[i]=dp[j]&&check(s[j..i])
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                }
            }
        }
        return dp[s.length()];
    }
}
