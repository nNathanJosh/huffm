import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  Display/Prints the Huffman Tree structure, the Huffman Tree is already generated before this class called, however this
 *  class is what allows us to see a readable represenation of it.
 *  This class will provide us two display style based on the amount of frequency from the user inputs
 *   1. Top-Down Tree Diagram
 *   2. Outline Style Tree Diagram
 */
public class TreePrinter {

    private static final boolean UNICODE = true;
    private static final int MAX_WIDTH = 120; //if the diagram width is mode than 120, the App will use the 'Outline Style Tree Diagram'
    private static final int GAP = 6;
    private static final char TL = UNICODE ? '\u250C' : '+';  // top-left corner (TL)
    private static final char TR = UNICODE ? '\u2510' : '+';  // top-right corner  (TR)
    private static final char HZ = UNICODE ? '\u2500' : '-';  // horizontal line  (HZ)
    private static final char UP = UNICODE ? '\u2534' : '+';  // horizontal line with a branch going up  (UP)

    /** A finished piece of the drawing. stores Lines, Width, Anchor(horizontal position of the top label) * */
    private static class Block {
        final List<String> lines;
        final int width;
        final int anchor;
        //constructor
        Block(List<String> lines, int width, int anchor) {
            this.lines = lines;
            this.width = width;
            this.anchor = anchor;
        }
    }


    /**
     *  Display/Prints the full Huffman Tree beginning from top/root.
     *   If wider than MAX_WIDTH limit, switch to the outline tree view,
     *   when user give to much unique inputs it can lead to
     *
     *     try it with :  Almost every fish have grills now
     *                    Almost everyone money got stolen seven
     *                    aaaaaaaabbbbcdefffffgggggggxxxxyyz
     *                    ΩΨΔΘЦΞЖaAoБΣБЦБЦΞha
     */
    public static void printTree(HuffmanTreeNode root) {
        if (root == null) { // check if empty
            System.out.println("(Empty Tree)");
            return;
        }

        Block drawing = build(root);

        if (drawing.width > MAX_WIDTH) {
            System.out.println("(Too wide for top-down view, showing outline view:)\n");
            printOutlineTree(root);
            return;
        }

        for (String line : drawing.lines) {
            System.out.println(line);
        }
    }


    /**  Original Top-Down 2D diagram */
    private static Block build(HuffmanTreeNode node) {
        if (node.isLeaf()) {
            String label = showSymbol(node.character) + ":" + node.frequency;
            return new Block(Arrays.asList(label), label.length(), label.length() / 2);
        }

        Block left = build(node.left);
        Block right = build(node.right);

        int rightStart = left.width + GAP;
        int leftCol = left.anchor;
        int rightCol = rightStart + right.anchor;
        int center = (leftCol + rightCol) / 2;
        String label = String.valueOf(node.frequency);

        char[] bar = new char[rightCol - leftCol + 1];
        Arrays.fill(bar, HZ);
        bar[0] = TL;
        bar[bar.length - 1] = TR;
        bar[center - leftCol] = UP;

        if (center - leftCol - 2 >= 0) {
            bar[center - leftCol - 2] = '0';
        }
        if (center - leftCol + 2 < bar.length) {
            bar[center - leftCol + 2] = '1';
        }

        List<StringBuilder> rows = new ArrayList<>();
        put(rows, 0, center - label.length() / 2, label);
        put(rows, 1, leftCol, new String(bar));
        for (int i = 0; i < left.lines.size(); i++) {
            put(rows, 2 + i, 0, left.lines.get(i));
        }
        for (int i = 0; i < right.lines.size(); i++) {
            put(rows, 2 + i, rightStart, right.lines.get(i));
        }

        List<String> lines = new ArrayList<>();
        for (StringBuilder row : rows) {
            lines.add(row.toString());
        } return new Block(lines, rightStart + right.width, center);
    }


    /**
     * Conclude the location of a text to a specifc row and colomn in the graph
     */
    private static void put(List<StringBuilder> rows, int row, int col, String text) {
        while (rows.size() <= row) {
            rows.add(new StringBuilder());
        }
        StringBuilder line = rows.get(row);
        while (line.length() < col + text.length()) {
            line.append(' ');
        }
        for (int i = 0; i < text.length(); i++) {
            line.setCharAt(col + i, text.charAt(i));
        }
    }


    /**
     * Display the other display format when user to much unique char
     */
    public static void printOutlineTree(HuffmanTreeNode root) {
        System.out.println("Tree outline --- * = internal node, number = frequency");
        if (root.isLeaf()) {
            System.out.println(getLeafLabel(root, "0"));
        } else {
            System.out.println("* (" + root.frequency + ")");
            printOutlineNode(root.left, "", false, "0", "0");
            printOutlineNode(root.right, "", true, "1", "1");
        }
    }


    /**
     * Prints every individual nodes in the other display format (Outline Style Tree Diagram)
     */
    private static void printOutlineNode(HuffmanTreeNode node, String prefix, boolean isTail, String bit, String code) {
        if (node == null) return;
        //Box Drawings Light Up and Right Unicode symbol
        String connector = isTail ? "└─[" + bit + "] " : "├─[" + bit + "] ";

        if (node.isLeaf()) {
            System.out.println(prefix + connector + getLeafLabel(node, code));
        } else {
            System.out.println(prefix + connector + "* (" + node.frequency + ")");
            String childPrefix = prefix + (isTail ? "   " : "|  ");
            printOutlineNode(node.left, childPrefix, false, "0", code + "0");
            printOutlineNode(node.right, childPrefix, true, "1", code + "1");
        }
    }


    /**
     * Format of the leaf into a user redable
     */
    private static String getLeafLabel(HuffmanTreeNode node, String code) {
        return "'" + formatChar(node.character) + "' (" + node.frequency + ")   code=" + code;
    }


    /**
     * Converstion of special characters into readable for the (Outline Style Tree Diagram)
     */
    private static String formatChar(Character symbol) {
        if (symbol == null) return "null";
        if (symbol == ' ') return " ";
        if (symbol == '\n') return "\\n";
        if (symbol == '\t') return "\\t";
        if (symbol == '\'') return "\\'";
        if (symbol == '\\') return "\\\\";
        return Character.toString(symbol);
    }


    /**
     * Give us readable names for special characters
     *  null = [null]
     *  Space = [space]
     *  Newline = [newline]
     *  Tab	= [tab]
     *  if others, it symbol itself will be displayed
     */
    public static String showSymbol(Character symbol) {
        if (symbol == null) return "[null]";
        if (symbol == ' ') return "[space]";
        if (symbol == '\n') return "[newline]";
        if (symbol == '\t') return "[tab]";
        return Character.toString(symbol);
    }
}