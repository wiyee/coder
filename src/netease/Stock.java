package netease;

import java.util.Map;
import java.util.Scanner;

/**
 * Created by wiyee on 2018/5/25.
 * 牛牛得知了一些股票今天买入的价格和明天卖出的价格，他找犇犇老师借了一笔钱，现在他想知道他最多能赚多少钱。
 * 输入描述:
 * 每个输入包含一个测试用例。
 * 输入的第一行包括两个正整数,表示股票的种数N(1<=N<=1000)和牛牛借的钱数M(1<=M<=1000)。
 * 接下来N行，每行包含两个正整数，表示这只股票每一股的买入价X(1<=X<=1000)和卖出价Y(1<=Y<=2000)。
 * 每只股票可以买入多股，但必须是整数。
 * <p>
 * <p>
 * 输出描述:
 * 输出一个整数表示牛牛最多能赚的钱数。
 * <p>
 * 输入例子1:
 * 3 5
 * 3 6
 * 2 3
 * 1 1
 * <p>
 * 输出例子1:
 * 4
 */
public class Stock {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int[] X = new int[N];
        int[] Y = new int[N];
        int[] W = new int[N]; // 每只股票的盈利额
        for (int i = 0; i < N; i++) {
            X[i] = scanner.nextInt();
            Y[i] = scanner.nextInt();
            W[i] = Y[i] - X[i];
        }
        int[][] dp = new int[N + 1][M + 1];
        dp[0][0] = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= M; j++) {
                if (j >= X[i])
                    dp[i + 1][j] = Math.max(dp[i][j], dp[i + 1][j - X[i]] + W[i]);
                else
                    dp[i + 1][j] = dp[i][j];
            }
        }
        System.out.println(dp[N][M]);
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
    }
}
