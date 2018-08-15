import java.util.Scanner;

public class Main {
    public static long[][] C = new long[500][500];

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int k;
        int n, m;
        getC();
        n = sc.nextInt();
        m = sc.nextInt();
        k = sc.nextInt();
//		System.out.println(C[n+m][m]);
        if (C[n + m][m] < k) {
            System.out.println(-1);
            return;
        }
        while (m + n > 0) {
            int t = n + m - 1;
            if (n > 0) {
                if (C[t][m] >= k) {
                    n--;
                    System.out.print('a');
                } else {
                    k -= C[t][m];
                    m--;
                    System.out.print('z');

                }
            } else {
                m--;
                System.out.print('z');
            }


        }
    }

    public static void getC() {
        C[0][0] = 1;
        for (int i = 1; i < 300; ++i) {
            C[i][0] = C[i][i] = 1;
            for (int j = 1; j < i; ++j) {
                C[i][j] = C[i - 1][j] + C[i - 1][j - 1];
            }
        }
    }
}