package netease;


import java.util.Scanner;

public class T1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int[] a = new int[n];
        int[] t = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int dp[] = new int[100000];
        dp[0] = 0;
        int maxex = 0;
        int interset = 0;
        for (int i = 1; i <= n; i++) {
            int sleep = scanner.nextInt();
            dp[i] = dp[i - 1];
            if (sleep == 1) {
                interset += a[i - 1];
            } else {
                dp[i] += a[i - 1];
            }
            if (i >= k) {
                maxex = Math.max(maxex, dp[i] - dp[i - k]);
            }
        }

        System.out.println(interset + maxex);

    }
}
