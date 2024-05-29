package common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TreeNode {
    /**
     * 以下属性设置为public是考虑到leetcode中可以直接访问，保存代码一致
     */
    public int val;
    public TreeNode left;
    public TreeNode right;
    
    public TreeNode() {}
    
    public TreeNode(int val) { this.val = val; }
    
    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public static TreeNode buildTree(List<Integer> vals) {
        if (vals.isEmpty()) {
            return null;
        }
        List<TreeNode> nodes = new ArrayList<>(vals.size());
        for (Integer val : vals) {
            if (val != null) {
                nodes.add(new TreeNode(val));
            } else {
                nodes.add(null);
            }
        }
        for (int i = 0; i * 2 < nodes.size(); i++) {
            TreeNode node = nodes.get(i);
            if (node != null) {
                if (i * 2 + 1 < nodes.size()) {
                    node.left = nodes.get(i * 2 + 1);
                }
                if (i * 2 + 2 < nodes.size()) {
                    node.right = nodes.get(i * 2 + 2);
                }
            }
        }
        return nodes.get(0);
    }

    public List<Integer> travel(TravelOrder order) {
        List<Integer> leftvals = (left == null) ? new LinkedList<>() : left.travel(order);
        List<Integer> rightvals = (right == null) ? new LinkedList<>() : right.travel(order);
        switch (order) {
            case PRE:
                leftvals.addFirst(val);
                leftvals.addAll(rightvals);
                break;
            case IN:
                leftvals.add(val);
                leftvals.addAll(rightvals);
                break;
            case POST:
                leftvals.addAll(rightvals);
                leftvals.add(val);
                break;
            default:
                break;
        }
        return leftvals;
    }

    public static enum TravelOrder {
        PRE, IN, POST
    }
}
