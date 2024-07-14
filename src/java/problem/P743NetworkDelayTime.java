package problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

public class P743NetworkDelayTime {
    @Test
    public void test() {
        Assert.assertEquals(1, networkDelayTime(new int[][]{{1,2,1}}, 2, 1));
        Assert.assertEquals(-1, networkDelayTime(new int[][]{{1,2,1}}, 2, 2));
        Assert.assertEquals(2, networkDelayTime(new int[][]{{2,1,1},{2,3,1},{3,4,1}}, 4, 2));
    }

    private static final int INFINITY_MAX = Integer.MAX_VALUE;

    /**
     * Dijistra 算法
     */
    public int networkDelayTime(int[][] times, int n, int k) {
        Map<Integer, Node> nodeMap = new HashMap<>(n);
        for (int i = 1; i <= n; i++) {
            nodeMap.put(i, new Node(i, i == k ? 0 : INFINITY_MAX));
        }
        for (int[] edge : times) {
            int id = edge[0];
            nodeMap.get(id).edges.add(new int[]{edge[1], edge[2]});
        }

        int maxTime = 0;
        Set<Integer> nodeIds = new HashSet<>(n);
        TreeSet<Node> remainNodes = new TreeSet<>();
        nodeMap.values().forEach(node -> remainNodes.add(node));
        while (!remainNodes.isEmpty()) {
            Node node = remainNodes.pollFirst();
            if (node.pathLen == INFINITY_MAX) {
                break;
            }
            nodeIds.add(node.id);
            maxTime = Math.max(maxTime, node.pathLen);

            for (int[] edge : node.edges) {
                Node nextNode = nodeMap.get(edge[0]);
                if (node.pathLen + edge[1] < nextNode.pathLen) {
                    remainNodes.remove(nextNode);
                    nextNode.pathLen = node.pathLen + edge[1];
                    remainNodes.add(nextNode);
                }
            }
        }
        return nodeIds.size() == n ? maxTime : -1;
    }

    static class Node implements Comparable<Node> {
        final int id;
        int pathLen;
        List<int[]> edges;

        Node(int id, int pathLen) {
            this.id = id;
            this.pathLen = pathLen;
            this.edges = new ArrayList<>();
        }

        @Override
        public int compareTo(Node node) {
            int c = Integer.compare(pathLen, node.pathLen);
            if (c == 0) {
                c = Integer.compare(id, node.id);
            }
            return c;
        }

        @Override
        public String toString() {
            return "Node{id=" + id + ", pathLen="+pathLen+"}";
        }
    }
}
