package offer;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by wiyee on 2018/3/8.
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈最小元素的min函数。
 */
public class T21 {
    Stack<Integer> stack = new Stack<>();

    public void push(int node) {
        stack.push(node);
    }

    public void pop() {
        stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int min() {
        if (stack.empty())
            return 0;
        int min = top();
        int k = 0;
        Iterator<Integer> iterator = stack.iterator();
        while (iterator.hasNext()) {
            k = iterator.next();
            if (min > k) {
                min = k;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        T21 t21 = new T21();
        t21.push(10);
        t21.push(20);
        t21.push(30);
        t21.push(7);
        t21.push(50);
        t21.push(5);
        System.out.println(t21.min());

    }
}
