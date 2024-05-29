package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class P49GroupAnagrams {
    @Test
    public void test() {
        String[][] testCases = new String[][]{
            new String[]{"eat", "tea", "tan", "ate", "nat", "bat"},
            new String[]{"aab", "abb"},
            new String[]{"a"},
            new String[]{""}
        };
        for (String[] testCase : testCases) {
            List<List<String>> resultList1 = groupAnagrams(testCase);
            List<List<String>> resultList2 = groupAnagrams(testCase);
            System.out.println(resultList2);
            Assert.assertEquals(resultList1.size(), resultList2.size());
            for (List<String> strs : resultList2) {
                Set<String> set = strs.stream().map(P49GroupAnagrams::sortString).collect(Collectors.toSet());
                Assert.assertEquals(1, set.size());
            }
        }
    }

    private static String sortString(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    /**
     * 比较简单的方式：
     * 对string本身字符重排序，然后用哈希表记录重排序字符串的原字符串位置
     */
    public List<List<String>> groupAnagramsSimple(String[] strs) {
        Map<String, List<Integer>> hashtable = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            hashtable.computeIfAbsent(sortString(str), s -> new ArrayList<>())
                .add(i);
        }
        return hashtable.values().stream()
            .map(indices -> indices.stream().map(idx -> strs[idx]).collect(Collectors.toList()))
            .collect(Collectors.toList());
    }

    /**
     * 对每个字符串的字符进行分桶统计数量，然后用哈希表存储
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<WrapString, List<Integer>> hashtable = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            hashtable.computeIfAbsent(new WrapString(strs[i]), s -> new ArrayList<>())
                .add(i);
        }
        return hashtable.values().stream()
            .map(indices -> indices.stream().map(idx -> strs[idx]).collect(Collectors.toList()))
            .collect(Collectors.toList());
    }

    static class WrapString {
        String string;
        int[] charCounts;

        WrapString(String string) {
            this.string = string;
            this.charCounts = new int[52];
            for (int i = 0; i < string.length(); i++) {
                int offset = ((int)string.charAt(i)) - ((int)'a');
                charCounts[offset]++;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (o instanceof WrapString) {
                WrapString ws = (WrapString) o;
                return Arrays.equals(this.charCounts, ws.charCounts);
            }
            return false;
        }

        @Override
        public int hashCode() {
            int result = 1;
            for (int cnt : charCounts) {
                result = result * 31 + cnt;
            }
            return result;
        }

        @Override
        public String toString() {
            return string.toString();
        }
    }
}
