package offer;

import java.util.ArrayList;

/**
 * Created by wiyee on 2018/3/7.
 * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，如果输入如下矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
 */
public class T20 {
    public ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer> array = new ArrayList<>();
        int row = matrix.length;
        int col = matrix[0].length;
        // 输入的二维数组非法，返回空的数组
        if (row == 0 || col == 0)
            return array;
        int left = 0;
        int top = 0;
        int right = col - 1;
        int bottom = row - 1;
        while (left <= right && top <= bottom) {
            for (int i = left; i < right + 1; i++)
                array.add(matrix[top][i]);
            for (int i = top + 1; i <= bottom; i++)
                array.add(matrix[i][right]);

            //如果矩阵为1*n，不加判断后将会把矩阵逆序打印
            if (top != bottom) {
                for (int i = right - 1; i >= left; i--)
                    array.add(matrix[bottom][i]);
            }
            //如果矩阵为n*1
            if (left != right) {
                for (int i = bottom - 1; i > top; i--) {
                    array.add(matrix[i][left]);
                }
            }
            left++;
            top++;
            right--;
            bottom--;
        }
        return array;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1}, {2}, {3}, {4}, {5}};
        System.out.println(new T20().printMatrix(matrix));
    }
}
