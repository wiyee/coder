package netease;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Created by wiyee on 2018/5/16.
 * 一个由小写字母组成的字符串可以看成一些同一字母的最大碎片组成的。例如,"aaabbaaac"是由下面碎片组成的:'aaa','bb','c'。牛牛现在给定一个字符串,请你帮助计算这个字符串的所有碎片的平均长度是多少。
 * <p>
 * 输入描述:
 * 输入包括一个字符串s,字符串s的长度length(1 ≤ length ≤ 50),s只含小写字母('a'-'z')
 * <p>
 * <p>
 * 输出描述:
 * 输出一个整数,表示所有碎片的平均长度,四舍五入保留两位小数。
 * <p>
 * 如样例所示: s = "aaabbaaac"
 * 所有碎片的平均长度 = (3 + 2 + 3 + 1) / 4 = 2.25
 * <p>
 * 输入例子1:
 * aaabbaaac
 * <p>
 * 输出例子1:
 * 2.25
 */
public class FragmentString {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            if (str.length() < 1)
                System.out.println(0);
            if (str.length() == 1)
                System.out.println(1);
            int count = 1;
            int index = 0;
            int sum = 0;
            for (int i = 0; i < str.length() - 1; i++) {
                if (i + 1 < str.length() - 1) {
                    if (str.charAt(i) == str.charAt(i + 1)) {
                        count++;
                    } else {
                        sum += count;
                        count = 1;
                        index++;
                    }
                } else {
                    if (str.charAt(i) == str.charAt(i + 1)) {
                        sum = sum + count + 1;
                        index++;
                    } else {
                        sum += count + 1;
                        index += 2;
                    }
                }
            }
            DecimalFormat df = new DecimalFormat("#.00");
            System.out.println(df.format(sum * 1.0 / index));
        }
    }
}
