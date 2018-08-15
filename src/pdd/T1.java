package pdd;

import java.util.Scanner;

public class T1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String strLetter = scanner.next();
            int k = strLetter.length() / 4;
            char[] letterChar = strLetter.toCharArray();
            char[][] result = new char[k + 1][k + 1];
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[i].length; j++) {
                    result[i][j] = '0';
                }
            }
            for (int i = 0; i < k; i++) {
                result[0][i] = letterChar[i];
                result[i][k] = letterChar[i + k];
                result[k][k - i] = letterChar[i + 2 * k];
                result[k - i][0] = letterChar[i + 3 * k];
            }
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[i].length; j++) {
                    if (result[i][j] != '0') {
                        System.out.print(result[i][j]);
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
        }
    }
}