package problem;

import org.junit.Assert;
import org.junit.Test;

public class P1702MaximumBinaryStringAfterChange {
    @Test
    public void test() {
        Assert.assertEquals("01", maximumBinaryString("01"));
        Assert.assertEquals("10", maximumBinaryString("10"));
        Assert.assertEquals("111011", maximumBinaryString("000110"));
        Assert.assertEquals("1110", maximumBinaryString("1100"));
    }

    /**
     * 贪心算法
     */
    public String maximumBinaryString(String binary) {
        char[] chars = binary.toCharArray();
        int j = 0;
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == '0') {
                while (j <= i || j < chars.length && chars[j] == '1') {
                    j++;
                }
                if (j < chars.length) {
                    chars[i] = '1';
                    chars[i + 1] = '0';
                    if (j > i + 1) {
                        chars[j] = '1';
                    }
                }
            }
        }
        return new String(chars);
    }
}
