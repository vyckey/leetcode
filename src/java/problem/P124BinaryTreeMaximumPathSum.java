package problem;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import common.TreeNode;

public class P124BinaryTreeMaximumPathSum {
    @Test
    public void test() {
        Assert.assertEquals(-10, maxPathSum(TreeNode.buildTree(Arrays.asList(-10))));
        Assert.assertEquals(6, maxPathSum(TreeNode.buildTree(Arrays.asList(1,2,3))));
        Assert.assertEquals(42, maxPathSum(TreeNode.buildTree(Arrays.asList(-10,9,20,null,null,15,7))));
    }

    /**
     * maxSubPathSum(node)为以node为起点的最大子树路径和
     * maxPathSum(node)=max(0, maxSubPathSum(node.left)) + max(0, maxSubPathSum(node.right)) + node.val
     */
    public int maxPathSum(TreeNode root) {
        Holder<Integer> maxPathSum = new Holder<>(root.val);
        maxSubPathSum(root, maxPathSum);
        return maxPathSum.getValue();
    }

    private int maxSubPathSum(TreeNode node, Holder<Integer> maxPathSum) {
        if (node == null) {
            return 0;
        }
        int leftMax = maxSubPathSum(node.left, maxPathSum);
        int rightMax = maxSubPathSum(node.right, maxPathSum);

        // 左右孩子节点与node连接的最大路径和
        int curMaxPathSum = Math.max(leftMax, 0) + Math.max(rightMax, 0) + node.val;
        maxPathSum.setValue(Math.max(maxPathSum.getValue(), curMaxPathSum));

        // 返回以node为起点的最大向下路径和
        return Math.max(Math.max(leftMax, rightMax), 0) + node.val;
    }

    static class Holder<T> {
        T value;

        Holder() {
        }

        Holder(T value) {
            this.value = value;
        }

        T getValue() {
            return value;
        }

        void setValue(T value) {
            this.value = value;
        }
    }
}
