package problem;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import util.ArrayUtil;

public class P994RottingOranges {
    @Test
    public void test() {
        Assert.assertEquals(-1, orangesRotting(ArrayUtil.partition(2, 
            0, 1)));
        
        Assert.assertEquals(0, orangesRotting(ArrayUtil.partition(2, 
            0, 2)));

        Assert.assertEquals(4, orangesRotting(ArrayUtil.partition(3, 
            2,1,1,
            1,1,0,
            0,1,1)));

        Assert.assertEquals(-1, orangesRotting(ArrayUtil.partition(3, 
            2,1,1,
            0,1,1,
            1,0,1)));
    }

    public int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int freshCount = 0;
        List<Integer> rottingOranges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    freshCount++;
                } else if (grid[i][j] == 2) {
                    rottingOranges.add(i * n + j);
                }
            }
        }
        int minutes = 0;
        while (!rottingOranges.isEmpty()) {
            List<Integer> handlingOranges = new ArrayList<>(rottingOranges);
            rottingOranges.clear();
            for (int pos : handlingOranges) {
                int i = pos / n, j = pos % n;
                if (i > 0 && grid[i - 1][j] == 1) {
                    rottingOranges.add(pos - n);
                    grid[i - 1][j] = 2;
                }
                if (i + 1 < m && grid[i + 1][j] == 1) {
                    rottingOranges.add(pos + n);
                    grid[i + 1][j] = 2;
                }
                if (j > 0 && grid[i][j - 1] == 1) {
                    rottingOranges.add(pos - 1);
                    grid[i][j - 1] = 2;
                }
                if (j + 1 < n && grid[i][j + 1] == 1) {
                    rottingOranges.add(pos + 1);
                    grid[i][j + 1] = 2;
                }
            }
            if (!rottingOranges.isEmpty()) {
                freshCount -= rottingOranges.size();
                ++minutes;
            }
        }
        return freshCount == 0 ? minutes : -1;
    }
}
