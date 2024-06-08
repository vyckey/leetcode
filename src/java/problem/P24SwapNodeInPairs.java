package problem;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import common.ListNode;

public class P24SwapNodeInPairs {
    @Test
    public void test() {
        Assert.assertNull(swapPairs(null));
        ListNode list = ListNode.buildList(new int[]{1,2});
        Assert.assertEquals(list.next, swapPairs(list.next));
        Assert.assertEquals(Arrays.asList(2,1), swapPairs(list).values());

        ListNode list2 = ListNode.buildList(new int[]{2,1,4,3,6,5,8,7,9});
        Assert.assertEquals(Arrays.asList(1,2,3,4,5,6,7,8,9), swapPairs(list2).values());
    }

    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode prevNode = null, node = head;
        head = head.next;
        while (node != null && node.next != null) {
            ListNode nextNode = node.next;
            ListNode nnextNode = nextNode.next;
            if (prevNode != null) {
                prevNode.next = nextNode;
            }
            nextNode.next = node;
            node.next = nnextNode;

            prevNode = node;
            node = nnextNode;
        }
        return head;
    }
}
