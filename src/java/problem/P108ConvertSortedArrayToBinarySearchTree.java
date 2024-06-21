package problem;

import org.junit.Assert;
import org.junit.Test;

import common.TreeNode;

public class P108ConvertSortedArrayToBinarySearchTree {

    private static boolean isBST(TreeNode root) {
        return isBST(root, null, null);
    }
    
    private static boolean isBST(TreeNode root, Integer min, Integer max) {
        if (root == null) {
            return true;
        }
        if (min != null && root.val < min) {
            return false;
        }
        if (max != null && root.val > max) {
            return false;
        }
        return isBST(root.left, min, root.val) && isBST(root.right, root.val, max);
    }

    @Test
    public void test() {
        Assert.assertTrue(isBST(sortedArrayToBST(new int[]{1,3})));
        Assert.assertTrue(isBST(sortedArrayToBST(new int[]{-10,-3,0,5,9})));
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        return buildBST(nums, 0, nums.length);
    }

    private TreeNode buildBST(int[] nums, int left, int right) {
        if (left >= right) {
            return null;
        } else if (left + 1 == right) {
            return new TreeNode(nums[left]);
        }

        int mid = (left + right) >> 1;
        TreeNode leftBST = buildBST(nums, left, mid);
        TreeNode rightBST = buildBST(nums, mid + 1, right);
        return new TreeNode(nums[mid], leftBST, rightBST);
    }
}
