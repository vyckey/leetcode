package problem;

import org.junit.Assert;
import org.junit.Test;

import common.ListNode;

public class P234PalindromeLinkedList {
    @Test
    public void test() {
        Assert.assertTrue(isPalindrome(ListNode.buildList(new int[]{1,2,2,1})));
        Assert.assertTrue(isPalindrome(ListNode.buildList(new int[]{1,2,5,2,1})));
        Assert.assertFalse(isPalindrome(ListNode.buildList(new int[]{1,2})));
    }

    public boolean isPalindrome(ListNode head) {
        int len = length(head);

        // 翻转前半部分链表
        int mid = len >> 1;
        ListNode node = head, prev = null;
        while (mid > 0) {
            ListNode next = node.next;
            node.next = prev;
            prev = node;
            node = next;
            mid--;
        }
        if ((len & 1) == 1) {
            node = node.next;
        }
        // 对两段链表进行对比
        while (prev != null) {
            if (prev.val != node.val) {
                return false;
            }
            prev = prev.next;
            node = node.next;
        }
        return true;
    }

    private int length(ListNode head) {
        int len = 0;
        ListNode node = head;
        while (node != null) {
            ++len;
            node = node.next;
        }
        return len;
    }
}
