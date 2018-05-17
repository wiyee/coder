package leetcode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wiyee on 2018/4/13.
 * Given a collection of integers that might contain duplicates, S, return all possible subsets.
 * <p>
 * Note:
 * <p>
 * Elements in a subset must be in non-descending order.
 * The solution set must not contain duplicate subsets.
 * <p>
 * For example,
 * If S =[1,2,2], a solution is:
 * <p>
 * [
 * [2],
 * [1],
 * [1,2,2],
 * [2,2],
 * [1,2],
 * []
 * ]
 */
public class SubsetsWithDup {

    public static void main(String[] args) {
        int[] num = new int[]{1, 2, 2};
        System.out.println(new SubsetsWithDup().subsetsWithDup(num));
    }

    /**
     * 回溯法
     *
     * @param num
     * @return
     */
    public ArrayList<ArrayList<Integer>> subsetsWithDup(int[] num) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (num.length < 1) {
            res.add(new ArrayList<Integer>());
            return res;
        }
        Arrays.sort(num);
        dfs(res, new ArrayList<Integer>(), 0, num);
        return res;
    }

    private void dfs(ArrayList<ArrayList<Integer>> res, ArrayList<Integer> subset, int index, int[] num) {
        if (index <= num.length)
            res.add(new ArrayList<>(subset));

        for (int i = index; i < num.length; i++) {
            if (i > index && num[i] == num[i - 1])
                continue;
            subset.add(num[i]);
            dfs(res, subset, i + 1, num);
            subset.remove(subset.size() - 1);
        }

    }

    /**
     * 动态规划
     *
     * @param num
     * @return
     */
    public ArrayList<ArrayList<Integer>> subsetsWithDup2(int[] num) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (num.length < 1) {
            res.add(new ArrayList<Integer>());
            return res;
        }
        return res;
    }
}
