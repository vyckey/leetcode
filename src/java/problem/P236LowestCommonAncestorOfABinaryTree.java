package problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import common.TreeNode;

public class P236LowestCommonAncestorOfABinaryTree {
    @Test
    public void test() {
    }

    private static class Solution1 {
        private List<TreeNode> pPath, qPath;

        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            dfs(new LinkedList<>(), root, p, q);
            TreeNode parent = root;
            for (int i = 1; i < Math.min(pPath.size(), qPath.size()); i++) {
                if (pPath.get(i) != qPath.get(i)) {
                    return parent;
                }
                parent = pPath.get(i);
            }
            return parent;
        }

        private void dfs(List<TreeNode> path, TreeNode node, TreeNode p, TreeNode q) {
            if (pPath != null && qPath != null) {
                return;
            }
            path.add(node);
            if (node == p) {
                pPath = new ArrayList<>(path);
            } else if (node == q) {
                qPath = new ArrayList<>(path);
            }

            if (node.left != null) {
                dfs(path, node.left, p, q);
            }
            if (node.right != null) {
                dfs(path, node.right, p, q);
            }
            path.remove(path.size() - 1);
        }
    }
    
    private static class Solution2 {
        private TreeNode ansNode;

        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            dfs(root, p, q);
            return ansNode;
        }

        /**
         * 设F(t,n)表示子树t是否包含节点n，假设结果为n，n的左子树为l，右子树为r
         * 则有F(l,p) && F(r,q) || F(l,q) && F(r,p) 
         *  || n==p && (F(l,q) || F(r,q)) || n==q && (F(l,p) || F(r,p))
         */
        private boolean dfs(TreeNode node, TreeNode p, TreeNode q) {
            if (node == null) {
                return false;
            }
            
            boolean b1 = dfs(node.left, p, q);
            boolean b2 = dfs(node.right, p, q);
            if (b1 && b2 || node == p && (b1 || b2) || node == q && (b1 || b2)) {
                ansNode = node;
            }
            if (node == p || node == q) {
                return true;
            }
            return b1 || b2;
        }
    }
}
