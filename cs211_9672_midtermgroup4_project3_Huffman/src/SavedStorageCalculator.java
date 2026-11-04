import java.nio.charset.StandardCharsets;

/**
 * Calculates what percentage is saved from the encoded data size.
 */
public class SavedStorageCalculator {
    public static int calculateOriginalBits(String text) {
        if (text == null) {
            return 0;
        } return text.getBytes(StandardCharsets.UTF_8).length * 8;
    }


    /**
     * Calculate lenght of the Huffman encoded texts
     */
    public static int calculateEncodedBits(String encodedText) {
        if (encodedText == null) {
            return 0;
        } return encodedText.length();
    }


    /**
     * Percentage of storage saved.
     */
    public static double calculateSavingsPercentage(int originalBits, int encodedBits) {
        if (originalBits <= 0) {
            return 0.0;
        } return (originalBits - encodedBits) * 100.0 / originalBits;
    }


    /**
     * Percentage of storage saved directly from the original;
     */
    public static double calculateSavingsPercentage(String originalText, String encodedText) {
        int originalBits = calculateOriginalBits(originalText);
        int encodedBits = calculateEncodedBits(encodedText);
        return calculateSavingsPercentage(originalBits, encodedBits);
    }
}