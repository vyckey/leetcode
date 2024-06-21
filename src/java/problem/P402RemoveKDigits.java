package problem;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

public class P402RemoveKDigits {
    @Test
    public void test() {
        Assert.assertEquals("1219", removeKdigits("1432219", 3));
        Assert.assertEquals("200", removeKdigits("10200", 1));
        Assert.assertEquals("0", removeKdigits("10", 2));
        Assert.assertEquals("0", removeKdigits("10001", 4));
    }

    /**
     * 单调栈
     */
    public String removeKdigits(String num, int k) {
        int delCount = 0;
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < num.length(); i++) {
            while (delCount < k && !deque.isEmpty() && num.charAt(deque.peek()) > num.charAt(i)) {
                deque.pop();
                delCount++;
            }
            deque.push(i);
        }
        for (; delCount < k; delCount++) {
            deque.pop();
        }
        

        char[] chars = new char[deque.size()];
        for (int i = chars.length - 1; i >= 0; i--) {
            chars[i] = num.charAt(deque.pop());
        }
        return toString(chars);
    }

    private String toString(char[] chars) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (sb.length() == 0 && chars[i] == '0') {
                continue;
            }
            sb.append(chars[i]);
        }
        if (sb.length() == 0) {
            sb.append('0');
        }
        return sb.toString();
    }
}
