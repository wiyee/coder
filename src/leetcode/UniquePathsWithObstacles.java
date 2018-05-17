package leetcode;

/**
 * Created by wiyee on 2018/4/24.
 * Follow up for "Unique Paths":
 * <p>
 * Now consider if some obstacles are added to the grids. How many unique paths would there be?
 * <p>
 * An obstacle and empty space is marked as1and0respectively in the grid.
 * <p>
 * For example,
 * <p>
 * There is one obstacle in the middle of a 3x3 grid as illustrated below.
 * <p>
 * [
 * [0,0,0],
 * [0,1,0],
 * [0,0,0]
 * ]
 * The total number of unique paths is2.
 * <p>
 * Note: m and n will be at most 100.
 * 啊哈哈哈
 */
public class UniquePathsWithObstacles {
    public static void main(String[] args) {
        int[][] tmp = new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        System.out.println(uniquePathsWithObstacles(tmp));
    }

    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];

        if (obstacleGrid[0][0] == 1 || obstacleGrid[m - 1][n - 1] == 1)
            return 0;
        dp[0][0] = 1;
        for (int i = 1; i < m; i++) { // 第一lie
            if (dp[i - 1][0] == 1 && obstacleGrid[i][0] != 1)
                dp[i][0] = 1;
            else
                dp[i][0] = 0;

        }
        for (int i = 1; i < n; i++) { //第一hang
            if (dp[0][i - 1] == 1 && obstacleGrid[0][i] != 1)
                dp[0][i] = 1;
            else
                dp[0][i] = 0;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1)
                    dp[i][j] = 0;
                else
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
}
