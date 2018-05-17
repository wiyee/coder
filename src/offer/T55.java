package offer;

import java.util.HashMap;

/**
 * Created by wiyee on 2018/3/6.
 * 题目描述
 请实现一个函数用来找出字符流中第一个只出现一次的字符。例如，当从字符流中只读出前两个字符"go"时，第一个只出现一次的字符是"g"。当从该字符流中读出前六个字符“google"时，第一个只出现一次的字符是"l"。
 输出描述:
 如果当前字符流没有存在出现一次的字符，返回#字符。
 */
public class T55 {
    private static int[] count = new int[256];
    private static int index = 0;

    public T55() {
        for (int i = 0; i < 256; i++) {
            count[i] = -1;
        }
    }

    public void Insert(char ch) {
        if (count[ch] == -1) {
            count[ch] = index;
        } else if (count[ch] >= 0) {
            count[ch] = -2;
        }
        index++;

    }

    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce() {
        int tmp = Integer.MAX_VALUE;
        char ch = '\0';
        for (int i = 0; i < 256; i++) {
            if (count[i] >= 0 && count[i] < tmp) {
                tmp = count[i];
                ch = (char) i;
            }
        }
        if (ch == '\0')
            return '#';
        return ch;
    }

    public static void main(String[] args) {

    }
}
