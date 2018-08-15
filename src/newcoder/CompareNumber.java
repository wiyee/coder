package newcoder;

import java.util.Scanner;

public class CompareNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            if (x == y) {
                System.out.println("=");
            } else {
                if (x * Math.log(y) == y * Math.log(x))
                    System.out.println("=");
                else if (x * Math.log(y) > y * Math.log(x))
                    System.out.println("<");
                else
                    System.out.println(">");
            }


        }
    }
}
