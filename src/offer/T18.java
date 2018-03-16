package offer;

/**
 * Created by wiyee on 2018/3/9.
 * /*思路：参考剑指offer
 * 1、首先设置标志位result = false，因为一旦匹配成功result就设为true，
 * 剩下的代码不会执行，如果匹配不成功，默认返回false
 * 2、递归思想，如果根节点相同则递归调用DoesTree1HaveTree2（），
 * 如果根节点不相同，则判断tree1的左子树和tree2是否相同，
 * 再判断右子树和tree2是否相同
 * 3、注意null的条件，HasSubTree中，如果两棵树都不为空才进行判断，
 * DoesTree1HasTree2中，如果Tree2为空，则说明第二棵树遍历完了，即匹配成功，
 * tree1为空有两种情况（1）如果tree1为空&&tree2不为空说明不匹配，
 * （2）如果tree1为空，tree2为空，说明匹配。
 */
public class T18 {
    public static void main(String[] args) {
        TreeNode root1 = new TreeNode(10);
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        root1.left = node1;
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
        System.out.println(HasSubtree(root1, root2));
    }

    public static boolean HasSubtree(TreeNode root1, TreeNode root2) {
        boolean result = false;
        if (root1 != null && root2 != null) {
            if (root1.val == root2.val) {
                result = doesTree1HaveTree2(root1, root2);
            }
            if (!result)
                result = HasSubtree(root1.left, root2);
            if (!result)
                result = HasSubtree(root1.right, root2);
        }

        return result;
    }

    public static boolean doesTree1HaveTree2(TreeNode node1, TreeNode node2) {
        if (node2 == null)
            return true;
        if (node1 == null)
            return false;
        if (node1.val != node2.val)
            return false;
        return doesTree1HaveTree2(node1.left, node2.left) && doesTree1HaveTree2(node1.right, node2.right);
    }
}
