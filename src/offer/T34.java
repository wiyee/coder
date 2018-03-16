package offer;

/**
 * Created by wiyee on 2018/3/6.
 * 把只包含因子2、3和5的数称作丑数（Ugly Number）。例如6、8都是丑数，但14不是，因为它包含因子7。 习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。
 */
public class T34 {
    public int GetUglyNumber_Solution(int index) {
        if (index <= 0)
            return 0;
        int[] uglyNumber = new int[index];
        uglyNumber[0] = 1;
        int multiplyNumber2 = 0;
        int multiplyNumber3 = 0;
        int multiplyNumber5 = 0;
        int i = 1;

        while (i < index) {
            printString(uglyNumber);
            System.out.println(multiplyNumber2 + "---" + uglyNumber[multiplyNumber2] * 2);
            System.out.println(multiplyNumber3 + "---" + uglyNumber[multiplyNumber3] * 3);
            System.out.println(multiplyNumber5 + "---" + uglyNumber[multiplyNumber5] * 5);
            int min = min(uglyNumber[multiplyNumber2] * 2, uglyNumber[multiplyNumber3] * 3, uglyNumber[multiplyNumber5] * 5);
            uglyNumber[i] = min;
            System.out.println("第" + i + "个丑数---" + min);
            if (min == uglyNumber[multiplyNumber2] * 2)
                ++multiplyNumber2;
            if (min == uglyNumber[multiplyNumber3] * 3)
                ++multiplyNumber3;
            if (min == uglyNumber[multiplyNumber5] * 5)
                ++multiplyNumber5;
            i++;

        }
        return uglyNumber[index - 1];
    }

    private int min(int multiplyNumber2, int multiplyNumber3,
                    int multiplyNumber5) {
        int min = (multiplyNumber2 < multiplyNumber3) ? multiplyNumber2 : multiplyNumber3;
        min = (min < multiplyNumber5) ? min : multiplyNumber5;
        return min;
    }

    private void printString(int[] ints) {
        System.out.print("[");
        for (int i = 0; i < ints.length - 1; i++) {
            System.out.print(ints[i] + ",");
        }
        System.out.println(ints[ints.length - 1] + "]");
    }

    public static void main(String[] args) {
        System.out.println(new T34().GetUglyNumber_Solution(20));
    }
}
