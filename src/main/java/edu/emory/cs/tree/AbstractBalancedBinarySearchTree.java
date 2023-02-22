package edu.emory.cs.tree;

public abstract class AbstractBalancedBinarySearchTree<T extends Comparable<T>, N extends AbstractBinaryNode<T, N>> extends AbstractBinarySearchTree<T, N> {
    /**
     * Rotates the specific node to the left.
     * @param node the node to be rotated.
     */
    protected void rotateLeft(N node) {
        N child = node.getRightChild();
        node.setRightChild(child.getLeftChild());

        if (node.hasParent())
            node.getParent().replaceChild(node, child);
        else
            setRoot(child);

        child.setLeftChild(node);
    }

    /**
     * Rotates the specific node to the right.
     * @param node the node to be rotated.
     */
    protected void rotateRight(N node) {
        N child = node.getLeftChild();
        node.setLeftChild(child.getRightChild());

        if (node.hasParent())
            node.getParent().replaceChild(node, child);
        else
            setRoot(child);

        child.setRightChild(node);
    }
}
