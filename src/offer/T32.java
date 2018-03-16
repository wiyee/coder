package offer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wiyee on 2018/3/1.
 */
public class T32 {
    public static void main(String[] args) {
        int count = new T32().NumberOf1Between1AndN_Solution(13);
        System.out.println(count);
    }

    public int NumberOf1Between1AndN_Solution(int n) {
        if (n <= 0)
            return 0;
        int count = 0;
        for (int i = 0; i <= n; i++) {
            count += appearNumber(String.valueOf(i), "1");
        }
        return count;
    }

    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }
}
