package leetcode;

/**
 * Created by wiyee on 2018/4/24.
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 * <p>
 * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 * <p>
 * How many possible unique paths are there?
 * <p>
 * <p>
 * <p>
 * Above is a 3 x 7 grid. How many possible unique paths are there?
 * <p>
 * Note: m and n will be at most 100.
 * <p>
 * 啊哈哈哈哈，这是做对的第二道动态规划。
 */
public class UniquePaths {
    public static void main(String[] args) {
        System.out.println(uniquePaths(3, 3));
    }

    public static int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        if (m == 1 || n == 1)
            return 1;
        dp[m - 1][n - 1] = 0;
        for (int i = m - 2; i >= 0; i--) {
            dp[i][n - 1] = 1;
        }
        for (int i = n - 2; i >= 0; i--) {
            dp[m - 1][i] = 1;
        }
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                dp[i][j] = dp[i + 1][j] + dp[i][j + 1];
            }
        }
        return dp[0][0];
    }
}
