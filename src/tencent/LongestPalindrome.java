package tencent;

import java.util.Scanner;

/**
 * Created by wiyee on 2018/6/4.
 * 给定一个字符串s，你可以从中删除一些字符，使得剩下的串是一个回文串。如何删除才能使得回文串最长呢？
 * 输出需要删除的字符个数。
 * <p>
 * 输入描述:
 * <p>
 * 输入数据有多组，每组包含一个字符串s，且保证:1<=s.length<=1000.
 * <p>
 * 输出描述:
 * 对于每组数据，输出一个整数，代表最少需要删除的字符个数。
 * <p>
 * <p>
 * 输入例子1:
 * abcda
 * google
 * <p>
 * 输出例子1:
 * 2
 * 2
 */
public class LongestPalindrome {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.next();
            StringBuilder sb = new StringBuilder(s);// StringBuilder的reverse()方法可以实现字符串反转
            String invS = sb.reverse().toString();
            int result = getCommonSequence(s, invS);
            System.out.println(s.length() - result);

        }
    }

    private static int getCommonSequence(String s1, String s2) {
        int result = 0;
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 0;
            dp[0][i] = 0;
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp.length; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }
}
