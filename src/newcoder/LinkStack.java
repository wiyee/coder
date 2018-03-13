package newcoder;

/**
 * Created by wiyee on 2018/3/8.
 * 链表实现栈
 */
public class LinkStack {
    class Node{
        private Node next;
        private Object val;
    }

    Node top = null;

    void init() {
        top = new Node();
        top.next =null;
        top.val = null;
    }

    public void push(Object val){
        Node node = new Node();
        node.val = val;
        if (top.next == null)
            top.next = node;
        else {
            node.next = top.next;
            top.next = node;
        }
    }

    public Object pop(){
        Object val = null;
        if (top.next == null)
            return -1;
        else {
            val = top.next.val;
            top.next = top.next.next;
        }
        return val;
    }

    public Object peek(){
        if (top.next == null)
            return -1;
        else
            return top.next.val;
    }

    public boolean empty(){
        return top.next==null?true:false;
    }

    public void printStack(){
        Node node = top;
        while (top.next!=null){
            System.out.print(top.next.val);
            top.next = top.next.next;
        }
        System.out.println();
    }

    public static void main(String []args){
        int [] array= new int[]{9,8,7,6,5,4,3,2,1};
        LinkStack linkStack = new LinkStack();
        linkStack.init();
        for (int i:array){
            linkStack.push(i);
        }
        linkStack.printStack();

    }
}
