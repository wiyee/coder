package offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wiyee on 2018/3/8.
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 */
public class T14 {
    public void reOrderArray(int[] array) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 == 0) {
                list1.add(array[i]);//偶数
            } else {
                list2.add(array[i]);//奇数
            }
        }
        int i = 0;
        for (int val : list2) {
            array[i] = val;
            i++;
        }
        for (int val : list1) {
            array[i] = val;
            i++;
        }

    }

    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        T14 t14 = new T14();
        t14.reOrderArray(array);
    }
}
