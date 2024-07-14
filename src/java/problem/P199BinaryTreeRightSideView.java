package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import common.TreeNode;

public class P199BinaryTreeRightSideView {
    @Test
    public void test() {
        Assert.assertEquals(Arrays.asList(), rightSideView(null));
        Assert.assertEquals(Arrays.asList(1,3,4), rightSideView(TreeNode.buildTree(Arrays.asList(1,2,3,null,5,null,4))));
        Assert.assertEquals(Arrays.asList(1,3), rightSideView(TreeNode.buildTree(Arrays.asList(1,null,3))));
        Assert.assertEquals(Arrays.asList(1,3,4), rightSideView(TreeNode.buildTree(Arrays.asList(1,2,3,4))));
    }

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> rightSideVals = new ArrayList<>();
        if (root == null) {
            return rightSideVals;
        }

        dfs(rightSideVals, root, 1);
        return rightSideVals;
    }

    private void dfs(List<Integer> rightSideVals, TreeNode node, int depth) {
        if (depth > rightSideVals.size()) {
            rightSideVals.add(node.val);
        }
        if (node.right != null) {
            dfs(rightSideVals, node.right, depth + 1);
        }
        if (node.left != null) {
            dfs(rightSideVals, node.left, depth + 1);
        }
    }
}
