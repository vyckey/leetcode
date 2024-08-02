package problem;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import common.ListNode;

public class P25ReverseNodesInKGroup {
    @Test
    public void test() {
        Assert.assertEquals(Arrays.asList(1,2,3,4), reverseKGroup(ListNode.buildList(new int[]{1,2,3,4}), 1).values());
        Assert.assertEquals(Arrays.asList(2,1,4,3,5), reverseKGroup(ListNode.buildList(new int[]{1,2,3,4,5}), 2).values());
        Assert.assertEquals(Arrays.asList(3,2,1,4,5), reverseKGroup(ListNode.buildList(new int[]{1,2,3,4,5}), 3).values());
        Assert.assertEquals(Arrays.asList(3,2,1,6,5,4,7,8), reverseKGroup(ListNode.buildList(new int[]{1,2,3,4,5,6,7,8}), 3).values());
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode hNode = head, newHead = null, prevTail = null;
        while (hNode != null) {
            int c = 1;
            ListNode node = hNode;
            while (node != null && c < k) {
                node = node.next;
                c++;
            }
            if (node == null) {
                break;
            }
            // 下一段k个节点的head节点
            ListNode nextHNode = null;
            if (node != null && node.next != null) {
                nextHNode = node.next;
                node.next = null;
            }
            ListNode newHNode = reverse(hNode);
            if (prevTail == null) {
                newHead = newHNode;
            } else {
                prevTail.next = newHNode;
            }
            hNode.next = nextHNode;

            prevTail = hNode;
            hNode = nextHNode;
        }
        return newHead;
    }

    /**
     * 翻转链表，返回新的head
     */
    private ListNode reverse(ListNode head) {
        ListNode newHead = null;
        ListNode node = head, prev = null;
        while (node != null) {
            ListNode next = node.next;
            if (next == null) {
                newHead = node;
            }
            node.next = prev;
            prev = node;
            node = next;
        }
        return newHead;
    }
}
