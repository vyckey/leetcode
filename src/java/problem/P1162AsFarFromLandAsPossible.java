package problem;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Test;

public class P1162AsFarFromLandAsPossible {
    @Test
    public void test() {
        int[][] grid = new int[100][100];
        grid[0][0]=1;
        System.out.println(new BFSSolution().maxDistance(grid));
    }

    private static class BFSSolution {
        private static final int[][] DIRECTIONS = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};

        public int maxDistance(int[][] grid) {
            final int n = grid.length;
            int maxDist = -1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 0) {
                        int dist = bfs(grid, n, i, j);
                        maxDist = Math.max(maxDist, dist);
                    }
                }
            }
            return maxDist;
        }

        private int bfs(int[][] grid, int n, int i, int j) {
            Deque<int[]> deque = new LinkedList<>();
            Set<Integer> visited = new HashSet<>();

            deque.offer(new int[]{i, j, 0});
            visited.add(i * n + j);
            while (!deque.isEmpty()) {
                int[] pos = deque.poll();
                int x = pos[0], y = pos[1], dist = pos[2];
                for (int[] dir : DIRECTIONS) {
                    int x2 = x + dir[0], y2 = y + dir[1];
                    if (x2 < 0 || x2 >= n || y2 < 0 || y2 >= n) {
                        continue;
                    }
                    if (!visited.add(x2 * n + y2)) {
                        continue;
                    }
                    if (grid[x2][y2] == 1) {
                        return dist + 1;
                    }
                    deque.offer(new int[]{x2, y2, dist + 1});
                }
            }
            return -1;
        }
    }
}
