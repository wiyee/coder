package leetcode;

import java.time.Instant;
import java.util.List;

/**
 * Created by wiyee on 2018/5/15.
 * <p>
 * The set[1,2,3,…,n]contains a total of n! unique permutations.
 * <p>
 * By listing and labeling all of the permutations in order,
 * We get the following sequence (ie, for n = 3):
 * <p>
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 * <p>
 * Given n and k, return the k th permutation sequence.
 * <p>
 * Note: Given n will be between 1 and 9 inclusive.
 */
public class GetPermutation {
    public static int count;
    public static String result;

    public String getPermutation(int n, int k) {
        char[] arr = new char[n];
        for (int i = 0; i < n; i++)
            arr[i] = (char) ('1' + i);
        permutation(arr, 0, k);
        return result;
    }

    public void permutation(char[] arr, int index, int k) {
        if (index == arr.length) {
            count++;
            if (count == k) {
                result = String.valueOf(arr);
                return;
            }
        }
        for (int m = index; m < arr.length; m++) {
            swap(arr, index, m);
            permutation(arr, index + 1, k);
            swap(arr, index, m);
        }

    }

    private void swap(char[] chars, int i, int j) {
        char tmp = chars[i];
        chars[i] = chars[j];
        chars[j] = tmp;
    }


    public static void main(String[] args) {
        Instant now = Instant.now();
        System.out.println(new GetPermutation().getPermutation(2, 1));
    }

}
