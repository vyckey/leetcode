package problem;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

public class P150EvaluateReversePolishNotation {
    @Test
    public void test() {
        Assert.assertEquals(9, evalRPN(new String[]{"2","1","+","3","*"}));
        Assert.assertEquals(6, evalRPN(new String[]{"4","13","5","/","+"}));
        Assert.assertEquals(22, evalRPN(new String[]{"10","6","9","3","+","-11","*","/","*","17","+","5","+"}));
    }

    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList<>();
        for (String token : tokens) {
            if ("+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token)) {
                int y = stack.pop(), x = stack.pop();
                stack.push(compute(x, y, token));
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.peek();
    }

    private int compute(int x, int y, String operator) {
        int result;
        switch (operator) {
            case "+":
                result = x + y;
                break;
            case "-":
                result = x - y;
                break;
            case "*":
                result = x * y;
                break;
            case "/":
                result = x / y;
                break;
            default:
                throw new IllegalArgumentException("unknown operator " + operator);
        }
        return result;
    }
}
