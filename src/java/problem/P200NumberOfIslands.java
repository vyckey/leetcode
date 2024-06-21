package problem;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class P200NumberOfIslands {
    private static char[][] buildGrid(String string) {
        String[] rows = string.split("\n");
        if (rows.length == 0) {
            throw new IllegalArgumentException("not found '\n' character");
        }
        int m = rows.length, n = rows[0].length();
        char[][] grid = new char[m][n];
        for (int i = 0; i < m; i++) {
            if (rows[i].length() != n) {
                throw new IllegalArgumentException("invalid length");
            }
            for (int j = 0; j < n; j++) {
                grid[i][j] = rows[i].charAt(j);
            }
        }
        return grid;
    }

    @Test
    public void test() {
        Assert.assertEquals(1, numIslands(buildGrid("11110\n11010\n11000\n00000")));
        Assert.assertEquals(3, numIslands(buildGrid("11000\n11000\n00100\n00011")));
    }

    public int numIslands(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        Set<Integer> checkingPositions = new HashSet<>();
        for (int i = 0; i < m * n; i++) {
            checkingPositions.add(i);
        }

        int numIslands = 0;
        while (!checkingPositions.isEmpty()) {
            int pos = checkingPositions.iterator().next();
            if (bfs(checkingPositions, grid, m, n, pos)) {
                numIslands += 1;
            }
        }
        return numIslands;
    }

    private char charAt(char[][] grid, int pos) {
        int n = grid[0].length;
        return grid[pos / n][pos % n];
    }

    private boolean bfs(Set<Integer> checkingPositions, char[][] grid, int m, int n, int pos) {
        char ch = charAt(grid, pos);
        checkingPositions.remove(pos);
        List<Integer> checking = new LinkedList<>();
        if (pos - n >= 0) {
            checking.add(pos - n);
        }
        if (pos + n < m * n) {
            checking.add(pos + n);
        }
        if ((pos % n) > 0) {
            checking.add(pos - 1);
        }
        if ((pos % n) < n - 1) {
            checking.add(pos + 1);
        }
        checking.removeIf(p -> charAt(grid, p) != ch || !checkingPositions.contains(p));
        
        checkingPositions.removeAll(checking);
        for (int p : checking) {
            bfs(checkingPositions, grid, m, n, p);
        }
        return ch == '1';
    }
}
