package pdd;


import java.util.Arrays;
import java.util.Scanner;

public class T2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int result = 0;
            String num = scanner.nextLine();
            int[] intNum = new int[String.valueOf(num).length()];
            char[] charNum = String.valueOf(num).toCharArray();
            for (int i = 0; i < intNum.length; i++) {
                intNum[i] = charNum[i] - '0';
            }
            for (int i = 1; i < intNum.length; i++) {
                String left = String.valueOf(num).substring(0, i);
                String right = String.valueOf(num).substring(i, intNum.length);
                int intLeft = Integer.parseInt(left);
                int intRight = Integer.parseInt(right);
                // no dot
                if (String.valueOf(intLeft).equals(left) && String.valueOf(intRight).equals(right))
                    result++;
                if (String.valueOf(intRight).equals(right)) {
                    if (i > 1 && intNum[i - 1] != 0) {
                        if (intNum[0] == 0)
                            result++;
                        else
                            result += i;
                    }
                }
                if (String.valueOf(intLeft).equals(left)) {
                    if (i < intNum.length - 2 && intNum[intNum.length - 1] != 0) {
                        if (intNum[i] == 0)
                            result++;
                        else
                            result += i;
                    }
                }
            }
            System.out.println(result);
        }
    }
}
