package problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class P433MinimumGeneticMutation {
    @Test
    public void test() {
        Solution1 solution = new Solution1();
        Assert.assertEquals(1, solution.minMutation("AACCGGTT", "AACCGGTA", new String[]{"AACCGGTA"}));
        Assert.assertEquals(2, solution.minMutation("AACCGGTT", "AAACGGTA", new String[]{"AACCGGTA","AACCGCTA","AAACGGTA"}));
    }

    private static class Solution1 {
        public int minMutation(String startGene, String endGene, String[] bank) {
            Map<Integer, List<Integer>> graph = new HashMap<>();
            int end = -1;
            for (int i = 0; i < bank.length; i++) {
                if (endGene.equals(bank[i])) {
                    end = i;
                    break;
                }
            }
            for (int i = -1; i < bank.length - 1; i++) {
                String gene1 = (i == -1) ? startGene : bank[i];
                for (int j = i + 1; j < bank.length; j++) {
                    String gene2 = bank[j];
                    if (mutable(gene1, gene2)) {
                        graph.computeIfAbsent(i, a -> new ArrayList<>()).add(j);
                        graph.computeIfAbsent(j, a -> new ArrayList<>()).add(i);
                    }
                }
            }
            return end < 0 ? -1 : minMutation(graph, end);
        }

        private boolean mutable(String gene1, String gene2) {
            int step = 0;
            for (int i = 0; i < gene1.length(); i++) {
                if (gene1.charAt(i) != gene2.charAt(i)) {
                    step++;
                }
                if (step > 1) {
                    break;
                }
            }
            return step == 1;
        }

        private int minMutation(Map<Integer, List<Integer>> graph, int target) {
            int minPath = dfs(graph, new HashSet<>(), -1, target);
            return minPath == Integer.MAX_VALUE ? -1 : minPath;
        }

        private int dfs(Map<Integer, List<Integer>> graph, Set<Integer> visited, int node, int target) {
            if (visited.contains(node)) {
                return Integer.MAX_VALUE;
            }
            if (node == target) {
                return 0;
            }

            visited.add(node);
            int minPath = Integer.MAX_VALUE;
            List<Integer> children = graph.get(node);
            if (children != null && !children.isEmpty()) {
                for (int child : children) {
                    minPath = Math.min(minPath, dfs(graph, visited, child, target));
                }
            }
            visited.remove(node);
            if (minPath != Integer.MAX_VALUE) {
                minPath += 1;
            }
            return minPath;
        }
    }
}
