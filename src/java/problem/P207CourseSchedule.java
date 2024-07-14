package problem;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class P207CourseSchedule {
    @Test
    public void test() {
        Assert.assertTrue(new Solution1().canFinish(2, new int[][]{{1,0}}));
        Assert.assertFalse(new Solution1().canFinish(2, new int[][]{{1,0},{0,1}}));
        Assert.assertTrue(new Solution1().canFinish(2, new int[][]{{0,1}}));
        Assert.assertTrue(new Solution1().canFinish(5, new int[][]{{1,4},{2,4},{3,1},{3,2}}));
    }

    private static class Solution1 {
        private boolean hasCircle;

        /**
         * DFS
         */
        public boolean canFinish(int numCourses, int[][] prerequisites) {
            Map<Integer, List<Integer>> graph = new HashMap<>(numCourses);
            for (int[] edge : prerequisites) {
                graph.computeIfAbsent(edge[1], i -> new ArrayList<>()).add(edge[0]);
            }

            Set<Integer> visited = new HashSet<>();
            for (int i = 0; i < numCourses; i++) {
                if (visited.contains(i)) {
                    continue;
                }

                travel(graph, i, visited, new HashSet<>());
                if (hasCircle) {
                    return false;
                }
            }
            return true;
        }

        private void travel(Map<Integer, List<Integer>> graph, int node, Set<Integer> visited, Set<Integer> path) {
            if (path.contains(node)) {
                hasCircle = true;
            }
            if (visited.contains(node)) {
                return;
            }

            visited.add(node);
            path.add(node);
            List<Integer> children = graph.get(node);
            if (children != null && !children.isEmpty()) {
                for (int child : children) {
                    travel(graph, child, visited, path);
                }
            }
            path.remove(node);
        }
    }

    class Solution2 {
        /**
         * 拓扑排序
         */
        public boolean canFinish(int numCourses, int[][] prerequisites) {
            // 构建图结构
            Node[] nodes = new Node[numCourses];
            for (int i = 0; i < numCourses; i++) {
                nodes[i] = new Node(i);
            }
            for (int[] edge : prerequisites) {
                Node node = nodes[edge[1]];
                Node nextNode = nodes[edge[0]];
                node.children.add(nextNode);
                nextNode.indegree++;
            }
            // System.out.println(Arrays.stream(nodes).map(Node::toString).collect(Collectors.joining(", ")));

            // 使用拓扑排序
            Deque<Node> queue = new LinkedList<>();
            for (Node node : nodes) {
                if (node.indegree == 0) {
                    queue.offer(node);
                }
            }

            int deleteCount = 0;
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                deleteCount++;
                for (Node child : node.children) {
                    if (--child.indegree == 0) {
                        queue.offer(child);
                    }
                }
            }
            return deleteCount == numCourses;
        }

        static class Node {
            private final int value;
            private final List<Node> children = new ArrayList<>();
            private int indegree;

            Node(int value) {
                this.value = value;
            }

            @Override
            public String toString() {
                String childrenStr = children.stream().map(n -> n.value).map(String::valueOf)
                    .collect(Collectors.joining(",", "(", ")"));
                return "Node{val=" + value + ", children=" + childrenStr + "}";
            }
        }
    }
}
