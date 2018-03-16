package offer;

import java.util.ArrayList;

/**
 * Created by wiyee on 2018/3/15.
 * 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
 */
public class T23 {
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<TreeNode> queue = new ArrayList<>();
        if (root == null)
            return list;
        queue.add(root);
        while (queue.size() != 0) {
            TreeNode tmp = queue.remove(0);
            if (tmp.left != null) {
                queue.add(tmp.left);
            }
            if (tmp.right != null) {
                queue.add(tmp.right);
            }
            list.add(tmp.val);
        }
        return list;
    }

    public static void main(String[] args) {
        TreeNode root1 = new TreeNode(10);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        root1.left = node1;
        node1.left = node2;
        node1.right = node3;
        node3.left = node6;
        node3.right = node7;
        T23 t23 = new T23();
        System.out.println(t23.PrintFromTopToBottom(root1));
    }
}
