package problem;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import common.TreeNode;

public class P101SymmetricTree {
    @Test
    public void test() {
        Assert.assertFalse(isSymmetric(TreeNode.buildTree(Arrays.asList(1,2,2,null,3,null,3))));
        Assert.assertTrue(isSymmetric(TreeNode.buildTree(Arrays.asList(1,2,2,3,4,4,3))));
        Assert.assertFalse(isSymmetric(TreeNode.buildTree(Arrays.asList(1,2,2,3,4,3,4))));
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return false;
        }
        return isSymmetric(root.left, root.right);
    }

    private boolean isSymmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }

        if (left.val != right.val) {
            return false;
        }
        if (!isSymmetric(left.left, right.right)) {
            return false;
        }
        return isSymmetric(left.right, right.left);
    }
}
