package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wiyee on 2018/5/3.
 * Given a set of distinct integers, S, return all possible subsets.
 * <p>
 * Note:
 * <p>
 * Elements in a subset must be in non-descending order.
 * The solution set must not contain duplicate subsets.
 * <p>
 * For example,
 * If S =[1,2,3], a solution is:
 * <p>
 * [
 * [3],
 * [1],
 * [2],
 * [1,2,3],
 * [1,3],
 * [2,3],
 * [1,2],
 * []
 * ]
 * 回溯法结果正确，但未通过
 */
public class Subsets {

    public static ArrayList<ArrayList<Integer>> result = new ArrayList<>();

    public static void main(String[] args) {
        int[] S = new int[]{1, 2, 3};
        Subsets subsets = new Subsets();
        System.out.println(subsets.subsets(S));
    }

    public ArrayList<ArrayList<Integer>> subsets(int[] S) {
        ArrayList<Integer> list = new ArrayList<>();
        if (S.length < 1)
            return new ArrayList<>();
        Arrays.sort(S);
        dfs(S, 0, list);
        return result;
    }

    public static void dfs(int[] S, int index, ArrayList<Integer> list) {
        result.add(new ArrayList<>(list));
        for (int i = index; i < S.length; ++i) {
            list.add(S[i]);
            dfs(S, i + 1, list);
            list.remove(list.size() - 1);
        }
    }
}
