/**
 * Validates input texts from the user before encoding and decoding operations.
 */
public class UsersInputValidator {
    /**
     * Check validity for the first user input (sample text)
     */
    public static void validateSample(String sample) {
        if (sample == null || sample.isEmpty()) {
            throw new IllegalArgumentException("Sample text cannot be empty.");
        }
    }


    /**
     * Check validity if text can be encoded with the existing Huffman codebook.
     */
    public static void validateTextToEncode(String text, HuffmanCodebook model) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text to encode cannot be empty.");
        }
        for (char c : text.toCharArray()) {
            if (!model.codes.containsKey(c)) {
                throw new IllegalArgumentException("The codebook does not contain the character: " + TreePrinter.showSymbol(c));
            }
        }
    }


    /**
     * Check validity for inputed binary digits
     */
    public static void validateBitsToDecode(String bits) {
        if (bits == null || bits.isEmpty()) {
            throw new IllegalArgumentException("Bit sequence cannot be empty.");
        }
        for (char bit : bits.toCharArray()) {
            if (bit != '0' && bit != '1') {
                throw new IllegalArgumentException("Bits must contain only 0 and 1.");
            }
        }
    }
}