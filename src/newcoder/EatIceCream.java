package newcoder;

import java.util.Scanner;

/**
 * Created by wiyee on 2018/5/23.
 * 牛牛吃雪糕
 * 最近天气太热了，牛牛每天都要吃雪糕。雪糕有一盒一份、一盒两份、一盒三份这三种包装，牛牛一天可以吃多盒雪糕，但是只能吃六份，吃多了就会肚子疼，吃少了就会中暑。而且贪吃的牛牛一旦打开一盒雪糕，就一定会把它吃完。请问牛牛能健康地度过这段高温期么？
 * 输入描述:
 * 每个输入包含多个测试用例。
 * 输入的第一行包括一个正整数，表示数据组数T(1<=T<=100)。
 * 接下来N行，每行包含四个正整数，表示高温期持续的天数N(1<=N<=10000)，一盒一份包装的雪糕数量A(1<=A<=100000)，一盒两份包装的雪糕数量B(1<=B<=100000)，一盒三份包装的雪糕数量C(1<=A<=100000)。
 * <p>
 * <p>
 * 输出描述:
 * 对于每个用例，在单独的一行中输出结果。如果牛牛可以健康地度过高温期则输出"Yes"，否则输出"No"。
 * <p>
 * 输入例子1:
 * 4
 * 1 1 1 1
 * 2 0 0 4
 * 3 0 2 5
 * 4 24 0 0
 * <p>
 * 输出例子1:
 * Yes
 * Yes
 * No
 * Yes
 */
public class EatIceCream {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();

        for (int i = 0; i < T; i++) {
            boolean flag = true;
            int N = scanner.nextInt();
            int A = scanner.nextInt();
            int B = scanner.nextInt();
            int C = scanner.nextInt();
            int count = 6;
            flag = true;

            while (N > 0) {
                count = 6;

                while (count > 0) {
                    if (C > 0 && count >= 3) {
                        count -= 3;
                        C--;
                        continue;
                    } else if (B > 0 && count >= 2) {
                        count -= 2;
                        B--;
                        continue;
                    } else if (A > 0 && count >= 1) {
                        count -= 1;
                        A--;
                        continue;
                    } else {
                        System.out.println("No");
                        flag = false;
                        break;
                    }
                }

                if (!flag)
                    break;
                N--;
            }

            if (flag)
                System.out.println("Yes");
        }
    }
}