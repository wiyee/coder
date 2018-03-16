package offer;

/**
 * Created by wiyee on 2018/3/7.
 * <p>
 * public ListNode FindKthToTail(ListNode head,int k) { //5,{1,2,3,4,5}
 * ListNode p, q;
 * p = q = head;
 * int i = 0;
 * for (; p != null; i++) {
 * if (i >= k)
 * q = q.next;
 * p = p.next;
 * }
 * return i < k ? null : q;
 * }
 */

public class T15 {

    public ListNode FindKthToTail(ListNode head, int k) {
        ListNode listNode = null;
        if (head == null || k == 0)
            return listNode;
        // 两个指针同时向后移动
        ListNode right = head;
        while (right != null && k > 0) {
            k--;
            right = right.next;
        }
        if (k > 0 && right == null)
            return listNode;
        while (right != null) {
            right = right.next;
            head = head.next;
        }
        System.out.println(head.val);
        return head;
    }

    public static void main(String[] args) {
        ListNode listNode = null;
        int[] vals = new int[]{1, 2, 8, 9};
        ListNode head = listNode;
        ListNode p = head;
        for (int i : vals) {
            ListNode x = new ListNode(i);
            if (head == null) {
                head = x;
                p = head;
            } else {
                p.next = x;
                p = p.next;
            }
        }
        T15 t15 = new T15();
        ListNode listNode1 = t15.FindKthToTail(head, 9);

    }
}
