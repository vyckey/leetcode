package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

import common.NestedInteger;

public class P341FlattenNestedListIterator {
    private List<Integer> integerValues(List<NestedInteger> nestedInts) {
        NestedIterator iterator = new NestedIterator(nestedInts);
        List<Integer> values = new ArrayList<>();
        while (iterator.hasNext()) {
            values.add(iterator.next());
        }
        return values;
    }

    @Test
    public void test() {
        //[[],[]]
        List<NestedInteger> ints1 = Arrays.asList(NestedInteger.of(), NestedInteger.of());
        Assert.assertEquals(Arrays.asList(), integerValues(ints1));

        //[[],[3]]
        List<NestedInteger> ints2 = Arrays.asList(NestedInteger.of(), NestedInteger.of(NestedInteger.of(3)));
        Assert.assertEquals(Arrays.asList(3), integerValues(ints2));

        //[[1,1],2,[3,4]]
        List<NestedInteger> ints3 = Arrays.asList(
            NestedInteger.of(NestedInteger.of(1), NestedInteger.of(1)),
            NestedInteger.of(2),
            NestedInteger.of(NestedInteger.of(3), NestedInteger.of(4)));
        Assert.assertEquals(Arrays.asList(1,1,2,3,4), integerValues(ints3));

        //[[1,1],2,[3,[],[2],4]]
        List<NestedInteger> ints4 = Arrays.asList(
            NestedInteger.of(NestedInteger.of(1), NestedInteger.of(1)),
            NestedInteger.of(2),
            NestedInteger.of(NestedInteger.of(3), NestedInteger.of(), NestedInteger.of(NestedInteger.of(2)),
                NestedInteger.of(4)));
        Assert.assertEquals(Arrays.asList(1,1,2,3,2,4), integerValues(ints4));
    }

    private static class NestedIterator implements Iterator<Integer> {
        private final Deque<Iterator<NestedInteger>> stack = new LinkedList<>();
        private NestedInteger nextNestedInt;

        NestedIterator(List<NestedInteger> nestedList) {
            stack.push(nestedList.iterator());
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                Integer integer = nextNestedInt.getInteger();
                nextNestedInt = null;
                return integer;
            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasNext() {
            if (nextNestedInt != null) {
                return true;
            }

            while (!stack.isEmpty()) {
                Iterator<NestedInteger> peek = stack.peek();
                if (!peek.hasNext()) {
                    stack.pop();
                    continue;
                }

                NestedInteger nextInt = peek.next();
                if (nextInt.isInteger()) {
                    nextNestedInt = nextInt;
                    break;
                }
                Iterator<NestedInteger> nextIter = nextInt.getList().iterator();
                stack.push(nextIter);
            }
            return nextNestedInt != null;
        }

    }
}
