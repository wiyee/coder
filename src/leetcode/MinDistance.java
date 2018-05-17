package leetcode;

/**
 * Created by wiyee on 2018/5/9.
 * Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. (each operation is counted as 1 step.)
 * <p>
 * You have the following 3 operations permitted on a word:
 * <p>
 * a) Insert a character
 * b) Delete a character
 * c) Replace a character
 * <p>
 * dp
 */
public class MinDistance {
    public static void main(String[] args) {
        MinDistance minDistance = new MinDistance();
        System.out.println(minDistance.minDistance("a", "b"));
    }

    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        if (word1.length() == 0 && word2.length() == 0)
            return 0;
        if (word1.length() == 0)
            return n;
        if (word2.length() == 0)
            return m;

        // dp[i][j]代表由word1的前i个子串变为word2的前j个子串的花费
        int[][] dp = new int[m + 1][n + 1];
        dp[0][0] = 0;
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int i = 1; i <= n; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;

                }
            }
        }
        return dp[m][n];
    }
}
