package problem;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import common.TreeNode;
import common.TreeNode.TravelOrder;;

public class P105BinaryTreeConstruct {
    private static int[] toIntArr(List<Integer> list) {
        return list.stream().mapToInt(i -> i).toArray();
    }

    @Test
    public void test() {
        int[][][] testCases = new int[][][]{
            new int[][]{new int[]{6}, new int[]{6}},
            new int[][]{new int[]{3,9,20,15,7}, new int[]{9,3,15,20,7}}
        };
        for (int[][] testCase : testCases) {
            int[] preorder = testCase[0];
            int[] inorder = testCase[1];
            TreeNode root = buildTree(preorder, inorder);
            System.out.println(root);
            Assert.assertArrayEquals(preorder, toIntArr(root.travel(TravelOrder.PRE)));
            Assert.assertArrayEquals(inorder, toIntArr(root.travel(TravelOrder.IN)));
        }
    }

    private static int indexOf(int[] array, int from, int target) {
        for (int i = from; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree(preorder, inorder, 0, preorder.length, 0, inorder.length);
        // if (preorder.length == 0) {
        //     return null;
        // } else if (preorder.length == 1) {
        //     return new TreeNode(preorder[0]);
        // }
        // int parent = preorder[0];
        // int index = indexOf(inorder, parent);
        // TreeNode left = buildTree(Arrays.copyOfRange(preorder, 1, index + 1), Arrays.copyOfRange(inorder, 0, index));
        // TreeNode right = buildTree(Arrays.copyOfRange(preorder, index + 1, preorder.length), Arrays.copyOfRange(inorder, index + 1, inorder.length));
        // return new TreeNode(parent, left, right);
    }

    private TreeNode buildTree(int[] preorder, int[] inorder, int start1, int end1, int start2, int end2) {
        if (end1 - start1 == 0) {
            return null;
        } else if (end1 - start1 == 1) {
            return new TreeNode(preorder[start1]);
        }
        int parent = preorder[start1];
        int index = indexOf(inorder, start2, parent);
        int len = index - start2;
        TreeNode left = buildTree(preorder, inorder, start1 + 1, start1 + len  + 1, start2, start2 + len);
        TreeNode right = buildTree(preorder, inorder, start1 + len + 1, end1, start2 + len + 1, end2);
        return new TreeNode(parent, left, right);
    }
}
