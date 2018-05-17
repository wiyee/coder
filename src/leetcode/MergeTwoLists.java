package leetcode;


/**
 * Created by wiyee on 2018/5/9.
 * <p>
 * Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.
 */
class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
}

public class MergeTwoLists {
    public static void main(String[] args) {
        ListNode l1 = null;
        ListNode l2 = new ListNode(0);
        MergeTwoLists mergeTwoLists = new MergeTwoLists();
        System.out.println(mergeTwoLists.mergeTwoLists(l1, l2).val);
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode p = new ListNode(0);
        ListNode list = p;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                list.next = l1;
                l1 = l1.next;
            } else {
                list.next = l2;
                l2 = l2.next;
            }
            list = list.next;
        }
        if (l1 != null) {
            list.next = l1;
        }
        if (l2 != null) {
            list.next = l2;
        }
        return p.next;
    }
}
