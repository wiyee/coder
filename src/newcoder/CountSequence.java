package newcoder;

import java.util.Scanner;
import java.util.Stack;

public class CountSequence {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int result = 0;
        String[] sequence = new String[n];
        int[] left = new int[n];
        int[] right = new int[n];
        for (int i = 0; i < n; i++) {
            sequence[i] = scanner.next();
            char[] c = sequence[i].toCharArray();
            int le = 0;
            int ri = 0;
            for (int j = 0; j < c.length; j++) {
                if (c[j] == '(')
                    le++;
                else
                    ri++;
            }
            left[i] = le;
            right[i] = ri;
        }
        for (int i = 0; i < n; i++) {
            if (isTrueSequence(sequence[i] + sequence[i]))
                result++;
            for (int j = i + 1; j < n; j++) {
                if (right[i] + right[j] != left[i] + left[j])
                    continue;
                if (sequence[i].charAt(0) == '(' && sequence[j].charAt(sequence[j].length() - 1) == ')' && isTrueSequence(sequence[i] + sequence[j]))
                    result++;
                if (sequence[j].charAt(0) == '(' && isTrueSequence(sequence[j] + sequence[i]))
                    result++;
            }
        }
        System.out.println(result);
    }

    public static boolean isTrueSequence(String str) {
        char[] c = str.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < c.length; i++) {
            if (stack.isEmpty()) {
                if (c[i] == '(')
                    stack.push(c[i]);
                else
                    return false;
            } else {
                if (c[i] == ')')
                    stack.pop();
                else
                    stack.push('(');
            }
        }
        if (stack.isEmpty())
            return true;
        else
            return false;

    }

}
