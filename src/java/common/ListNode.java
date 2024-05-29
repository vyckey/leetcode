package common;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public ListNode tail() {
        ListNode tail = this;
        while (tail.next != null) {
            tail = tail.next;
        }
        return tail;
    }

    public ListNode forward(int i) {
        ListNode node = this;
        int p = 0;
        while (p != i && node != null) {
            node = node.next;
            ++p;
        }
        return node;
    }

    public static ListNode buildList(int[] vals) {
        ListNode node = null;
        for (int i = vals.length - 1; i >= 0; i--) {
            int val = vals[i];
            node = new ListNode(val, node);
        }
        return node;
    }

}
