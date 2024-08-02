package problem;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

public class P155MinStack {
    @Test
    public void test() {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        Assert.assertEquals(-3, minStack.getMin());
        minStack.pop();
        Assert.assertEquals(0, minStack.top());
        Assert.assertEquals(-2, minStack.getMin());
    }

    private static class MinStack {
        private final Deque<Integer> stack;
        private final Deque<Integer> mins;

        public MinStack() {
            this.stack = new LinkedList<>();
            this.mins = new LinkedList<>();
        }
        
        public void push(int val) {
            stack.push(val);
            if (mins.isEmpty()) {
                mins.push(val);
            } else {
                mins.push(Math.min(mins.peek(), val));
            }
        }
        
        public void pop() {
            stack.pop();
            mins.pop();
        }
        
        public int top() {
            return stack.peek();
        }
        
        public int getMin() {
            return mins.peek();
        }
    }
}
