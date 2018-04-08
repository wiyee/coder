package netease;

import java.util.Scanner;


public class Bag {
    /**
     * 牛牛准备参加学校组织的春游, 出发前牛牛准备往背包里装入一些零食, 牛牛的背包容量为w。
     * 牛牛家里一共有n袋零食, 第i袋零食体积为v[i]。
     * 牛牛想知道在总体积不超过背包容量的情况下,他一共有多少种零食放法(总体积为0也算一种放法)。
     * <p>
     * 输入描述:
     * 输入包括两行
     * 第一行为两个正整数n和w(1 <= n <= 30, 1 <= w <= 2 * 10^9),表示零食的数量和背包的容量。
     * 第二行n个正整数v[i](0 <= v[i] <= 10^9),表示每袋零食的体积。
     * <p>
     * 输出描述:
     * 输出一个正整数, 表示牛牛一共有多少种零食放法。
     * <p>
     * 输入例子1:
     * 3 10
     * 1 2 4
     * <p>
     * 输出例子1:
     * 8
     * <p>
     * 例子说明1:
     * 三种零食总体积小于10,于是每种零食有放入和不放入两种情况，一共有2*2*2 = 8种情况。
     */
    public static int count = 0;

    private static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int w = scanner.nextInt();

        long sum = 0;
        int[] v = new int[n];
        for (int i = 0; i < n; i++) {
            v[i] = scanner.nextInt();
            sum += v[i];
        }
        if (sum <= w) {
            System.out.println((int) Math.pow(2, n));
        } else {
            dfs(0, 0, n, v, w);
            System.out.println(count + 1);
        }

    }

    private static void dfs(long sum, int cur, int n, int[] v, int w) {
        if (cur < n) {
            if (sum > w)
                return;
            dfs(sum, cur + 1, n, v, w);
            if (sum + v[cur] <= w) {
                count++;
                dfs(sum + v[cur], cur + 1, n, v, w);
            }
        }
    }

}