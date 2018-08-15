package tencent;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by wiyee on 2018/6/4.
 * 小Q今天在上厕所时想到了这个问题：有n个数，两两组成二元组，差最小的有多少对呢？差最大呢？
 * <p>
 * 输入描述:
 * 输入包含多组测试数据。
 * <p>
 * 对于每组测试数据：
 * N - 本组测试数据有n个数
 * a1,a2...an - 需要计算的数据
 * <p>
 * 保证:
 * 1<=N<=100000,0<=ai<=INT_MAX.
 * <p>
 * 输出描述:
 * 对于每组数据，输出两个数，第一个数表示差最小的对数，第二个数表示差最大的对数。
 * <p>
 * 输入例子1:
 * 6
 * 45 12 45 32 5 6
 * <p>
 * 输出例子1:
 * 1 2
 */
public class InterestingNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int N = scanner.nextInt();
            int[] num = new int[N];
            for (int i = 0; i < N; i++) {
                num[i] = scanner.nextInt();
            }
            // 数组从小到大排序
            Arrays.sort(num);
            int min = Integer.MAX_VALUE;
            int maxCount = 0;
            int minCount = 0;
            boolean sign = false;
            for (int i = 1; i < N; i++) {
                if (num[i] == num[i - 1]) {
                    sign = true; // 判断是否有重复数字
                    break;
                }
            }
            int maxC = 0;
            int minC = 0;
            for (int i = N - 1; i >= 0; i--) {
                if (num[i] == num[N - 1]) {
                    maxC++;// 最大值数量
                } else if (num[i] == num[0]) {
                    minC++;
                }
            }
            if (sign) {
                int tempMin = 0;
                for (int i = 1; i < N; i++) {
                    if (num[i] == num[i - 1]) {
                        tempMin++;
                    } else {
                        minCount += tempMin * (tempMin + 1) / 2;
                        tempMin = 0;
                    }
                }
                minCount += tempMin * (tempMin + 1) / 2;
            } else {
                for (int i = 0; i < N; i++) {
                    int count = Math.abs(num[i] - num[i - 1]);
                    if (count <= min) {
                        if (count == min) {
                            minCount++;
                        } else {
                            min = count;
                            minCount = 1;
                        }
                    }
                }
            }

            if (num[N - 1] == num[0]) {
                maxCount = N * (N - 1) / 2;
            } else {
                maxCount = maxC * minC;
            }
            System.out.println(minCount + " " + maxCount);
        }
    }
    /**
     * 暴力穷举 超时
     public static void main(String[] args) {
     Scanner scanner = new Scanner(System.in);
     int N = scanner.nextInt();
     int[] num = new int[N];
     for (int i = 0; i < N; i ++){
     num[i] = scanner.nextInt();
     }
     int min = Integer.MAX_VALUE;
     int minCount = 0;
     int max = Integer.MIN_VALUE;
     int maxCount = 0;
     for (int i = 0; i < N; i ++){
     for (int j = i + 1; j < N; j ++){
     int count = Math.abs(num[i] - num[j]);
     if (count <= min){
     if (count == min){
     minCount ++;
     } else {
     min = count;
     minCount = 1;
     }

     } else if (count >= max){
     if (count == max){
     maxCount ++;
     } else {
     max = count;
     maxCount = 1;
     }
     }
     }
     }
     System.out.println(minCount + " " + maxCount);
     }
     */
}
