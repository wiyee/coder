package newcoder;

import java.util.Scanner;

/**
 * Created by wiyee on 2018/6/22.
 * 题目描述
 * 小易去附近的商店买苹果，奸诈的商贩使用了捆绑交易，只提供6个每袋和8个每袋的包装(包装不可拆分)。 可是小易现在只想购买恰好n个苹果，小易想购买尽量少的袋数方便携带。如果不能购买恰好n个苹果，小易将不会购买。
 * 输入描述:
 * 输入一个整数n，表示小易想购买n(1 ≤ n ≤ 100)个苹果
 * 输出描述:
 * 输出一个整数表示最少需要购买的袋数，如果不能买恰好n个苹果则输出-1
 * 示例1
 * 输入
 * 复制
 * 20
 * 输出
 * 复制
 * 3
 */
public class BuyApple {

    /**
     * 动态规划
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();

        }
        in.close();
    }

    /**
     * 数学分析法
     * @param args
     */
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        while(scanner.hasNext()){
//            int n = scanner.nextInt();
//            if (n % 2 != 0 || n == 10 || n < 6){
//                System.out.println(-1);
//                continue;
//            }else {
//                if (n % 8 == 0){
//                    System.out.println(n/8);
//                } else
//                    System.out.println(n/8 + 1);
//            }
//
//        }
//        scanner.close();
//    }
}
