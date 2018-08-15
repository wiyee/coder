package netease;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int[] a = new int[n + 1];
        a[0] = 0;
        for (int i = 0; i < n; i++) {
            a[i + 1] = scanner.nextInt() + a[i];
        }
        int m = scanner.nextInt();

        int[] q = new int[m];
        for (int i = 0; i < m; i++) {
            q[i] = scanner.nextInt();
        }
        for (int i = 0; i < m; i++) {
            int left = 0;
            int right = n + 1;
            int mid = (left + right) / 2;
            while (true) {
                if (a[mid] >= q[i] && a[mid - 1] < q[i]) {
                    System.out.println(mid);
                    break;
                } else if (a[mid] < q[i]) {
                    left = mid;
                    mid = (left + right) / 2;
                } else if (a[mid - 1] >= q[i]) {
                    right = mid;
                    mid = (left + right) / 2;
                }
            }
        }
    }
}
