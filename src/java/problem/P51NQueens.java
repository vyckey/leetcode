package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class P51NQueens {
    private static void printBoards(List<List<String>> result) {
        int i = 1;
        for (List<String> board : result) {
            System.out.println("Answer" + i++);
            System.out.println(board.stream().collect(Collectors.joining("\n")) + "\n");
        }
    }

    private static boolean isValid(List<String> board, int i, int j) {
        int n = board.size();
        int k = 1;
        while ((i - k) >= 0) {
            if (j - k >= 0 && board.get(i - k).charAt(j - k) == 'Q') {
                return false;
            }
            if (j + k < n && board.get(i - k).charAt(j + k) == 'Q') {
                return false;
            }
            k++;
        }
        k = 1;
        while ((i + k) < n) {
            if (j - k >= 0 && board.get(i + k).charAt(j - k) == 'Q') {
                return false;
            }
            if (j + k < n && board.get(i + k).charAt(j + k) == 'Q') {
                return false;
            }
            k++;
        }
        return true;
    }

    private static boolean isValid(List<List<String>> result, int n) {
        for (List<String> board : result) {
            int queens = 0;
            boolean[] cols = new boolean[n];
            for (int i = 0; i < n; i++) {
                String row = board.get(i);
                if (row.length() != n) {
                    return false;
                }

                int j = row.indexOf('Q');
                if (j < 0 || cols[j] || !isValid(board, i, j)) {
                    return false;
                }
                cols[j] = true;
                queens += row.chars().filter(c -> c==((int)'Q')).count();
            }
            if (queens != n) {
                return false;
            }
        }
        return result.size() == result.stream().map(list -> String.join("", list)).distinct().count();
    }

    @Test
    public void test() {
        Assert.assertTrue(solveNQueens(2).isEmpty());
        Assert.assertTrue(solveNQueens(3).isEmpty());

        int[] nums = new int[]{1,4,5,6,7,8,9};
        for (int n : nums) {
            List<List<String>> result = solveNQueens(n);
            printBoards(result);
            Assert.assertTrue(isValid(result, n));
        }
    }

    /**
     * 深度优先＋剪枝
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        Set<Integer> emtpyColumns = new HashSet<>();
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
            emtpyColumns.add(i);
        }

        solveNQueens(result, board, 0, emtpyColumns);
        return result;
    }

    private void solveNQueens(List<List<String>> result, char[][] board, int i, Set<Integer> emptyColums) {
        int n = board.length;
        if (i >= n) {
            List<String> ans = new ArrayList<>(n);
            for (char[] chars : board) {
                ans.add(new String(chars));
            }
            result.add(ans);
        }

        Integer[] emptyColumnsArr = emptyColums.toArray(new Integer[0]);
        for (int j : emptyColumnsArr) {
            int k = 1;
            boolean invalid = false;
            while ((i - k) >= 0) {
                if (j - k >= 0 && board[i - k][j - k] == 'Q') {
                    invalid = true;
                    break;
                }
                if (j + k < n && board[i - k][j + k] == 'Q') {
                    invalid = true;
                    break;
                }
                k++;
            }
            if (invalid) {
                continue;
            }

            char ch = board[i][j];
            board[i][j] = 'Q';
            emptyColums.remove(j);
            solveNQueens(result, board, i + 1, emptyColums);
            emptyColums.add(j);
            board[i][j] = ch;
        }
    }
}
