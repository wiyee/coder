package newcoder;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CardGameWin {
    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("0.0000");
        Scanner scanner = new Scanner(System.in);
        int a1 = scanner.nextInt();
        int b1 = scanner.nextInt();
        int c1 = scanner.nextInt();
        int a2 = scanner.nextInt();
        int b2 = scanner.nextInt();
        int c2 = scanner.nextInt();
        int total = 0;
        int win = 0;
        int total1 = a1 + b1 + c1;
        int total2 = a2 + b2 + c2;
        int[] count = new int[14];
        for (int i = 0; i < 14; i++) {
            count[i] = 0;
        }
        count[a1]++;
        count[a2]++;
        count[b1]++;
        count[b2]++;
        count[c1]++;
        count[c2]++;

        for (int i = 1; i <= 13; i++) {
            if (count[i] > 3) {
                continue;
            }
            int m = 4 - count[i];
            for (int j = 1; j <= 13; j++) {
                if (i == j) {
                    if (count[j] > 2)
                        continue;
                    else {
                        total += m * (m - 1);
                    }
                } else {
                    if (count[j] > 3)
                        continue;
                    else
                        total += m * (4 - count[j]);
                }
                if (total2 + i < total1 + j) {
                    if (i == j) {
                        win += m * (m - 1);
                    } else {
                        win += m * (4 - count[j]);
                    }

                }
            }
        }
        double winRate = (win * 1.0) / total;
        System.out.println(df.format(winRate));
    }

    public static void putMap(int x, Map<Integer, Integer> map) {
        if (map.containsKey(x)) {
            int count = map.get(x);
            map.put(x, count + 1);
        } else
            map.put(x, 1);
    }
}
