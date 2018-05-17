package leetcode;

import java.util.Arrays;

/**
 * Created by wiyee on 2018/5/9.
 * Given an array of integers, every element appears three times except for one. Find that single one.
 * <p>
 * Note:
 * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
 * <p>
 * 没看懂
 */
public class SingleNumber {

    public static void main(String[] args) {
        SingleNumber singleNumber = new SingleNumber();
        int[] A = new int[]{1};
        System.out.println(singleNumber.singleNumber(A));
    }

    public int singleNumber(int[] A) {
        Arrays.sort(A);
        for (int i = 0; i < A.length - 1; i += 3) {
            if (A[i] != A[i + 1])
                return A[i];
        }
        return A[A.length - 1];
    }

//    public int singleNumber(int[] A) {
//        int ones = 0;//记录只出现过1次的bits
//        int twos = 0;//记录只出现过2次的bits
//        int threes;
//        for(int i = 0; i < A.length; i++){
//            int t = A[i];
//            twos |= ones&t;//要在更新ones前面更新twos
//             ones ^= t;
//             threes = ones&twos;//ones和twos中都为1即出现了3次
//             ones &= ~threes;//抹去出现了3次的bits
//             twos &= ~threes;
//         }
//         return ones;
//    }
}
