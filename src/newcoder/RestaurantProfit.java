package newcoder;

import java.util.Scanner;

/**
 * Created by wiyee on 2018/6/22.
 * 题目描述
 * 某餐馆有n张桌子，每张桌子有一个参数：a 可容纳的最大人数； 有m批客人，每批客人有两个参数:b人数，c预计消费金额。 在不允许拼桌的情况下，请实现一个算法选择其中一部分客人，使得总预计消费金额最大
 * 输入描述:
 * 输入包括m+2行。 第一行两个整数n(1 <= n <= 50000),m(1 <= m <= 50000) 第二行为n个参数a,即每个桌子可容纳的最大人数,以空格分隔,范围均在32位int范围内。 接下来m行，每行两个参数b,c。分别表示第i批客人的人数和预计消费金额,以空格分隔,范围均在32位int范围内。
 * 输出描述:
 * 输出一个整数,表示最大的总预计消费金额
 * 示例1
 * 输入
 * 复制
 * 3 5 2 4 2 1 3 3 5 3 7 5 9 1 10
 * 输出
 * 复制
 * 20
 */
public class RestaurantProfit {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            int m = in.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }

            for (int i = 0; i < n; i++) {

            }
        }
        in.close();
    }
}
