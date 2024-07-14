package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class P138CopyListWithRandomPointer {
    private static boolean deepEquals(Node node1, Node node2) {
        while (node1 != null && node2 != null) {
            if (node1.val != node2.val) {
                return false;
            }
            if (node1.random == null && node2.random == null) {
                node1 = node1.next;
                node2 = node2.next;
                continue;
            }
            if (node1.random == null || node2.random == null) {
                return false;
            }
            if (node1.random.val != node2.random.val) {
                return false;
            }
            node1 = node1.next;
            node2 = node2.next;
        }
        return node1 == null && node2 == null;
    }

    @Test
    public void test() {
        Node node1 = Node.build(Arrays.asList(new Integer[]{7,null},new Integer[]{13,0},new Integer[]{11,4},new Integer[]{10,2},new Integer[]{1,0}));
        Assert.assertTrue(deepEquals(node1, copyRandomList(node1)));

        Node node2 = Node.build(Arrays.asList(new Integer[]{1,1},new Integer[]{2,1}));
        Assert.assertTrue(deepEquals(node2, copyRandomList(node2)));

        Node node3 = Node.build(Arrays.asList(new Integer[]{3,null},new Integer[]{3,0},new Integer[]{3,null}));
        Assert.assertTrue(deepEquals(node3, copyRandomList(node3)));
    }

    /**
     * O(n)时间复杂度，O(1)空间复杂度的算法
     */
    public Node copyRandomList(Node head) {
        // 从原始链表中插入新节点
        // A->B->C
        // A->A'->B->B'->C->C'
        Node node = head;
        while (node != null) {
            Node next = node.next;
            // insert newNode behind current node
            Node newNode = new Node(node.val);
            node.next = newNode;
            newNode.next = next;

            node = next;
        }

        // 赋值新节点的random属性
        node = head;
        while (node != null) {
            Node newNode = node.next;
            if (node.random != null) {
                Node newRandom = node.random.next;
                newNode.random = newRandom;
            }
            node = newNode.next;
        }

        // 从原链表中删除新节点
        Node newHead = null, prevNode = null;
        node = head;
        while (node != null) {
            Node newNode = node.next;
            node.next = newNode.next;
            node = newNode.next;

            if (prevNode != null) {
                prevNode.next = newNode;
            } else {
                newHead = newNode;
            }
            prevNode = newNode;
        }
        return newHead;
    }

    /**
     * 哈希表
     */
    public Node copyRandomList2(Node head) {
        Node newHead = new Node(-1);
        Map<Node, Node> old2NewMap = new HashMap<>();
        Node oldNode = head, newNode = newHead; 
        while (oldNode != null) {
            Node node = new Node(oldNode.val);
            newNode.next = node;
            newNode = node;
            old2NewMap.put(oldNode, newNode);
            oldNode = oldNode.next;
        }
        newHead = newHead.next;

        oldNode = head;
        newNode = newHead;
        while (oldNode != null) {
            Node random = old2NewMap.get(oldNode.random);
            newNode.random = random;
            oldNode = oldNode.next;
            newNode = newNode.next;
        }
        return newHead;
    }

    private static class Node {
        int val;
        Node next;
        Node random;
    
        Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }

        static Node build(List<Integer[]> list) {
            List<Node> nodes = new ArrayList<>(list.size());
            Node prevNode = null;
            for (Integer[] arr : list) {
                Node node = new Node(arr[0]);
                nodes.add(node);
                if (prevNode != null) {
                    prevNode.next = node;
                }
                prevNode = node;
            }
            for (int i = 0; i < list.size(); i++) {
                Node node = nodes.get(i);
                Integer[] arr = list.get(i);
                if (arr[1] != null && 0 <= arr[1] && arr[1] < list.size()) {
                    Node random = nodes.get(arr[1]);
                    node.random = random;
                }
            }
            return nodes.isEmpty() ? null : nodes.get(0);
        }
    }
}
