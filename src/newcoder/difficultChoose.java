package newcoder;


import java.util.Scanner;

public class difficultChoose {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
            while (a[i] % 2 == 0) {
                a[i] /= 2;
                count++;
            }
        }
        System.out.println(count);

    }

}
