package offer;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiyee on 2018/3/15.
 * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。
 * 思路:创建一个list，遍历数组中的数，判断list中是否有这个数，有了就把数组中的数删除，没有就加入list。
 */
public class T40 {
    public void FindNumsAppearOnce(int[] array, int num1[], int num2[]) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < array.length; i++) {
            if (map.containsKey(array[i])) {
                map.remove(array[i]);
            } else {
                map.put(array[i], 1);
            }
        }
        int a = 0;
        for (Integer key : map.keySet()) {
            if (a == 0)
                num1[0] = key;
            if (a == 1)
                num2[0] = key;
            a++;
        }
    }

    public static void main(String[] args) {
        T40 t40 = new T40();
        int[] array = new int[]{1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 7, 8, 8, 9, 9};
        int[] a = new int[1];
        int[] b = new int[1];
        t40.FindNumsAppearOnce(array, a, b);
    }
}
