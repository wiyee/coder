package leetcode;

/**
 * Created by wiyee on 2018/5/4.
 * Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.
 * <p>
 * You may assume no duplicates in the array.
 * <p>
 * Here are few examples.
 * [1,3,5,6], 5 → 2
 * [1,3,5,6], 2 → 1
 * [1,3,5,6], 7 → 4
 * [1,3,5,6], 0 → 0
 */
public class SearchInsert {
    public int searchInsert(int[] A, int target) {
        if (A.length == 0) {
            return 0;
        }
        if (target > A[A.length - 1])
            return A.length;
        for (int i = 0; i < A.length; i++) {
            if (A[i] == target) {
                return i;
            } else if (A[i] > target) {
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] tmp = new int[]{1, 3, 5, 6};
        SearchInsert searchInsert = new SearchInsert();
        System.out.println(searchInsert.searchInsert(tmp, 7));
    }
}
