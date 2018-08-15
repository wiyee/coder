package tencent;

import java.util.Scanner;

/**
 * meizuochulai wohennanshou taifuzale qingkuanghaoduo xielehaoduozhongfangfa wodetian yigexiaoshiguoqule buxielebuxiele
 * Created by wiyee on 2018/6/5.
 * 小Q的父母要出差N天，走之前给小Q留下了M块巧克力。小Q决定每天吃的巧克力数量不少于前一天吃的一半，但是他又不想在父母回来之前的某一天没有巧克力吃，请问他第一天最多能吃多少块巧克力
 * 输入描述:
 * 每个输入包含一个测试用例。
 * 每个测试用例的第一行包含两个正整数，表示父母出差的天数N(N<=50000)和巧克力的数量M(N<=M<=100000)。
 * <p>
 * <p>
 * 输出描述:
 * 输出一个数表示小Q第一天最多能吃多少块巧克力。
 * <p>
 * 输入例子1:
 * 3 7
 * <p>
 * 输出例子1:
 * 4
 */
public class GreedyQ {
    private static int maxC = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            int a1 = (int) Math.floor(m / (Math.pow(2, n) - 1));
            int temp = (int) Math.floor(a1 * Math.pow(2, n - 1));
            if (temp != 0) {
                System.out.println(temp);
                continue;
            }
            dfs(n, m, 1, 1, 1);
            System.out.println(maxC);
        }
    }

    private static void dfs(int n, int m, int index, int sum, int numberOfC) {
        int tot = sum + 2 * numberOfC;
        if (tot <= m && index + 1 <= n) {
            dfs(n, m, index + 1, tot, 2 * numberOfC);
        } else {
            if (tot > m) {
                if (sum + n - index <= m - tot) {
                    if (numberOfC > maxC)
                        maxC = numberOfC;
                }
            } else if (index + 1 > n) {
                if (numberOfC > maxC)
                    maxC = numberOfC;
            }
        }
        if (tot + 1 <= m && index + 1 <= n) {
            dfs(n, m, index + 1, tot + 1, 2 * numberOfC + 1);
        } else {
            if (tot + 1 > m) {
                if (sum + n - index <= m - tot - 1) {
                    if (numberOfC > maxC)
                        maxC = numberOfC;
                }
            } else if (index + 1 > n) {
                if (numberOfC > maxC)
                    maxC = numberOfC;
            }
        }
    }
}
