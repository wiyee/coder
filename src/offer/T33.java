package offer;

/**
 * Created by wiyee on 2018/3/1.
 */
public class T33 {
    private String PrintMinNumber(int[] numbers) {
        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = 0; j < numbers.length - 1 - i; j++) {
                if (compareString(numbers[j], numbers[j + 1]) == 1) {
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int n : numbers) {
            stringBuffer.append(String.valueOf(n));
        }
        return stringBuffer.toString();
    }

    private static int compareString(int a, int b) {
        String left = String.valueOf(a) + String.valueOf(b);
        String right = String.valueOf(b) + String.valueOf(a);
        if (Long.parseLong(left) > Long.parseLong(right))
            return 1;
        else
            return -1;
    }

    public static void main(String[] args) {
        int[] number = new int[]{3334, 3, 3333332};
        String a = new T33().PrintMinNumber(number);
        System.out.println(a);
    }
}
