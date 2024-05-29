package problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import common.TreeNode;;

public class P94BinaryTreeOrderTraversal {
    @Test
    public void test() {

    }

    public List<Integer> inorderTraversal(TreeNode root) {
        return traversal(root, TraversalOrder.IN);
    }

    public List<Integer> traversal(TreeNode root, TraversalOrder order) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> leftvals = (root.left == null) ? new LinkedList<>() : traversal(root.left, order);
        List<Integer> rightvals = (root.right == null) ? new LinkedList<>() : traversal(root.right, order);
        switch (order) {
            case PRE:
                leftvals.addFirst(root.val);
                leftvals.addAll(rightvals);
                break;
            case IN:
                leftvals.add(root.val);
                leftvals.addAll(rightvals);
                break;
            case POST:
                leftvals.addAll(rightvals);
                leftvals.add(root.val);
                break;
            default:
                break;
        }
        return leftvals;
    }

    enum TraversalOrder {
        PRE, IN, POST
    }
}
