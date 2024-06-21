package problem;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import common.TreeNode;

public class P230KthSmallestElementInABST {

    @Test
    public void test() {
        Assert.assertEquals(10, kthSmallest(TreeNode.buildTree(Arrays.asList(1,2,3,null,4,5,6,null,null,null,7,8,9,10)), 9));
        
        List<Integer> vals = Arrays.asList(41,37,44,24,39,42,48,1,35,38,40,null,43,46,49,0,2,30,36,null,null,null,null,null,null,45,47,null,null,null,null,null,4,29,32,null,null,null,null,null,null,3,9,26,null,31,34,null,null,7,11,25,27,null,null,33,null,6,8,10,16,null,null,null,28,null,null,5,null,null,null,null,null,15,19,null,null,null,null,12,null,18,20,null,13,17,null,null,22,null,14,null,null,21,23);
        Assert.assertEquals(48, kthSmallest(TreeNode.buildTree(vals), 25));
    }

    public int kthSmallest(TreeNode root, int k) {
        Holder<TreeNode> result = new Holder<>();
        kthSmallest(result, root, k);
        return Optional.ofNullable(result.getValue()).map(node -> node.val).orElse(-1);
    }

    /**
     * DFS遍历
     * @return 如果已经找到第k个节点，返回任意值，否则返回子树的节点数量
     */
    private int kthSmallest(Holder<TreeNode> result, TreeNode node, int k) {
        // 找到结果，尽快退出
        if (result.hasValue()) {
            return 0;
        }
        if (node == null) {
            return 0;
        }

        int leftCount = kthSmallest(result, node.left, k);
        if (result.hasValue()) {
            return 0;
        }
        if (leftCount + 1 == k) {
            result.setValue(node);
            return 0;
        }

        int rightCount = kthSmallest(result, node.right, k - leftCount - 1);
        return leftCount + rightCount + 1;
    }

    static class Holder<T> {
        T value;

        void setValue(T value) {
            this.value = value;
        }

        boolean hasValue() {
            return value != null;
        }

        T getValue() {
            return value;
        }
    }
}
