package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class P763PartitionLabels {
    @Test
    public void test() {
        Assert.assertEquals(Arrays.asList(9, 7, 8), partitionLabels("ababcbacadefegdehijhklij"));
        Assert.assertEquals(Arrays.asList(10), partitionLabels("eccbbbbdec"));
    }

    public List<Integer> partitionLabels(String s) {
        Map<Character, Integer> maxPosMap = new HashMap<>();
        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (!maxPosMap.containsKey(ch)) {
                maxPosMap.put(ch, i);
            }
        }
        
        List<Integer> lengths = new ArrayList<>();
        int from = 0;
        while (from < s.length()) {
            int end = from;
            for (int i = from; i <= end; i++) {
                int maxPos = maxPosMap.get(s.charAt(i));
                end = Math.max(end, maxPos);
            }
            lengths.add(end - from + 1);
            from = end + 1;
        }
        return lengths;
    }
}
