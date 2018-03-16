package offer;

/**
 * Created by wiyee on 2018/3/15.
 * 统计一个数字在排序数组中出现的次数。
 */
public class T38 {
    public int GetNumberOfK(int[] array, int k) {
        if (array.length == 0)
            return 0;
        int count = 0;
        int n = 0;
        for (int i : array) {
            if (k == i) {
                n++;
                count++;
            }
            if (k != i && n > 0)
                return count;

        }
        return count;
    }

    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9, 9, 10, 11, 12, 13};
        T38 t38 = new T38();
        System.out.println(t38.GetNumberOfK(array, 9));
    }
}
