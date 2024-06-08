package problem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class P22GenerateParanthesis {
    /**
     * ()
     * ()() (())
     * ()()() (())() ()(()) (()()) ((()))
     */
    @Test
    public void test() {
        List<String> parenthesis = generateParenthesis3(3);
        System.out.println(parenthesis);
    }

    /**
     * 方法一：补加括号法，每次加一层括号
     */
    public List<String> generateParenthesis1(int n) {
        if (n <= 0) {
            return new ArrayList<>();
        }
        Set<String> parenthesis = new HashSet<>();
        parenthesis.add("()");
        for (int i = 1; i < n; i++) {
            parenthesis = addParenthesis(parenthesis);  
        }
        return new ArrayList<>(parenthesis);
    }

    private Set<String> addParenthesis(Set<String> parenthesis) {
        // 重新生成的可能会有重复，需要使用Set去重
        Set<String> newParenthesis = new HashSet<>();
        for (String str : parenthesis) {
            final int len = str.length();
            newParenthesis.add("()" + str);
            // 对每一个左开口的括号(遍历，补加()
            for (int i = 0; i < len; i++) {
                if (str.charAt(i) != '(') {
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str, 0, i + 1);
                sb.append("()").append(str, i + 1, len);
                newParenthesis.add(sb.toString());
            }
        }
        return newParenthesis;
    }

    /**
     * 方法二：扩充前缀进行递归调用
     */
    public List<String> generateParenthesis(int n) {
        List<String> parenthesis = new ArrayList<>();
        generateParenthesis(parenthesis, new StringBuilder(), n, 0);
        return parenthesis;
    }

    private void generateParenthesis(List<String> parenthesis, StringBuilder prefix, int left, int open) {
        if (left == 0) {
            StringBuilder sb = new StringBuilder(prefix);
            for (int i = 0; i < open; i++) {
                sb.append(')');
            }

            parenthesis.add(sb.toString());
            return;
        }

        prefix.append('(');
        generateParenthesis(parenthesis, prefix, left - 1, open + 1);
        prefix.deleteCharAt(prefix.length() - 1);
        
        if (open > 0) {
            prefix.append(')');
            generateParenthesis(parenthesis, prefix, left, open - 1);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }


    /**
     * 方法三：按括号数量进行递归遍历，对非法情况进行剪枝
     */
    public List<String> generateParenthesis3(int n) {
        List<String> parenthesis = new ArrayList<>();
        generateParenthesis(parenthesis, new StringBuilder(), n, n, 0);
        return parenthesis;
    }

    private void generateParenthesis(List<String> parenthesis, StringBuilder prefix, int left, int right, int open) {
        if (left > 0 && right <= 0 || open < 0) {
            return;
        }
        if (left == 0 && right == 0 && open == 0) {
            parenthesis.add(prefix.toString());
        }

        if (left > 0) {
            prefix.append('(');
            generateParenthesis(parenthesis, prefix, left - 1, right, open + 1);
            prefix.deleteCharAt(prefix.length() - 1);
        }
        if (right > 0) {
            prefix.append(')');
            generateParenthesis(parenthesis, prefix, left, right - 1, open - 1);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}
