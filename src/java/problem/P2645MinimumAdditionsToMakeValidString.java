package problem;

import org.junit.Assert;
import org.junit.Test;

public class P2645MinimumAdditionsToMakeValidString {
    @Test
    public void test() {
        Assert.assertEquals(2, addMinimum("b"));
        Assert.assertEquals(6, addMinimum("aaa"));
        Assert.assertEquals(0, addMinimum("abc"));
    }

    public int addMinimum(String word) {
        int ans = 0;
        int i = 0;
        while (i < word.length()) {
            int j = i + 1;
            while (j < word.length() && word.charAt(j) > word.charAt(j - 1)) {
                j++;
            }
            ans += (3 - (j - i));
            i = j;
        }
        return ans;
    }
}