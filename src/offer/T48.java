package offer;

/**
 * Created by wiyee on 2018/3/2.
 */
public class T48 {
    public static int Add(int num1, int num2) {
        while (num2 != 0) {
            int temp = num1 ^ num2;
            System.out.println("temp:" + temp);
            num2 = (num1 & num2) << 1;
            System.out.println("num2:" + num2);
            num1 = temp;
        }
        return num1;
    }

    public static void main(String[] args) {

        System.out.println(Add(5, 7));
    }
}
