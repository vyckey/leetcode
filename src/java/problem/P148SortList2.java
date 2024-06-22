package problem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import common.ListNode;
import util.RandomUtil;

public class P148SortList2 {
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

    /**
     * 快速排序
     */
    public ListNode sortList(ListNode head) {
        return sortSubList(head, null);
    }

    private ListNode sortSubList(ListNode start, ListNode end) {
        if (start == end || start.next == end) {
            return start;
        }

        int pivot = start.val;
        ListNode prevIndexNode = start, indexNode = start.next;
        for (ListNode curNode = start.next; curNode != end; curNode = curNode.next) {
            if (curNode.val < pivot) {
                swapNode(indexNode, curNode);
                prevIndexNode = indexNode;
                indexNode = indexNode.next;
            }
        }
        
        indexNode = prevIndexNode;
        swapNode(start, indexNode);

        sortSubList(start, indexNode);
        sortSubList(indexNode.next, end);
        return start;
    }

    private static void swapNode(ListNode node1, ListNode node2) {
        if (node1 == node2) {
            return;
        }
        int tmp = node1.val;
        node1.val = node2.val;
        node2.val = tmp;
    }
}
