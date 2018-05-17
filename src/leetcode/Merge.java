package leetcode;

/**
 * Created by wiyee on 2018/5/7.
 * You may assume that A has enough space to hold additional elements from B. The number of elements initialized in A and B are m and n respectively.
 */
public class Merge {
    public static void main(String[] args) {
        int[] A = new int[]{1, 3, 5, 7, 9};

        int[] B = new int[]{2, 4, 6, 8, 10, 12};
        Merge merge = new Merge();
        merge.merge(A, 10, B, 6);
        for (int i = 0; i < A.length; i++) {
            System.out.println(A[i]);
        }
    }

    public void merge(int A[], int m, int B[], int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (A[i] < B[j]) {
                A[k--] = B[j--];
            } else {
                A[k--] = A[i--];
            }
        }
        while (i >= 0) {
            A[k--] = A[i--];
        }
        while (j >= 0) {
            A[k--] = B[j--];
        }

    }
}
