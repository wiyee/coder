package offer;

/**
 * Created by wiyee on 2018/4/10.
 */
public class T52 {
    public static void main(String[] args) {
        int[] A = new int[]{1, 2, 3, 4, 5, 6};
        int[] B = multiply2(A);
        for (int i : B)
            System.out.println(i);
    }

    public static int[] multiply(int[] A) {
        int[] B = new int[A.length];
        for (int i = 0; i < B.length; i++) {
            B[i] = 1;
        }
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                if (j != i)
                    B[i] *= A[j];
            }
        }
        return B;
    }

    public static int[] multiply2(int[] A) {
        int length = A.length;
        int[] B = new int[length];
        if (length != 0) {
            B[0] = 1;
            //计算下三角连乘
            for (int i = 1; i < length; i++) {
                B[i] = B[i - 1] * A[i - 1];
            }
            int temp = 1;
            //计算上三角
            for (int j = length - 2; j >= 0; j--) {
                temp *= A[j + 1];
                B[j] *= temp;
            }
        }
        return B;
    }
}
