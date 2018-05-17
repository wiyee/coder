package sort;

import java.util.Arrays;

public class Bubble {
    public static void main(String[] args) {
        int arr[] = new int[]{1, 6, 2, 2, 5};
        BubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void BubbleSort(int[] a) {
        int j, k = a.length;
        boolean flag = true;//发生了交换就为true, 没发生就为false，第一次判断时必须标志位true。
        while (flag) {
            flag = false;//每次开始排序前，都设置flag为未排序过
            for (j = 1; j < k; j++) {
                if (a[j - 1] > a[j]) {//前面的数字大于后面的数字就交换
                    //交换a[j-1]和a[j]
                    int temp;
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;

                    //表示交换过数据;
                    flag = true;
                }
            }
            k--;//减小一次排序的尾边界
        }//end while
    }
}
