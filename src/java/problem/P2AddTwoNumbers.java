package problem;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import common.ListNode;
import util.RandomUtil;

public class P2AddTwoNumbers {
    private static ListNode buildList(int val) {
        if (val <= 0) {
            return new ListNode(val);
        }

        ListNode head = new ListNode(-1), node = head;
        while (val > 0) {
            node.next = new ListNode(val % 10);
            node = node.next;
            val /= 10;
        }
        return head.next;
    }

    @Test
    public void test1() {
        Assert.assertEquals(Arrays.asList(0), addTwoNumbers(buildList(0), buildList(0)).values());
        Assert.assertEquals(Arrays.asList(7, 0, 8), addTwoNumbers(buildList(342), buildList(465)).values());
        Assert.assertEquals(Arrays.asList(8,9,9,9,0,0,0,1), addTwoNumbers(buildList(9999999), buildList(9999)).values());
    }

    @Test
    public void test2() {
        for (int i = 0; i < 100; i++) {
            int i1 = RandomUtil.nextInt(0, 100000), i2 = RandomUtil.nextInt(0, 100000);
            int s = i1 + i2;
            Assert.assertEquals(buildList(s).values(), addTwoNumbers(buildList(i1), buildList(i2)).values());
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-1), node = head;
        int add = 0;
        while (l1 != null && l2 != null) {
            int sum = l1.val + l2.val + add;
            add = sum / 10;
            node.next = new ListNode(sum % 10);
            node = node.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        ListNode l3 = (l1 != null) ? l1 : l2;
        while (l3 != null) {
            int sum = l3.val + add;
            add = sum / 10;
            node.next = new ListNode(sum % 10);
            node = node.next;
            l3 = l3.next;
        }
        if (add > 0) {
            node.next = new ListNode(add);
        }
        return head.next;
    }
}
