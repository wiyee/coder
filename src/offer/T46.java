package offer;

/**
 * Created by wiyee on 2018/3/2.
 */
public class T46 {
    private int Sum_Solution(int n) {
        int result = 0;
        if (n == 1)
            return 1;
        else
            return Sum_Solution(n - 1) + n;
    }

    public static void main(String[] args) {
        System.out.println(new T46().Sum_Solution(10));
    }
}
