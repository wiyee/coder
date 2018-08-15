package newcoder;

import java.util.Scanner;

public class T3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int x = scanner.nextInt();// 房租
            int f = scanner.nextInt();// 已有水果
            int d = scanner.nextInt();// 已有钱
            int p = scanner.nextInt(); // 水果价格
            int result = 0;
            if (d < x * f) {
                result = d / x;
            } else {
                result = f + (d - f * x) / (x + p);
            }
            System.out.println(result);

        }

    }
}
