package leetcode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wiyee on 2018/4/12.
 */
public class CombinationSum {
    public static void main(String[] args) {
        CombinationSum combinationSum = new CombinationSum();
        int[] candidates = new int[]{8, 7, 4, 3};
        System.out.println(combinationSum.combinationSum(candidates, 11));
    }

    public ArrayList<ArrayList<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        if (candidates.length < 1 || target < 1)
            return lists;
        dfs(candidates, target, 0, 0, lists, new ArrayList<Integer>());

        return lists;
    }

    public void dfs(int[] candidates, int target, int index, int sum, ArrayList<ArrayList<Integer>> res, ArrayList<Integer> combineList) {
        if (sum == target) {
            res.add(new ArrayList<>(combineList));
            return;
        }
        if (sum > target)
            return;
        if (sum < target) {
            for (int i = index; i < candidates.length; i++) {
                if (i > 0 && candidates[i] == candidates[i - 1]) continue;
                combineList.add(candidates[i]);
                dfs(candidates, target, i, sum + candidates[i], res, combineList);
                combineList.remove(combineList.size() - 1);
            }
        }
    }
}
