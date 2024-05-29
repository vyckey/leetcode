package problem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import common.ListNode;;

public class P141LinkedListCycle {
    private ListNode buildList(int[] vals) {
        int pos = vals[vals.length - 1];
        ListNode list = ListNode.buildList(Arrays.copyOfRange(vals, 0, vals.length - 1));
        ListNode node = list.forward(pos);
        list.tail().next = node;
        return list;
    }

    @Test
    public void test() {
        Assert.assertEquals(false, hasCycle(buildList(new int[]{1,-1})));
        Assert.assertEquals(true, hasCycle(buildList(new int[]{1,2,0})));
        Assert.assertEquals(true, hasCycle(buildList(new int[]{3,2,0,-4,1})));
    }

    /**
     * 哈希表
     */
    public boolean hasCycle1(ListNode head) {
        Set<ListNode> visitedNodes = new HashSet<>();
        ListNode node = head;
        while (node != null) {
            if (!visitedNodes.add(node)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    /**
     * 快慢指针
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && slow != null) {
            if (slow == fast) {
                return true;
            }
            slow = slow.next;
            fast = fast.next;
            if (fast != null) {
                fast = fast.next;
            }
        }
        return false;
    }

}