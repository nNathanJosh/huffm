import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * Builds the Huffman codebooks which allows this class to uses it for the encode/decode of text.
 *  - This is one of the most important Class and it contains five methods :
 *      1.countCharacters
 *      2.buildModel
 *      3.makeCodes
 *      4.encode
 *      5.decode
 */

public class HuffmanEncoderDecoder {

    /**
     * Count how often symbols/characters appears from the sample text user input.
     *  - We use string/char to process inputs which user can put any english letters and numbers from 0 to 9
     *  - Our implementation process Java UTF-16 char values, and it can handle:
     *      - ASCII characters, spaces, punctuation, mathematical symbols, and many Unicode characters
     */
    private Map<Character, Integer> countCharacters(String text) {
        Map<Character, Integer> frequencies = new TreeMap<>();
        //make frequency map , Key = character , Value = number of occurrences.
        for (char character : text.toCharArray()) { //string to char convert
            frequencies.put(character, frequencies.getOrDefault(character, 0) + 1);
        } return frequencies;
    }


    /**
     * Build the full Huffman model from the given sample text.
     */
    public HuffmanCodebook buildModel(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Sample text cannot be empty.");
        }

        Map<Character, Integer> frequencies = countCharacters(text);
        PriorityQueue<HuffmanTreeNode> queue = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            queue.add(new HuffmanTreeNode(entry.getKey(), entry.getValue(), null, null));
        }

        while (queue.size() > 1) {
            HuffmanTreeNode left = queue.remove();
            HuffmanTreeNode right = queue.remove();
            queue.add(new HuffmanTreeNode(null, left.frequency + right.frequency, left, right));
        }

        HuffmanTreeNode root = queue.remove();
        Map<Character, String> codes = new HashMap<>();

        if (root.isLeaf()) {
            codes.put(root.character, "0");
        } else {
            makeCodes(root, "", codes);
        } return new HuffmanCodebook(frequencies, codes, root);
    }


    /**
     * Generates binary codes, this method is a recursion "uses it self to solve the smaller parts of it"
     * It is a private method, intended only for the HuffmanEncoderDecoder when model construction
     * @param node , current tree node that is being examined
     * @param prefix , accumulated binary path
     * @param codes , map of where the binary codes are saved
     */
    private void makeCodes(HuffmanTreeNode node, String prefix, Map<Character, String> codes) {
        if (node.isLeaf()) {
            codes.put(node.character, prefix);
            return;
        }
        makeCodes(node.left, prefix + "0", codes);
        makeCodes(node.right, prefix + "1", codes);
    }


    /**
     * Convert text to its Huffman binary representation
     */
    public String encode(String text, HuffmanCodebook model) {
        StringBuilder bits = new StringBuilder();
        for (char character : text.toCharArray()) {
            String code = model.codes.get(character);
            if (code == null) {
                throw new IllegalArgumentException("The codebook does not contain: " + character);
            } bits.append(code);
        } return bits.toString();
    }


    /**
     * Convert back to its ordinary text
     */
    public String decode(String bits, HuffmanCodebook model) {
        StringBuilder text = new StringBuilder();

        if (model.root.isLeaf()) {
            for (char bit : bits.toCharArray()) {
                if (bit != '0') {
                    throw new IllegalArgumentException("Invalid bit sequence.");
                } text.append(model.root.character);
            } return text.toString();
        }

        HuffmanTreeNode node = model.root;

        for (char bit : bits.toCharArray()) {
            if (bit == '0') {
                node = node.left;
            } else if (bit == '1') {
                node = node.right;
            } else {
                throw new IllegalArgumentException("Bits must contain only 0 and 1.");
            }

            if (node.isLeaf()) {
                text.append(node.character);
                node = model.root;
            }
        }

        if (node != model.root) {
            throw new IllegalArgumentException("Bits end before a complete code is reached.");
        } return text.toString();
    }
}