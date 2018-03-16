package offer;

/**
 * Created by wiyee on 2018/3/1.
 * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
 */
public class T29 {
    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 2, 2, 2, 5, 4, 2};
        System.out.println(solution(array));
    }

    public static int solution(int[] array) {
        int temp = 0;
        int result = 0;
        int time = 0;
        int re;
        for (int i = 0; i < array.length; i++) {
            if (time == 0) {
                result = array[i];
                time++;
            } else if (result == array[i])
                time++;
            else
                time--;

        }
        if (time > 0) {
            re = result;
        } else
            return 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == re)
                temp++;
        }

        if (temp * 2 >= (array.length + 1))
            return result;
        else
            return 0;
    }

}
