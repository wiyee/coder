package leetcode;

import java.util.Arrays;

/**
 * Created by wiyee on 2018/5/9.
 * Given an array with n objects colored red, white or blue, sort them so that objects of the same color are adjacent, with the colors in the order red, white and blue.
 * <p>
 * Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
 * <p>
 * Note:
 * You are not suppose to use the library's sort function for this problem.
 * <p>
 * click to show follow up.
 * <p>
 * Follow up:
 * A rather straight forward solution is a two-pass algorithm using counting sort.
 * First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's, then 1's and followed by 2's.
 * <p>
 * Could you come up with an one-pass algorithm using only constant space?
 */
public class SortColors {
    public static void main(String[] args) {
        int[] A = new int[]{1, 2, 0, 1, 2, 2, 2, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2};
        SortColors sortColors = new SortColors();
        sortColors.sortColors(A);
        for (int i : A) {
            System.out.println(i);
        }
    }

    public void sortColors(int[] A) {
        int indexZero = 0;
        int indexTwo = A.length - 1;
        int j = 0;
        while (j < indexTwo + 1) {
            if (A[j] == 0) {
                int tmp = A[indexZero];
                A[indexZero] = A[j];
                A[j] = tmp;
                j++;
                indexZero++;
            } else if (A[j] == 2) {
                int tmp = A[indexTwo];
                A[indexTwo] = A[j];
                A[j] = tmp;
                indexTwo--;
            } else {
                j++;
            }
        }
    }

//    public void sortColors(int[] A) {
//        int[] count = new int[]{0,0,0};
//        for (int i = 0; i < A.length; i ++){
//            if (A[i] == 0)
//                count[0] ++;
//            if (A[i] == 1)
//                count[1] ++;
//            if (A[i] == 2)
//                count[2] ++;
//        }
//        for (int i = 0; i < count[0]; i ++){
//            A[i] = 0;
//        }
//        for (int i = count[0]; i < count[0] + count[1]; i ++){
//            A[i] = 1;
//        }
//        for (int i = count[1] + count[0]; i < count[0] + count[1] + count[2]; i ++){
//            A[i] = 2;
//        }
//    }
}
