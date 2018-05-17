package offer;

/**
 * Created by wiyee on 2018/3/6.
 * 在一个字符串(1<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置
 * T55
 */
public class T35 {
    int FirstNotRepeatingChar(String str) {
        if (str.trim().length() > 10000 || str.trim().length() < 1)
            return 0;
        int[] chars = new int[100];
        int result = 0;
        for (int i = 0; i < str.length(); i++) {
            chars[str.charAt(i) - 64]++;

        }
        for (int i = 0; i < str.length(); i++) {
            if (chars[str.charAt(i) - 64] == 1) {
                return i + 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int i = new T35().FirstNotRepeatingChar("NXWtnzyoHoBhUJaPauJaAitLWNMlkKwDYbbigdMMaYfkVPhGZcrEwp");
        System.out.println(i);
    }
}
