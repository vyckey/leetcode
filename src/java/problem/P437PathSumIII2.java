package problem;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;

import common.TreeNode;

public class P437PathSumIII2 {
    @Test
    public void test1() {
        Assert.assertEquals(0, pathSum(null, 1));

        TreeNode root1 = TreeNode.buildTree(Arrays.asList(10,5,-3,3,2,null,11,3,-2,null,1));
        Assert.assertEquals(3, pathSum(root1, 8));

        TreeNode root2 = TreeNode.buildTree(Arrays.asList(5,4,8,11,null,13,4,7,2,null,null,5,1));
        Assert.assertEquals(3, pathSum(root2, 22));

        TreeNode root3 = TreeNode.buildTree(Arrays.asList(1000000000,1000000000,null,294967296,null,1000000000,null,1000000000,null,1000000000));
        Assert.assertEquals(0, pathSum(root3, 0));
    }

    @Test
    public void test2() {
        TreeNode root = new TreeNode(1000000000), node = root;
        for (int val : Arrays.asList(1000000000,294967296,1000000000,1000000000,1000000000)) {
            TreeNode left = new TreeNode(val);
            node.left = left;
            node = left;
        }
        Assert.assertEquals(0, pathSum(root, 0));
    }

    /**
     * 设f(node, sum)为以node开始且路径和为sum的数量
     * f(node, sum) = (sum==node.val ? 1:0) + f(node.left, sum - node.val) + f(node.right, sum - node.val)
     */
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int count = 0;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            count += pathSumFrom(node, targetSum);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return count;
    }

    private int pathSumFrom(TreeNode node, int targetSum) {
        if (isMinusOverflow(targetSum, node.val)) {
            return 0;
        }
        int nextTargetSum = targetSum - node.val;
        int count = (nextTargetSum == 0) ? 1 : 0;
        if (node.left != null) {
            count += pathSumFrom(node.left, nextTargetSum);
        }
        if (node.right != null) {
            count += pathSumFrom(node.right, nextTargetSum);
        }
        return count;
    }

    private static boolean isMinusOverflow(int x, int y) {
        int z = x - y;
        return (x > 0 && y < 0 && z <= 0) || (x < 0 && y > 0 && z >= 0);
    }
}
