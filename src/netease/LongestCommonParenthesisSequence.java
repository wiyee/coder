package netease;

import java.util.*;

/**
 * Created by wiyee on 2018/5/17.
 * 一个合法的括号匹配序列被定义为:
 * 1. 空串""是合法的括号序列
 * 2. 如果"X"和"Y"是合法的序列,那么"XY"也是一个合法的括号序列
 * 3. 如果"X"是一个合法的序列,那么"(X)"也是一个合法的括号序列
 * 4. 每个合法的括号序列都可以由上面的规则生成
 * 例如"", "()", "()()()", "(()())", "(((()))"都是合法的。
 * 从一个字符串S中移除零个或者多个字符得到的序列称为S的子序列。
 * 例如"abcde"的子序列有"abe","","abcde"等。
 * 定义LCS(S,T)为字符串S和字符串T最长公共子序列的长度,即一个最长的序列W既是S的子序列也是T的子序列的长度。
 * 小易给出一个合法的括号匹配序列s,小易希望你能找出具有以下特征的括号序列t:
 * 1、t跟s不同,但是长度相同
 * 2、t也是一个合法的括号匹配序列
 * 3、LCS(s, t)是满足上述两个条件的t中最大的
 * 因为这样的t可能存在多个,小易需要你计算出满足条件的t有多少个。
 * <p>
 * 如样例所示: s = "(())()",跟字符串s长度相同的合法括号匹配序列有:
 * "()(())", "((()))", "()()()", "(()())",其中LCS( "(())()", "()(())" )为4,其他三个都为5,所以输出3.
 * 输入描述:
 * 输入包括字符串s(4 ≤ |s| ≤ 50,|s|表示字符串长度),保证s是一个合法的括号匹配序列。
 * <p>
 * <p>
 * 输出描述:
 * 输出一个正整数,满足条件的t的个数。
 * <p>
 * 输入例子1:
 * (())()
 * <p>
 * 输出例子1:
 * 3
 */
public class LongestCommonParenthesisSequence {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            int n = str.length();
            Map<String, Integer> map = new HashMap<>();
            for (int i = 0; i < n; i++) {
                String s1 = str.substring(0, i);
                String s2 = str.substring(i + 1, n);
                String s3 = s1 + s2;
                for (int j = 0; j < n; j++) {
                    String b1 = s3.substring(0, j);
                    String b2 = s3.substring(j, n - 1);
                    String b3 = b1 + String.valueOf(str.charAt(i)) + b2;
                    if (b3.equals(str))
                        continue;
                    else {
                        if (isTrueSequence(b3))
                            map.put(b3, 1);
                    }
                }
            }
            System.out.println(map.size());

        }
    }

    public static boolean isTrueSequence(String str) {
        char[] c = str.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < c.length; i++) {
            if (stack.isEmpty()) {
                if (c[i] == '(') {
                    stack.push(c[i]);
                } else
                    return false;
            } else {
                if (c[i] == ')')
                    stack.pop();
                else
                    stack.push('(');
            }
        }
        if (stack.isEmpty())
            return true;
        else
            return false;

    }
}
