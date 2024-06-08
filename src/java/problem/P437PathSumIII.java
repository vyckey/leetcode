package problem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import common.TreeNode;

public class P437PathSumIII {
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
        // 测试整型加减溢出
        TreeNode root = new TreeNode(1000000000), node = root;
        for (int val : Arrays.asList(1000000000,294967296,1000000000,1000000000,1000000000)) {
            TreeNode left = new TreeNode(val);
            node.left = left;
            node = left;
        }
        Assert.assertEquals(0, pathSum(root, 0));
    }

    /**
     * 前缀和DFS
     */
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }

        // 保存当前node的所有前缀和的数量，key是前缀和，value是同一前缀和值的数量
        Map<Long, Integer> prefixMap = new HashMap<>();
        prefixMap.put(0L, 1);

        return dfsPathSum(root, prefixMap, 0, targetSum);
    }

    private int dfsPathSum(TreeNode node, Map<Long, Integer> prefixMap, long curSum, int targetSum) {
        if (node == null) {
            return 0;
        }

        curSum += node.val;
        long prefixSum = curSum - targetSum;
        int count = prefixMap.getOrDefault(prefixSum, 0);

        prefixMap.put(curSum, prefixMap.getOrDefault(curSum, 0) + 1);

        count += dfsPathSum(node.left, prefixMap, curSum, targetSum);
        count += dfsPathSum(node.right, prefixMap, curSum, targetSum);

        // 非常重要！换路径就应该删除掉！
        prefixMap.put(curSum, prefixMap.get(curSum) - 1);
        return count;
    }
}
