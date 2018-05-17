package leetcode;

/**
 * Created by wiyee on 2018/5/15.
 * The count-and-say sequence is the sequence of integers beginning as follows:
 * 1, 11, 21, 1211, 111221, ...
 * <p>
 * 1is read off as"one 1"or11.
 * 11is read off as"two 1s"or21.
 * 21is read off as"one 2, thenone 1"or1211.
 * <p>
 * Given an integer n, generate the n th sequence.
 * <p>
 * Note: The sequence of integers will be represented as a string.
 */
public class CountAndSay {
    public static void main(String[] args) {
        CountAndSay countAndSay = new CountAndSay();
        System.out.println(countAndSay.countAndSay(10));
    }

    public String countAndSay(int n) {
        if (n < 1)
            return "";
        String res = "1";
        while (--n > 0) {
            int size = 1;
            StringBuilder sb = new StringBuilder();
            for (int j = 1; j < res.length(); j++) {
                if (res.charAt(j) == res.charAt(j - 1)) {
                    size++;
                } else {
                    sb.append(size).append(res.charAt(j - 1));
                    size = 1;
                }
            }
            sb.append(size).append(res.charAt(res.length() - 1));
            res = sb.toString();
        }
        return res;
    }
}
