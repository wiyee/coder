package leetcode;

import java.util.ArrayList;

/**
 * Created by wiyee on 2018/4/12.
 * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
 * <p>
 * 例如，
 * 如果 n = 4 和 k = 2，组合如下：
 * <p>
 * [
 * [2,4],
 * [3,4],
 * [2,3],
 * [1,2],
 * [1,3],
 * [1,4],
 * ]
 */
public class Combinations {
    public static void main(String[] args) {
        System.out.println(new Combinations().combine(4, 2));
    }

    public ArrayList<ArrayList<Integer>> combine(int n, int k) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (n < 1 || k < 1 || k > n)
            return res;
        dfs(res, new ArrayList<Integer>(), n, k, 0, 0);

        return res;
    }

    private void dfs(ArrayList<ArrayList<Integer>> res, ArrayList<Integer> combineList, int n, int k, int index, int sum) {
        if (sum == k) {
            res.add(new ArrayList<>(combineList));
            return;
        }
        for (int i = index + 1; i <= n; i++) {
            combineList.add(i);
            dfs(res, combineList, n, k, i, sum + 1);
            combineList.remove(combineList.size() - 1);
        }

    }
}
