package offer;

import java.util.Stack;

/**
 * Created by wiyee on 2018/3/8.
 * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。假设压入栈的所有数字均不相等。
 * 例如序列1,2,3,4,5是某栈的压入顺序，序列4，5,3,2,1是该压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列的弹出序列。（注意：这两个序列的长度是相等的）
 */
public class T22 {
    public boolean IsPopOrder(int[] pushA, int[] popA) {
        if (pushA.length == 0 || popA.length == 0 || pushA.length != popA.length)
            return false;
        Stack<Integer> stack = new Stack<>();
        int j = 0;//指向popA数组的第一个数字
        for (int i = 0; i < pushA.length; i++) {
            stack.push(pushA[i]);
            if (pushA[i] == popA[j]) {
                while (j < popA.length && popA[j] == stack.peek() && !stack.empty()) {
                    j++;
                    stack.pop();
                }
            }
        }
        if (j == popA.length)
            return true;
        else
            return false;
    }

    public static void main(String[] args) {
        int[] pushA = new int[]{1, 2, 3, 4, 5};
        int[] popB = new int[]{4, 5, 3, 1, 2};

        T22 t22 = new T22();
        System.out.println(t22.IsPopOrder(pushA, popB));
    }
}
