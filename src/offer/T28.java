package offer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by wiyee on 2018/3/2.
 * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
 * 输入一个字符串,长度不超过9(可能有字符重复),字符只包括大小写字母。
 */
public class T28 {

    public ArrayList<String> Permutation(String str) {
        Set<String> set = new TreeSet<>();
        for (int i = 0; i < str.length(); i++) {
            set.add(str.charAt(i) + "");
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : set) {
            stringBuffer.append(s);
        }
        ArrayList<String> res = new ArrayList<>();
        if (str != null && str.length() > 0) {
            PermutationHelper(stringBuffer.toString().toCharArray(), 0, res);
            Collections.sort(res);
        }
        return res;
    }

    private static void PermutationHelper(char[] cs, int i, ArrayList<String> list) {
        if (i == cs.length - 1) { //解空间的一个叶节点
            list.add(String.valueOf(cs)); //找到一个解
        } else {
            for (int j = i; j < cs.length; ++j) {
                if (j == i || cs[j] != cs[i]) {
                    swap(cs, i, j);
                    PermutationHelper(cs, i + 1, list);
                    swap(cs, i, j); //复位
                }
            }
        }
    }

    public static void swap(char[] chars, int i, int j) {
        char aChar = chars[i];
        chars[i] = chars[j];
        chars[j] = aChar;
    }

    public static void main(String[] args) {
        String str = "aa";
        ArrayList<String> list = new T28().Permutation(str);
        for (String s : list) {
            System.out.println(s);
        }
    }
}
