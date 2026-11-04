/**
 * This Class 'HuffmanTreeNode' represent a 'one node' from the Huffman tree.
 */
public class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {
    public final Character character; // null for a branch node
    public final int frequency;
    public final HuffmanTreeNode left;
    public final HuffmanTreeNode right;


    /**
     * Creates a branch node (constructor)
     */
    public HuffmanTreeNode(Character character, int frequency, HuffmanTreeNode left, HuffmanTreeNode right)
    {
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }


    /**
     * @return a leaf node
     * A node is a leaf if it has no left child and no right child-
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }


    /**
     * Conclude the ordering of two nodes based on its 'frequencies' ,
     * if Node a = 1, Node b = 3 means node a will place before node b
     * (lowest-frequency nodes must always be combined first)
     */
    @Override
    public int compareTo(HuffmanTreeNode other) {
        return Integer.compare(frequency, other.frequency);
    }
}