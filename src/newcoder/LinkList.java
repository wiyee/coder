package newcoder;

/**
 * Created by wiyee on 2018/3/7.
 * 单链表
 */
class Node{
    public Node next;
    public int data;
    public Node(int data){
        this.data=data;
    }
}
public class LinkList {
    public Node head;
    public int length = 0;

    //打印链表
    public void printLinkList() {
        Node p = head;
        while (p != null) {
            System.out.println(p.data);
            p = p.next;
        }
        System.out.println("长度为 ：" + length);
    }

    //判断链表是否为空
    public Boolean isEmpty() {
        if (head == null)
            return true;
        return false;
    }

    //尾插法添加结点
    public void addLastNode(int data) {
        Node x = new Node(data);
        if (head == null) {
            head = x;
            length++;
            return;
        }
        Node q = head;
        while (q.next != null)
            q = q.next;
        q.next = x;
        length++;
    }

    //头插法添加结点
    public void addHeadNode(int data) {
        Node x = new Node(data);
        if (head == null) {
            head = x;
            length++;
            return;
        }
        x.next = head;
        head = x;
        length++;
    }

    public static void main(String[] args) {
        LinkList linkList=new LinkList();
        linkList.addLastNode(1);
        linkList.addLastNode(2);
        linkList.addLastNode(3);
        linkList.addLastNode(4);
        linkList.printLinkList();
    }
}
