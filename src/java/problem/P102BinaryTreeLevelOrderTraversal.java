package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import common.TreeNode;
import util.ListUtil;

public class P102BinaryTreeLevelOrderTraversal {
    @Test
    public void test() {
        Assert.assertTrue(ListUtil.deepEquals(new ArrayList<>(), levelOrder(TreeNode.buildTree(new ArrayList<>()))));
        Assert.assertTrue(ListUtil.deepEquals(Arrays.asList(
            Arrays.asList(3)
        ), levelOrder(TreeNode.buildTree(Arrays.asList(3)))));
        Assert.assertTrue(ListUtil.deepEquals(Arrays.asList(
            Arrays.asList(3),
            Arrays.asList(9,20),
            Arrays.asList(15,7)
        ), levelOrder(TreeNode.buildTree(Arrays.asList(3,9,20,null,null,15,7)))));
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root != null) {
            addChildren(result, Arrays.asList(root));
        }
        return result;
    }

    private void addChildren(List<List<Integer>> result, List<TreeNode> nodes) {
        List<Integer> vals = new ArrayList<>(nodes.size());
        List<TreeNode> children = new ArrayList<>();
        for (TreeNode node : nodes) {
            vals.add(node.val);
            if (node.left != null) {
                children.add(node.left);
            }
            if (node.right != null) {
                children.add(node.right);
            }
        }
        result.add(vals);
        if (!children.isEmpty()) {
            addChildren(result, children);
        }
    }
}
