package fintech;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class T1 {
    public static int count = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        if (t > 100 || t < 1)
            return;
        for (int i = 0; i < t; i++) {
            int n = scanner.nextInt();
            int k = scanner.nextInt();
            if (n > 100 || n < 1 || k > 10000 || k < 1)
                return;
            int[] v = new int[n];
            for (int j = 0; j < n; j++) {
                v[j] = scanner.nextInt();
            }

            T1 t1 = new T1();
            int m = t1.combinationSum(v, k);
            System.out.println(m % 100000007);
        }
        ;

    }

    public int combinationSum(int[] candidates, int target) {

        if (candidates.length < 1 || target < 1)
            return 0;
        dfs(candidates, target, 0, 0);

        return count;
    }

    public void dfs(int[] candidates, int target, int index, int sum) {
        if (sum == target) {
            count++;
            return;
        }
        if (sum > target)
            return;
        if (sum < target) {
            for (int i = index; i < candidates.length; i++) {
                if (i > 0 && candidates[i] == candidates[i - 1]) continue;

                dfs(candidates, target, i, sum + candidates[i]);

            }
        }
    }
}
