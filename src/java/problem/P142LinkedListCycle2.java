package problem;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import common.ListNode;

public class P142LinkedListCycle2 {
    private ListNode buildList(int[] vals) {
        int pos = vals[vals.length - 1];
        ListNode list = ListNode.buildList(Arrays.copyOfRange(vals, 0, vals.length - 1));
        ListNode node = list.forward(pos);
        list.tail().next = node;
        return list;
    }

    @Test
    public void test() {
        ListNode cycle = detectCycle(buildList(new int[]{3,2,0,-4,1}));
        Assert.assertEquals(2, cycle.val);
    }

    /**
     * 快慢指针
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != null && fast != null) {
            slow = slow.next;
            fast = fast.next;
            if (fast != null) {
                fast = fast.next;
            }
            if (slow == fast) {
                ListNode ptr = head;
                slow = slow.next;
                while (slow != ptr) {
                    slow = slow.next;
                    ptr = ptr.next;
                }
                return ptr;
            }
        }
        return null;
    }
}