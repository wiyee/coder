package tencent;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by wiyee on 2018/6/5.
 * 小Q定义了一种数列称为翻转数列:
 * 给定整数n和m, 满足n能被2m整除。对于一串连续递增整数数列1, 2, 3, 4..., 每隔m个符号翻转一次, 最初符号为'-';。
 * 例如n = 8, m = 2, 数列就是: -1, -2, +3, +4, -5, -6, +7, +8.
 * 而n = 4, m = 1, 数列就是: -1, +2, -3, + 4.
 * 小Q现在希望你能帮他算算前n项和为多少。
 * 输入描述:
 * 输入包括两个整数n和m(2 <= n <= 109, 1 <= m), 并且满足n能被2m整除。
 * <p>
 * <p>
 * 输出描述:
 * 输出一个整数, 表示前n项和。
 * <p>
 * 输入例子1:
 * 8 2
 * <p>
 * 输出例子1:
 * 8
 */
public class ReverseNumberList {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            BigInteger A = new BigInteger(String.valueOf(n));
            BigInteger B = new BigInteger(String.valueOf(m));
            System.out.println(A.multiply(B).divide(new BigInteger("2")));
        }
    }
}
