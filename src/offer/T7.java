package offer;

import java.util.Stack;

/**
 * Created by wiyee on 2018/3/8.
 * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
 */
public class T7 {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        while (!stack1.empty()) {
            stack2.push(stack1.pop());
        }
        int result = stack2.pop();
        while (!stack2.empty()) {
            stack1.push(stack2.pop());
        }
        return result;
    }
}
