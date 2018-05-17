package leetcode;

/**
 * Created by wiyee on 2018/4/13.
 * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum of all numbers along its path.
 * <p>
 * Note: You can only move either down or right at any point in time.
 * 啊哈哈哈哈 这是做出来的第一道动态规划啊
 */
public class MinPathSum {
    public static void main(String[] args) {
        int[][] grid = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(minPathSum(grid));
    }

    public static int minPathSum(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[][] dp = new int[row][col];
        dp[row - 1][col - 1] = grid[row - 1][col - 1];
        for (int i = col - 2; i >= 0; i--) {// 最后一行
            dp[row - 1][i] = grid[row - 1][i] + dp[row - 1][i + 1];
        }
        for (int i = row - 2; i >= 0; i--) {// 最后一列
            dp[i][col - 1] = grid[i][col - 1] + dp[i + 1][col - 1];
        }
        for (int i = row - 2; i >= 0; i--) {
            for (int j = col - 2; j >= 0; j--) {
                dp[i][j] = grid[i][j] + Math.min(dp[i + 1][j], dp[i][j + 1]);
            }
        }

        return dp[0][0];
    }
}
