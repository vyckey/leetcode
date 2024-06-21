package problem;

import static java.lang.StringTemplate.STR;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class P316RemoveDuplicateLetters {
    @Test
    public void test() {
        Assert.assertEquals("abcd", removeDuplicateLetters("abababcdd"));
        Assert.assertEquals("abc", removeDuplicateLetters("bcabc"));
        Assert.assertEquals("acdb", removeDuplicateLetters("cbacdcbc"));
        Assert.assertEquals("abcf", removeDuplicateLetters("abcacf"));
    }

    /**
     * 单调栈
     */
    public String removeDuplicateLetters(String s) {
        // 对所有字符进行一次个数统计
        Map<Character, Integer> counts = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            counts.put(ch, counts.getOrDefault(ch, 0) + 1);
        }

        Set<Character> visited = new HashSet<>();
        Deque<Character> deque = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            // 确保counts为s[i+1..len]子串的字符计数
            counts.put(ch, counts.get(ch) - 1);

            // stack上有重复字符就跳过了
            if (visited.contains(ch)) {
                continue;
            }

            while (!deque.isEmpty() && deque.peek() > ch) {
                // 只有i位置后面还有同样字符的时候才删除
                if (counts.get(deque.peek()) > 0) {
                    visited.remove(deque.pollFirst());
                } else {
                    break;
                }
            }
            deque.offerFirst(ch);
            visited.add(ch);
        }
        
        StringBuilder sb = new StringBuilder();
        while (!deque.isEmpty()) {
            sb.append(deque.pollLast());
        }
        return sb.toString();
    }
}
