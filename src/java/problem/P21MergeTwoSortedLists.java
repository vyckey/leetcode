package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import common.ListNode;
import util.ArrayUtil;

public class P21MergeTwoSortedLists {
    @Test
    public void test1() {
        Assert.assertNull(mergeTwoLists(null, null));
        Assert.assertEquals(Arrays.asList(3,5,7), mergeTwoLists(null, ListNode.buildList(new int[]{3,5,7})).values());
    }

    @Test
    public void test2() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int[] arr1 = ArrayUtil.randomIntArray(random.nextInt(0, 50), -100, 100);
            int[] arr2 = ArrayUtil.randomIntArray(random.nextInt(0, 50), -100, 100);
            Arrays.sort(arr1);
            Arrays.sort(arr2);

            List<Integer> expect = new ArrayList<>(arr1.length + arr2.length);
            for (int num : arr1) {
                expect.add(num);
            }
            for (int num : arr2) {
                expect.add(num);
            }
            Collections.sort(expect);

            ListNode mergedList = mergeTwoLists(ListNode.buildList(arr1), ListNode.buildList(arr2));
            Assert.assertEquals(expect, mergedList.values());
        }
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0), node = dummy;
        ListNode node1 = list1, node2 = list2;
        while (node1 != null && node2 != null) {
            if (node1.val <= node2.val) {
                node.next = node1;
                node1 = node1.next;
            } else {
                node.next = node2;
                node2 = node2.next;
            }
            node = node.next;
        }
        node.next = (node1 != null) ? node1 : node2;
        return dummy.next;
    }
}
