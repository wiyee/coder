package offer;

import java.util.Stack;

/**
 * Created by wiyee on 2018/3/10.
 * 输入两个链表，找出它们的第一个公共结点。
 */
public class T37 {
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null)
            return null;
        Stack<ListNode> stack1 = new Stack<>();
        Stack<ListNode> stack2 = new Stack<>();
        while (pHead1 != null) {
            stack1.push(pHead1);
            pHead1 = pHead1.next;
        }
        while (pHead2 != null) {
            stack2.push(pHead2);
            pHead2 = pHead2.next;
        }
        ListNode listNode1 = null;
        while (!stack2.isEmpty() && !stack1.isEmpty() && stack1.peek() == stack2.peek()) {
            stack2.pop();
            listNode1 = stack1.pop();
        }
        return listNode1;
    }

    public static void main(String[] args) {
        int[] vals = new int[]{2, 3, 4, 5, 6, 7, 8, 9};
        int[] vals2 = new int[]{5, 6, 7, 8, 9};
        ListNode listNode = new ListNode(1);
        //尾插法的时候，要两个指针，一个用来随数据移动，一个保持头指针位置。
        ListNode head = listNode;
        ListNode tmp = head;
        for (int i : vals) {
            ListNode p = new ListNode(i);
            tmp.next = p;
            tmp = p;
        }
        ListNode listNode1 = new ListNode(2);
        ListNode head1 = listNode1;
        ListNode tmp1 = head1;
        for (int i : vals2) {
            ListNode p = new ListNode(i);
            tmp1.next = p;
            tmp1 = p;
        }
        T37 t37 = new T37();
        System.out.println(t37.FindFirstCommonNode(head, head1).val);

    }
}
