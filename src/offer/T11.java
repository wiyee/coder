package offer;

/**
 * Created by wiyee on 2018/3/7.
 * 给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。
 */
public class T11 {
    double Power(double base, int exponent) {
        if (exponent == 0)
            return 1;
        if (exponent < 0) {
            base = 1 / base;
            return base * Power(base, Math.abs(exponent) - 1);
        } else if (exponent == 1) {
            return base;
        } else
            return base * Power(base, exponent - 1);
    }

    public static void main(String[] args) {
        T11 t11 = new T11();

        System.out.println(t11.Power(2, -3));
    }
}
