package problem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import common.ListNode;
import util.RandomUtil;

public class P148SortList {
    @Test
    public void test() {
        final int testCaseSize = 100;
        for (int j = 0; j < testCaseSize; j++) {
            int[] nums = new int[RandomUtil.nextInt(1, 100)];
            for (int i = 0; i < nums.length; i++) {
                nums[i] = RandomUtil.nextInt(0, 200);
            }
            ListNode list = ListNode.buildList(nums);

            List<Integer> expect = Arrays.stream(nums).sorted().mapToObj(i -> i).collect(Collectors.toList());
            Assert.assertEquals(expect, sortList(list).values());
        }
    }

    public ListNode sortList(ListNode head) {
        return sortList(head, null);
    }

    /**
     * 分治算法
     */
    private ListNode sortList(ListNode start, ListNode end) {
        if (start == end || start.next == end) {
            return start;
        }

        ListNode start2 = partition(start, end);
        ListNode left = sortList(start, null);
        ListNode right = sortList(start2, end);
        
        ListNode head = new ListNode(0), node = head;
        while (left != null && right != null) {
            if (left.val <= right.val) {
                node.next = left;
                node = left;
                left = left.next;
            } else {
                node.next = right;
                node = right;
                right = right.next;
            }
        }
        if (left == null) {
            node.next = right;
        }
        if (right == null) {
            node.next = left;
        }
        return head.next;
    }

    private ListNode partition(ListNode start, ListNode end) {
        ListNode preSlow = null, slow = start, fast = start;
        while (fast != end) {
            preSlow = slow;
            slow = slow.next;
            fast = fast.next;
            if (fast != end) {
                fast = fast.next;
            }
        }
        // 拆分成两段不相连链表
        if (preSlow != null) {
            preSlow.next = null;
        }
        return slow;
    }
}
