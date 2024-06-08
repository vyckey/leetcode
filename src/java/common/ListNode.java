package common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ListNode implements Iterable<Integer> {
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

    public List<Integer> values() {
        List<Integer> values = new ArrayList<>();
        for (Integer val : this) {
            values.add(val);
        }
        return values;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
    
    public String toText() {
        return values().stream().map(i -> i.toString()).collect(Collectors.joining("->"));
    }

    public static ListNode buildList(int[] vals) {
        ListNode node = null;
        for (int i = vals.length - 1; i >= 0; i--) {
            int val = vals[i];
            node = new ListNode(val, node);
        }
        return node;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iter(this);
    }

    private static class Iter implements Iterator<Integer> {
        private ListNode current;

        private Iter(ListNode current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Integer next() {
            Integer val =  current.val;
            current = current.next;
            return val;
        }
    }

}
