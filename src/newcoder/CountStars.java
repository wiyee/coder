package newcoder;

import java.util.Scanner;

public class CountStars {
    /**
     * 一闪一闪亮晶晶，满天都是小星星，牛牛晚上闲来无聊，便躺在床上数星星。
     * 牛牛把星星图看成一个平面，左上角为原点(坐标为(1, 1))。现在有n颗星星，他给每颗星星都标上坐标(xi，yi)，表示这颗星星在第x行，第y列。
     * 现在，牛牛想问你m个问题，给你两个点的坐标(a1, b1)(a2，b2)，表示一个矩形的左上角的点坐标和右下角的点坐标，请问在这个矩形内有多少颗星星（边界上的点也算是矩形内）。
     * 输入描述:
     * 第一行输入一个数字n(1≤n≤100000)，表示星星的颗数。
     * 接下来的n行，每行输入两个数xi和yi(1≤xi，yi≤1000），表示星星的位置。
     * 然后输入一个数字m(1≤m≤100000), 表示牛牛询问问题的个数。
     * 接下来m行，每行输入四个数字a1，b1，a2，b2(1≤a1＜a2≤1000), (1≤b1＜b2≤1000）
     * 题目保证两颗星星不会存在于同一个位置。
     * <p>
     * 输出描述:
     * 输出一共包含m行，每行表示与之对应的每个问题的答案。
     * <p>
     * 输入例子1:
     * 4
     * 1 1
     * 2 2
     * 3 3
     * 1 3
     * 4
     * 1 1 2 2
     * 1 1 3 3
     * 2 2 3 3
     * 1 2 2 3
     * <p>
     * 输出例子1:
     * 2
     * 4
     * 2
     * 2
     * 用一个二维数组dp[i][j]存储从（1，1）到（i，j）的矩阵内有多少数据，预处理输入dp[i][j] = dp[i-1][j]+add（add是a[i][1]到a[i][j]有多少星星数，预处理复杂度最大o（10w））
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();//星星个数
        int[] x = new int[n];
        int[] y = new int[n];
        int maxX = 0;
        int maxY = 0;
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
            if (x[i] > maxX)
                maxX = x[i];
            if (y[i] > maxY)
                maxY = y[i];
        }
        int[][] star = new int[maxX + 1][maxY + 1];
        int[][] dp = new int[maxX + 1][maxY + 1];
        for (int i = 0; i < star.length; i++) {
            for (int j = 0; j < star[i].length; j++) {
                star[i][j] = 0;
            }
        }
        for (int i = 0; i < n; i++) {
            star[x[i]][y[i]] = 1;
        }
        dp[1][1] = star[1][1];
        for (int i = 2; i < star.length; i++) {
            dp[i][1] = dp[i - 1][1] + star[i][1];
        }
        for (int i = 2; i < star[0].length; i++) {
            dp[1][i] = dp[1][i - 1] + star[1][i];
        }
        for (int i = 2; i < star.length; i++) {
            for (int j = 2; j < star[i].length; j++) {
                int count = 0;
                for (int k = 1; k <= j; k++) {
                    count += star[i][k];
                }
                dp[i][j] = dp[i - 1][j] + count;
            }
        }
        int m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            int a1 = scanner.nextInt();
            int b1 = scanner.nextInt();
            int a2 = scanner.nextInt();
            int b2 = scanner.nextInt();
            if (a2 > maxX)
                a2 = maxX;
            if (b2 > maxY)
                b2 = maxY;
            System.out.println(dp[a2][b2] - dp[a2][b1 - 1] - dp[a1 - 1][b2] + dp[a1 - 1][b1 - 1]);
        }
    }
}
