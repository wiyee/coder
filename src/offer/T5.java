package offer;

import java.util.ArrayList;

/**
 * Created by wiyee on 2018/3/7.
 */
class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}

public class T5 {
    public ArrayList<Integer> array = new ArrayList<>();

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        if (listNode == null) {
            return new ArrayList<>();
        }

        /**
         //用stack实现
         Stack<Integer> stack = new Stack<>();
         while (listNode.next!=null){
         stack.push(listNode.val);
         listNode = listNode.next;
         }
         stack.push(listNode.val);
         while (!stack.empty()){
         array.add(stack.pop());
         }
         */
        // 递归实现
        if (listNode != null) {
            printListFromTailToHead(listNode.next);
            array.add(listNode.val);
        }

        return array;
    }

    public static void main(String[] args) {
        int[] vals = new int[]{2, 3, 4, 5, 6, 7, 8, 9};
        T5 t5 = new T5();
        ListNode listNode = new ListNode(1);
        //尾插法的时候，要两个指针，一个用来随数据移动，一个保持头指针位置。
        ListNode head = listNode;
        ListNode tmp = head;
        for (int i : vals) {
            ListNode p = new ListNode(i);
            tmp.next = p;
            tmp = p;
        }
        System.out.println(t5.printListFromTailToHead(head));

    }
}
