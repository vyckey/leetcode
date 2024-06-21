package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class P17LetterCombinationsOfAPhoneNumber {
    @Test
    public void test() {
        Assert.assertEquals(new ArrayList<>(), letterCombinations(""));
        Assert.assertEquals(Arrays.asList("a", "b", "c"), letterCombinations("2"));
        Assert.assertEquals(Arrays.asList("ad","ae","af","bd","be","bf","cd","ce","cf"), letterCombinations("23"));
    }

    private static final String[] ALPHABET = new String[]{"", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuilder(), digits, 0);
        return result;
    }

    private void backtrack(List<String> result, StringBuilder prefix, String digits, int index) {
        if (index >= digits.length()) {
            if (prefix.length() > 0) {
                result.add(prefix.toString());
            }
            return;
        }
        int digit = digits.charAt(index) - '0';
        String str = ALPHABET[digit - 1];
        for (int i = 0; i < str.length(); i++) {
            prefix.append(str.charAt(i));
            backtrack(result, prefix, digits, index + 1);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}