package problem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;

import common.TreeNode;

public class P98ValidateBinarySearchTree {
    @Test
    public void test() {
        Assert.assertTrue(isValidBST(TreeNode.buildTree(Arrays.asList(2,1,3))));
        Assert.assertFalse(isValidBST(TreeNode.buildTree(Arrays.asList(5,1,4,null,null,3,6))));
        Assert.assertTrue(isValidBST(TreeNode.buildTree(Arrays.asList(5, 1, 2^32-1, -2^32, null))));
    }

    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return false;
        }
        Queue<NodeRange> queue = new LinkedList<>();
        queue.add(new NodeRange(root));
        while (!queue.isEmpty()) {
            NodeRange range = queue.poll();
            if (!range.isValid()) {
                return false;
            }
            TreeNode node = range.node;
            if (node.left != null) {
                queue.add(new NodeRange(node.left, range.from, node.val));
            }
            if (node.right != null) {
                queue.add(new NodeRange(node.right, node.val, range.end));
            }
        }
        return true;
    }

    static class NodeRange {
        final TreeNode node;
        final Integer from;
        final Integer end;

        NodeRange(TreeNode node) {
            this(node, null, null);
        }

        NodeRange(TreeNode node, Integer from, Integer end) {
            this.node = node;
            this.from = from;
            this.end = end;
        }

        boolean isValid() {
            return (from == null || from < node.val) && (end == null || node.val < end);
        }
    }
}
