package leetcode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wiyee on 2018/4/12.
 */
public class CombinationSum2 {
    public static void main(String[] args) {
        int[] candidates = new int[]{10, 1, 2, 7, 6, 1, 5};
        CombinationSum2 combinationSum2 = new CombinationSum2();
        System.out.println(combinationSum2.combinationSum2(candidates, 8));
    }

    public ArrayList<ArrayList<Integer>> combinationSum2(int[] candidates, int target) {
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
                if (i > index && candidates[i] == candidates[i - 1]) {
                    continue;
                }
                combineList.add(candidates[i]);
                dfs(candidates, target, i + 1, sum + candidates[i], res, combineList);
                combineList.remove(combineList.size() - 1);
            }
        }
    }
}
