package com.ddu.demo.java.algorithm;


/**
 * 定义多叉树
 */
class TreeNode {
    int val;
    TreeNode[] children;
    TreeNode(int val) { this.val = val; }
}


/**
 * 多叉树，删除只包含一个子节点的中间节点
 * @author wangxiaolong
 */
public class MultiChildrenTree {

    /**
     * 递归实现
     * @param root 初始根节点
     * @return 压缩后的根节点
     */

    static TreeNode compress(TreeNode root) {
        if (root == null) return null;
        TreeNode[] children = root.children;
        if (children == null) return root;
        int childrenNum = children.length;
        if (childrenNum > 1) {
            for (int i = 0; i < childrenNum; i++) {
                children[i] = compress(children[i]);
            }
        } else if (childrenNum == 1) {
            return compress(children[0]);
        } else if (childrenNum == 0) {
            return root;
        }
        return root;
    }

    // 用于测试
    static void printPreOrder(TreeNode node) {
        if (node == null) return;
        System.out.print(node.val + ",");
        if (node.children != null) {
            for (TreeNode child : node.children) {
                printPreOrder(child);
            }
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode tNode2 = new TreeNode(2);
        TreeNode tNode3 = new TreeNode(3);
        TreeNode tNode4 = new TreeNode(4);
        TreeNode tNode5 = new TreeNode(5);
        TreeNode tNode6 = new TreeNode(6);
        TreeNode tNode7 = new TreeNode(7);
        TreeNode tNode8 = new TreeNode(8);
        TreeNode tNode9 = new TreeNode(9);
        TreeNode tNode10 = new TreeNode(10);
        TreeNode tNode11 = new TreeNode(11);
        TreeNode tNode12 = new TreeNode(12);

        root.children = new TreeNode[] { tNode2, tNode3, tNode4 };

        tNode2.children = new TreeNode[] { tNode5, tNode6 };

        tNode3.children = new TreeNode[] { tNode7 };
        tNode7.children = new TreeNode[] { tNode9 };
        tNode9.children = new TreeNode[] { tNode12 };

        tNode4.children = new TreeNode[] { tNode8 };
        tNode8.children = new TreeNode[] { tNode10, tNode11 };

        printPreOrder(root);
        System.out.println("====");
        printPreOrder(compress(root));
    }
}
