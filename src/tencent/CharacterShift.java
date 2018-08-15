package tencent;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by wiyee on 2018/6/4.
 * 小Q最近遇到了一个难题：把一个字符串的大写字母放到字符串的后面，各个字符的相对位置不变，且不能申请额外的空间。
 * 你能帮帮小Q吗？
 * <p>
 * 输入描述:
 * 输入数据有多组，每组包含一个字符串s，且保证:1<=s.length<=1000.
 * <p>
 * 输出描述:
 * 对于每组数据，输出移位后的字符串。
 * <p>
 * 输入例子1:
 * AkleBiCeilD
 * <p>
 * 输出例子1:
 * kleieilABCD
 */
public class CharacterShift {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.next();
            char[] a = s.toCharArray();
            BubbleSort(a);// 冒泡排序思想
            System.out.println(String.copyValueOf(a));
        }
    }

    public static void BubbleSort(char[] a) {
        int j, k = a.length;
        boolean flag = true;//发生了交换就为true, 没发生就为false，第一次判断时必须标志位true。
        while (flag) {
            flag = false;
            for (j = 1; j < k; j++) {
                if (Character.isUpperCase(a[j - 1]) && !Character.isUpperCase(a[j])) {// 前面的字符是大写字母，后面的字符是小写字母就交换
                    char temp;
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                    flag = true;
                }
            }
            k--;//减小一次排序的尾边界
        }
    }
}
