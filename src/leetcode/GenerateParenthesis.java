package leetcode;

import java.util.ArrayList;

/**
 * Created by wiyee on 2018/4/13.
 * <p>
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 * <p>
 * For example, given n = 3, a solution set is:
 * <p>
 * "((()))", "(()())", "(())()", "()(())", "()()()"
 */
public class GenerateParenthesis {
    public static void main(String[] args) {
        System.out.println(new GenerateParenthesis().generateParenthesis(3));
    }

    public ArrayList<String> generateParenthesis(int n) {
        ArrayList<String> res = new ArrayList<>();
        if (n < 1)
            return res;
        generate(n, n, res, new StringBuffer());
        return res;
    }

    public void generate(int left, int right, ArrayList<String> res, StringBuffer sb) {
        if (left < 0 || right < 0 || left > right)
            return;
        if (left == 0 && right == 0) {
            res.add(sb.toString());
            return;
        }
        generate(left - 1, right, res, sb.append("("));
        sb.deleteCharAt(sb.length() - 1);
        generate(left, right - 1, res, sb.append(")"));
        sb.deleteCharAt(sb.length() - 1);
    }

}
