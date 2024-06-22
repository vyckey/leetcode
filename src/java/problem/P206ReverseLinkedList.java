package problem;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import common.ListNode;
import util.ListUtil;
import util.RandomUtil;

public class P206ReverseLinkedList {
    @Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            final int size = RandomUtil.nextInt(0, 5000);
            List<Integer> nums = ListUtil.randomList(size, -5000, 5000);
            ListNode list = ListNode.buildList(nums.stream().mapToInt(x -> x).toArray());
            Collections.reverse(nums);
            Assert.assertEquals(nums, reverseList(list).values());
        }
    }

    public ListNode reverseList(ListNode head) {
        ListNode newHead = null;
        ListNode current = head;
        while (current != null) {
            ListNode next = current.next;
            current.next = newHead;
            newHead = current;
            current = next;
        }
        return newHead;
    }
}
