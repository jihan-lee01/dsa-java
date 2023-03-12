package edu.emory.cs.tree.balanced;

import edu.emory.cs.tree.BinaryNode;

public class BalancedBinarySearchTreeQuiz<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, BinaryNode<T>> {
    @Override
    public BinaryNode<T> createNode(T key) {
        return new BinaryNode<>(key);
    }

    @Override
    protected void balance(BinaryNode<T> node) {
        BinaryNode<T> parent = node.getParent();
        BinaryNode<T> grandParent = node.getGrandParent();
        BinaryNode<T> uncle = node.getUncle();
        if (parent != null && grandParent != null && uncle != null) {
            if (parent.hasBothChildren()) return;
            if (!grandParent.isRightChild(parent)) return;
            if (!(uncle.hasLeftChild() ^ uncle.hasRightChild())) return;

            if (parent.isLeftChild(node) && uncle.hasLeftChild()) rotateRight(parent);
            else if (parent.isRightChild(node) && uncle.hasRightChild()) rotateLeft(uncle);
            else if (parent.isLeftChild(node) && uncle.hasRightChild()) {
                rotateLeft(uncle);
                rotateRight(parent);
            }
            rotateLeft(grandParent);
            rotateRight(grandParent);
        }
    }
}
