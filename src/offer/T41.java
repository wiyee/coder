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

    public static void main(String[] args) {
        T41 t41 = new T41();
        System.out.println(t41.FindContinuousSequence(100));
    }
}
