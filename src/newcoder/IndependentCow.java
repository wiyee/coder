package newcoder;

import java.util.Scanner;

/**
 * Created by wiyee on 2018/5/23.
 * 独立的牛牛
 */
public class IndependentCow {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        int f = in.nextInt();
        int d = in.nextInt();
        int p = in.nextInt();
        int result = 0;
        if (x * f < d) {
            result = f + (d - x * f) / (p + x);
        } else {
            result = d / x;
        }
        System.out.println(result);
    }
}


