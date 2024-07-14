package problem;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

public class P32LongestValidParentheses {
    @Test
    public void test() {
        Assert.assertEquals(0, longestValidParentheses("("));
        Assert.assertEquals(4, longestValidParentheses("()()"));
        Assert.assertEquals(4, longestValidParentheses(")()())"));
        Assert.assertEquals(8, longestValidParentheses(")(()()())("));
    }

    /**
     * 使用栈结构
     */
    public int longestValidParentheses(String s) {
        int longest = 0;
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (stack.isEmpty() || ch == '(') {
                stack.push(i);
            } else {
                if (s.charAt(stack.peek()) == '(') {
                    stack.pop();
                    int len = i - (stack.isEmpty() ? -1 : stack.peek());
                    longest = Math.max(longest, len);
                } else {
                    stack.push(i);
                }
            }
        }
        return longest;
    }
}
