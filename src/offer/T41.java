package offer;

import java.util.ArrayList;

/**
 * Created by wiyee on 2018/3/16.
 */
public class T41 {
    /**
     * 和为s的连续整数序列
     * 小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,他马上就写出了正确答案是100。但是他并不满足于此,
     * 他在想究竟有多少种连续的正数序列的和为100(至少包括两个数)。没多久,他就得到另一组连续正数和为100的序列:18,19,20,21,22。
     * 现在把问题交给你,你能不能也很快的找出所有和为S的连续正数序列? Good Luck!
     *
     * @param sum
     * @return
     */
    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        if (sum < 3)
            return lists;
        int left = 1;
        int right = 2;
        while (right - 1 <= sum / 2) {
            if (sumList(left, right) == sum) {
                ArrayList<Integer> list = new ArrayList<>();
                for (int i = left; i <= right; i++) {
                    list.add(i);
                }
                lists.add(list);
                left++;
                right = left + 1;
            } else if (sumList(left, right) < sum)
                right++;
            else
                left++;
        }
        return lists;

    }

    private static int sumList(int left, int right) {
        int sum = 0;
        for (int i = left; i <= right; i++) {
            sum += i;
        }
        return sum;
    }

    /*
     * 和为S的两个数字
     * 输入一个递增排序的数组和一个数字S，在数组中查找两个数，是的他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。
     */
    public ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> list = new ArrayList<>();
        if (sum < 1 || array.length < 1)
            return list;
        int left = 0;
        int right = array.length - 1;
        int product = -1;
        while (left < right) {
            if (array[left] + array[right] == sum) {
                if (array[left] * array[right] < product) {
                    list.remove(0);
                    list.remove(1);
                    list.add(array[left]);
                    list.add(array[right]);
                    product = array[left] * array[right];
                } else if (product == -1) {
                    list.add(array[left]);
                    list.add(array[right]);
                    product = array[left] * array[right];
                }
                left++;
                right--;
            } else if (array[left] + array[right] < sum)
                left++;
            else right--;
        }
        return list;
    }

    public static void main(String[] args) {
        T41 t41 = new T41();
        int[] array = new int[]{1, 2, 4, 7, 11, 15};
        System.out.println(t41.FindNumbersWithSum(array, 15));
    }
}
