package leetcode;

import java.util.ArrayList;

/**
 * Created by wiyee on 2018/4/24.
 * Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.
 * <p>
 * For example, given the following triangle
 * <p>
 * [
 * [2],
 * [3,4],
 * [6,5,7],
 * [4,1,8,3]
 * ]
 * <p>
 * The minimum path sum from top to bottom is11(i.e., 2 + 3 + 5 + 1 = 11).
 * <p>
 * Note:
 * Bonus point if you are able to do this using only O(n) extra space, where n is the total number of rows in the triangle.
 */
public class MiniMum {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> col = new ArrayList<>();
        ArrayList<Integer> row = new ArrayList<>();
        row.add(2);
        col.add(new ArrayList<>(row));
        row.clear();
        row.add(3);
        row.add(4);
        col.add(new ArrayList<>(row));
        row.clear();
        row.add(6);
        row.add(5);
        row.add(7);
        col.add(new ArrayList<>(row));
        row.clear();
        row.add(4);
        row.add(1);
        row.add(8);
        row.add(3);
        col.add(new ArrayList<>(row));

        System.out.println(minimumTotal(col));
    }

    public static int minimumTotal(ArrayList<ArrayList<Integer>> triangle) {
        int length = triangle.size();
        for (int i = length - 2; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                triangle.get(i).set(j, Math.min(triangle.get(i + 1).get(j), triangle.get(i + 1).get(j + 1)) + triangle.get(i).get(j));
            }
        }
        return triangle.get(0).get(0);
    }
}
