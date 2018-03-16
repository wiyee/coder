package offer;

/**
 * Created by wiyee on 2018/3/7.
 * 输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
 */
public class T17 {

//    /*
//    非递归版本
//     */
//    public ListNode Merge(ListNode list1,ListNode list2) {
//        ListNode listNode = null;
//
//        if (list1 == null){
//            return list2;
//        }
//        if (list2==null)
//            return list1;
//        if (list1.val <=list2.val){
//            listNode = new ListNode(list1.val);
//            list1 = list1.next;
//        }else {
//            listNode = new ListNode(list2.val);
//            list2 = list2.next;
//        }
//        ListNode p = listNode;
//        //两个链表分别建立一个指针，比较并向后移动。
//        ListNode left = list1;
//        ListNode right = list2;
//        while (left!=null && right!=null){
//            if (left.val<=right.val){
//                p.next = left;
//                left = left.next;
//            }
//            else {
//                p.next = right;
//                right = right.next;
//            }
//            p = p.next;
//        }
//        if(left!=null){
//            p.next = left;
//            p = p.next;
//        }
//        if (right!=null){
//            p.next = right;
//        }
//        return listNode;
//    }

    /*
    递归
     */
    public ListNode Merge(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null)
            return list1;
        ListNode pMergeHead = null;
        if (list1.val <= list2.val) {
            pMergeHead = list1;
            pMergeHead.next = Merge(list1.next, list2);
        } else {
            pMergeHead = list2;
            pMergeHead.next = Merge(list1, list2.next);
        }
        return pMergeHead;
    }

    public static void main(String[] args) {
        ListNode listNode = null;
        int[] vals = new int[]{1, 3, 5};
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

        int[] vals2 = new int[]{2, 4, 6};
        ListNode head2 = listNode;
        ListNode p1 = head2;
        for (int i : vals2) {
            ListNode x = new ListNode(i);
            if (head2 == null) {
                head2 = x;
                p1 = head2;
            } else {
                p1.next = x;
                p1 = p1.next;
            }
        }

        T17 t17 = new T17();
        ListNode listNode1 = t17.Merge(head, head2);
        while (listNode1.next != null) {
            System.out.println(listNode1.val);
            listNode1 = listNode1.next;
        }
    }
}
