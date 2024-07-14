package problem;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class P76MinimumWindowSubstring {
    @Test
    public void test() {
        Assert.assertEquals("a", minWindow("a", "a"));
        Assert.assertEquals("", minWindow("a", "aa"));
        Assert.assertEquals("BANC", minWindow("ADOBECODEBANC", "ABC"));
    }

    /**
     * 双指针
     */
    public String minWindow(String s, String t) {
        Map<Character, Integer> charCounts = countChars(t);

        int notFoundChars = t.length();
        int from = -1, len = Integer.MAX_VALUE;
        int i = 0, j = 0;
        while (i < s.length() && j < s.length()) {
            // 往右边找直到找到所有字符
            while (notFoundChars > 0 && j < s.length()) {
                char ch = s.charAt(j);
                if (charCounts.containsKey(ch)) {
                    charCounts.put(ch, charCounts.get(ch) - 1);
                    if (charCounts.get(ch) >= 0) {
                        --notFoundChars;
                    }
                }
                if (notFoundChars == 0) {
                    break;
                } else {
                    j++;
                }
            }
            // 尽可能去掉左边无效的字符
            while (i < j && notFoundChars == 0){
                char ch = s.charAt(i);
                if (charCounts.containsKey(ch)) {
                    charCounts.put(ch, charCounts.get(ch) + 1);
                    if (charCounts.get(ch) > 0) {
                        ++notFoundChars;
                    }
                }
                if (notFoundChars > 0) {
                    break;
                } else {
                    i++;
                }
            }
            // 保留长度更小的子串
            if (j < s.length() && (j - i + 1) < len) {
                from = i;
                len = j - i + 1;
            }
            j++;
            i++;
        }
        return from >= 0 ? s.substring(from, from + len) : "";
    }

    private Map<Character, Integer> countChars(String t) {
        Map<Character, Integer> charCounts = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            charCounts.put(ch, charCounts.getOrDefault(ch, 0) + 1);
        }
        return charCounts;
    }
}
