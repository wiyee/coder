package offer;

/**
 * Created by wiyee on 2018/3/7.
 */
public class T16 {
    public ListNode ReverseList(ListNode head) {
        //将链表中的元素通过前插法插入到一个新的链表中。
        ListNode listNode = null;
        if (head == null)
            return null;
        ListNode p = head;
        while (p != null) {
            ListNode x = new ListNode(p.val);
            x.next = listNode;
            listNode = x;
            p = p.next;
        }
        return listNode;
    }

    public static void main(String[] args) {
        ListNode listNode = null;
        int[] vals = new int[]{9};
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
        T16 t16 = new T16();
        ListNode listNode1 = t16.ReverseList(head);
        while (listNode1 != null) {
            System.out.println(listNode1.val);
            listNode1 = listNode1.next;
        }
    }
}
