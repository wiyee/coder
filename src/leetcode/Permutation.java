package leetcode;

import java.util.ArrayList;

/**
 * Created by wiyee on 2018/4/12.
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
 * Given n and k, return the k th permutation seque、nce.
 * <p>
 * Note: Given n will be between 1 and 9 inclusive.
 */
public class Permutation {
    public static void main(String[] args) {
        Permutation permutation = new Permutation();
        System.out.println(permutation.getPermutation(3, 1));
    }

    public ArrayList<String> getPermutation(int n, int k) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(Integer.toString(i));
        }

        return list;
    }
}
