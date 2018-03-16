package offer;

/**
 * Created by wiyee on 2018/3/11.
 * 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），
 * 返回结果为复制后复杂链表的head。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
 * 分析：遍历链表复制结点插入* 对应的结点之后，更新random指针，然后脱链即可，一共分三步，对应我自己代码里的三个while循环。
 * <p>
 * 三步法：复制成A-A'-B-B'-C-C'这种形式，random用new->random=old->random->next，分离两个链表就行了。
 */

class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}

public class T26 {
    public RandomListNode Clone(RandomListNode pHead) {
        if (pHead == null)
            return null;
        /*
        * 复制节点，把新建的节点A‘连接在原节点A后
        */
        RandomListNode tmp = pHead;
        while (tmp != null) {
            RandomListNode node = new RandomListNode(tmp.label);
            node.next = tmp.next;
            tmp.next = node;
            tmp = node.next;
        }
        /*
         * 链接random节点
         */
        RandomListNode tmpNode = pHead;
        while (tmpNode != null) {
            if (tmpNode.random != null) {
                RandomListNode node = tmpNode.next;
                node.random = tmpNode.random.next;
                tmpNode = node.next;
            } else
                tmpNode = tmpNode.next.next;
        }
        /*
         *把两个相连的链表拆分成两个
         */
        if (pHead == null)
            return null;
        RandomListNode tmpNode1 = pHead;
        RandomListNode newHead = pHead.next;

        while (tmpNode1 != null) {
            RandomListNode node = tmpNode1.next;
            tmpNode1.next = node.next;
            if (node.next != null)
                node.next = node.next.next;
            else
                node.next = null;
            tmpNode1 = tmpNode1.next;
        }

        return newHead;
    }
}
