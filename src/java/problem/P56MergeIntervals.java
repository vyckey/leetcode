package problem;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import util.ArrayUtil;

public class P56MergeIntervals {
    @Test
    public void test() {
        Assert.assertTrue(ArrayUtil.deepEquals(new int[][]{
            {1,6},{8,10},{15,18}
        }, merge(new int[][]{
            {1,3},{2,6},{8,10},{15,18}
        })));
        
        Assert.assertTrue(ArrayUtil.deepEquals(new int[][]{
            {1,5}
        }, merge(new int[][]{
            {1,4},{4,5}
        })));

        Assert.assertTrue(ArrayUtil.deepEquals(new int[][]{
            {0,5}
        }, merge(new int[][]{
            {1,4},{0,2},{3,5}
        })));
    }

    public int[][] merge(int[][] intervals) {
        List<int[]> list = new LinkedList<>();
        for (int[] interval : intervals) {
            list.add(interval);
        }
        Collections.sort(list, Comparator.comparingInt(arr -> arr[0]));

        int[] last = null;
        Iterator<int[]> iterator = list.iterator();
        while (iterator.hasNext()) {
            int[] current = iterator.next();
            if (last != null && couldMerge(last, current)) {
                iterator.remove();
                last[0] = Math.min(last[0], current[0]);
                last[1] = Math.max(last[1], current[1]);
            } else {
                last = current;
            }
        }
        return list.toArray(new int[0][0]);
    }

    private boolean couldMerge(int[] interval1, int[] interval2) {
        return !(interval1[1] < interval2[0] || interval2[1] < interval1[0]);
    }
}
