package leetcode;

import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by wiyee on 2018/5/9.
 * Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.
 * <p>
 * Do not allocate extra space for another array, you must do this in place with constant memory.
 * <p>
 * For example,
 * Given input array A =[1,1,2],
 * <p>
 * Your function should return length =2, and A is now[1,2].
 */
public class RemoveDuplicates {
    public static void main(String[] args) {
        RemoveDuplicates removeDuplicates = new RemoveDuplicates();
        int[] A = new int[]{1, 1, 2};
        System.out.println(removeDuplicates.removeDuplicates(A));
        for (int i : A) {
            System.out.println(i);
        }
    }

    public int removeDuplicates(int[] A) {
        if (A.length < 2) {
            return A.length;
        }
        int index = 1;
        for (int i = 1; i < A.length; i++) {
            if (A[i - 1] != A[i]) {
                A[index++] = A[i];
            }
        }
        return index;
    }
}
