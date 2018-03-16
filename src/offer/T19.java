package offer;

/**
 * Created by wiyee on 2018/3/15.
 * 求二叉树的镜像
 */
public class T19 {
    private void Mirror(TreeNode root) {
        if (root != null) {
            TreeNode left = root.left;
            TreeNode right = root.right;
            root.left = right;
            root.right = left;
            if (root.left != null)
                Mirror(root.left);
            if (root.right != null)
                Mirror(root.right);
        }

    }

    public static void main(String[] args) {
        TreeNode root1 = new TreeNode(10);
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node5 = new TreeNode(5);
        TreeNode node4 = new TreeNode(4);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        T19 t19 = new T19();
        t19.Mirror(root1);
    }

}
