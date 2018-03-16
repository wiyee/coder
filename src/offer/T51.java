package offer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiyee on 2018/3/15.
 * 在一个长度为n的数组里的所有数字都在0到n-1的范围内。 数组中某些数字是重复的，但不知道有几个数字是重复的。
 * 也不知道每个数字重复几次。请找出数组中任意一个重复的数字。 例如，如果输入长度为7的数组{2,3,1,0,2,5,3}，那么对应的输出是第一个重复的数字2。
 */
public class T51 {
    public boolean duplicate(int numbers[], int length, int[] duplication) {
        if (length <= 1)
            return false;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : numbers) {
            if (map.containsKey(i) && map.get(i) == 1) {
                duplication[0] = i;
                return true;
            } else map.put(i, 1);
        }
        return false;
    }

    public static void main(String[] args) {
        T51 t51 = new T51();
        int[] d = new int[]{};
        int[] ddd = new int[1];
        System.out.println(t51.duplicate(d, 0, ddd));
    }

}
