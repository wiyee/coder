package netease;

import java.util.Scanner;

/**
 * Created by wiyee on 2018/3/29.
 * 小Q得到一个神奇的数列: 1, 12, 123,...12345678910,1234567891011...。
 * 并且小Q对于能否被3整除这个性质很感兴趣。
 * 小Q现在希望你能帮他计算一下从数列的第l个到第r个(包含端点)有多少个数可以被3整除。
 * <p>
 * 输入描述:
 * 输入包括两个整数l和r(1 <= l <= r <= 1e9), 表示要求解的区间两端。
 * <p>
 * 输出描述:
 * 输出一个整数, 表示区间内能被3整除的数字个数。
 * <p>
 * 输入例子1:
 * 2 5
 * <p>
 * 输出例子1:
 * 3
 * <p>
 * 例子说明1:
 * 12, 123, 1234, 12345...
 * 其中12, 123, 12345能被3整除。
 */
public class Mod3Count {
    private static void main(String[] args) {
        int m, n;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            n = scanner.nextInt();
            m = scanner.nextInt();
            long count = 0;
            int sum = 0;

            for (int i = 1; i < n; i++) {
                count += i;
            }
            for (int i = n; i <= m; i++) {
                count += i;
                if (count % 3 == 0)
                    sum++;
            }
            System.out.println(sum);
        }
    }
}
