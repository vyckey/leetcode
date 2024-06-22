package problem;

import org.junit.Assert;
import org.junit.Test;

import common.ListNode;
import util.RandomUtil;

public class P160IntersectionOfTwoLinkedList {
    @Test
    public void test1() {
        ListNode headB = ListNode.buildList(new int[]{5,6,1,4,1,8,4,5});
        ListNode headA = headB.forward(3);
        ListNode intersection = headA.forward(2);
        headB.forward(2).next = intersection;
        Assert.assertEquals(intersection, getIntersectionNode(headA, headB));
    }

    @Test
    public void test2() {
        for (int i = 0; i < 100; i++) {
            int[] nums = new int[RandomUtil.nextInt(2, 3 * 10 ^ 4)];
            for (int j = 0; j < nums.length; j++) {
                nums[j] = RandomUtil.nextInt();
            }
            ListNode headB = ListNode.buildList(nums);
            int forward = RandomUtil.nextInt(0, nums.length - 1);
            ListNode headA = headB.forward(forward);
            ListNode intersection;
            if (forward > 0) {
                intersection = headA.forward(RandomUtil.nextInt(0, nums.length - forward - 1));
                headB.forward(forward - 1).next = intersection;
            } else {
                intersection = headA;
            }
            Assert.assertEquals(intersection, getIntersectionNode(headA, headB));
        }
    }
    
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode node1 = headA, node2 = headB;
        int lenA = length(headA);
        int lenB = length(headB);
        int diffLen = Math.abs(lenA - lenB);
        if (diffLen > 0) {
            int step = 0;
            while (step++ < diffLen) {
                if (lenA > lenB) {
                    node1 = node1.next;
                } else {
                    node2 = node2.next;
                }
            }
        }

        while (node1 != null && node1 != node2) {
            node1 = node1.next;
            node2 = node2.next;
        }
        return node1;
    }

    private int length(ListNode head) {
        int length = 0;
        ListNode node = head;
        while (node != null) {
            ++length;
            node = node.next;
        }
        return length;
    }
}
