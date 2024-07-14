package problem;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import common.ListNode;

public class P86PartitionList {
    @Test
    public void test() {
        Assert.assertEquals(Arrays.asList(1,2,2,4,3,5), partition(ListNode.buildList(new int[]{1,4,3,2,5,2}), 3).values());
        Assert.assertEquals(Arrays.asList(1,2), partition(ListNode.buildList(new int[]{2,1}), 2).values());
        Assert.assertEquals(Arrays.asList(1,1), partition(ListNode.buildList(new int[]{1,1}), 2).values());
    }

    public ListNode partition(ListNode head, int x) {
        ListNode newHead = new ListNode(0), smallTail = newHead;
        smallTail.next = head;

        ListNode prev = smallTail, node = head;
        while (node != null && node.val < x) {
            smallTail = smallTail.next;
            prev = node;
            node = node.next;
        }

        while (node != null) {
            ListNode next = node.next;
            if (node.val < x) {
                // remove node
                prev.next = next;
                // insert node
                node.next = smallTail.next;
                smallTail.next = node;
                smallTail = node;
            } else {
                prev = node;
            }
            node = next;
        }
        return newHead.next;
    }
}
