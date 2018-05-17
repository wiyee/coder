package fintech;

import java.util.*;

public class T2 {
    public static List<Integer> list = new LinkedList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for (int i = 0; i < t; i++) {
            int n = scanner.nextInt();
            int[] house = new int[n];
            for (int j = 0; j < n; j++) {
                house[j] = scanner.nextInt();
            }
            for (int h = 0; h < house.length; h++) {
                dfs(house, 0, 0, h);
            }
        }
        Collections.sort(list);
        System.out.println(list);
    }

    public static void dfs(int[] candidates, int sum, int index, int start) {
        if (index - start >= candidates.length - 1) {
            list.add(sum);
            return;
        }

        for (int j = index + 2; j < candidates.length; j++) {
            dfs(candidates, sum + candidates[j], j, start);
        }
    }
}
