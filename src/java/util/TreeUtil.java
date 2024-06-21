package util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import common.TreeNode;

public class TreeUtil {
    private TreeUtil() {
    }

    public static void printTreeNodes(TreeNode root) {
        printTreeNodes(root, "_", -1);
    }

    public static void printTreeNodes(TreeNode root, String blank, int width) {
        System.out.println("Tree is below:");
        if (root == null) {
            System.out.println("EMPTY");
            return;
        }

        List<List<TreeNode>> matrix = new ArrayList<>();
        fillTreeNodes(matrix, root, 0, 0);
        for (List<TreeNode> row : matrix) {
            String rowStr = row.stream().map(node -> {
                    if (node == null) return blank;
                    StringBuilder sb = new StringBuilder().append(node.val);
                    for (int i = sb.length(); i < width; i++) {
                        sb.append(' ');
                    }
                    return sb.toString();
                })
                .collect(Collectors.joining());
            System.out.println(rowStr);
        }
        System.out.println("\n");
    }

    private static int fillTreeNodes(List<List<TreeNode>> matrix, TreeNode node, int level, int offset) {
        if (node == null) {
            return 1;
        }
        
        List<TreeNode> row;
        if (level + 1 > matrix.size()) {
            row = new ArrayList<>();
            matrix.add(row);
        } else {
            row = matrix.get(level);
        }
        for (int i = row.size(); i < offset; i++) {
            row.add(null);
        }

        int leftCols = fillTreeNodes(matrix, node.left, level + 1, offset);
        int rightCols = fillTreeNodes(matrix, node.right, level + 1, offset + leftCols + 1);

        int pos = offset + 1 + (leftCols + rightCols + 1) / 2;
        for (int i = row.size(); i < pos; i++) {
            row.add(null);
        }
        row.add(node);
        return leftCols + rightCols + 1;
    }
}
