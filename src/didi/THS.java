package didi;

import java.util.Arrays;

public class THS {
    public static void main(String[] args) {

    }

    public static void stringIndex(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int i = 0;
        int j = 0;
        while (i < len1 && j < len2) {
            if (str1.charAt(i) == str2.charAt(j)) {
                i++;
                j++;
            } else {
                i = i - j + 1;
                j = 0;
            }
            if (j == len2) {
                System.out.println(i - j + 1);
                return;
            }
        }
    }


}