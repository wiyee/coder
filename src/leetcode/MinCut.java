package leetcode;

/**
 * Created by wiyee on 2018/4/25.
 * Given a string s, partition s such that every substring of the partition is a palindrome.
 * <p>
 * Return the minimum cuts needed for a palindrome partitioning of s.
 * <p>
 * For example, given s ="aab",
 * Return1since the palindrome partitioning["aa","b"]could be produced using 1 cut.
 * [dp]
 * // 思路： 动规方程：dp[i] = min{dp[i], dp[j+1] + 1}
 * dp[i] 表示字符串s从 i 到末尾的子串所需要的最小割数，如果从 i 到 j 的子串为回文串的话，那么最小割数就可能为 j + 1以后的子串的最小割数加上 j 和 j + 1 之间的一割。
 */
public class MinCut {
    public static void main(String[] args) {
        System.out.println(minCut("aab"));
    }

    public static int minCut(String s) {
        if (s == null || s.length() < 2)
            return 0;
        int n = s.length();
        int[] dp = new int[n];
        boolean[][] isPalin = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = n - 1 - i;

            for (int j = i; j < n; j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i < 2 || isPalin[i + 1][j - 1])) {
                    isPalin[i][j] = true;
                    if (j == n - 1) {
                        dp[i] = 0; // s[i...n-1] is palindrome, no need cut
                    } else {
                        dp[i] = Math.min(dp[i], dp[j + 1] + 1);
                    }
                }

            }
        }
        return dp[0];
    }
}
