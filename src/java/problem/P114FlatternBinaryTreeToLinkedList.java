package problem;

import java.util.Arrays;

import org.junit.Test;
import common.TreeNode;

public class P114FlatternBinaryTreeToLinkedList {
    @Test
    public void test() {
        TreeNode root = TreeNode.buildTree(Arrays.asList(1,2,5,3,4,null,6));
        flatten(root);
    }

    public void flatten(TreeNode root) {
        if (root != null) {
            flattenTree(root);
        }
    }

    private TreeNode flattenTree(TreeNode node) {
        TreeNode tail = node;
        if (node.left != null) {
            tail = flattenTree(node.left);
            tail.right = node.right;
        }
        if (node.right != null) {
            tail = flattenTree(node.right);
        }
        if (node.left != null) {
            node.right = node.left;
            node.left = null;
        }
        return tail;
    }
}
