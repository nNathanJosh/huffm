import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * This class is the main application, the Huffman Class controls the interaction between the user and other classes.
 * No implementation of algorithm inhere, it does calls the appropriate methods sequences from the other classes.
 */
public class HuffmanApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        HuffmanEncoderDecoder codec = new HuffmanEncoderDecoder(); //This create a object of 'HuffmanEncoderDecoder'

        /**
         * Ask a sample text from the user, then it will be stored in the 'sample'
         */
        System.out.print("ENTER THE SAMPLE TEXT: ");
        String sample = input.nextLine();


        /**
         * Declare the model, it will declare a variable that will hold the results from Huffman Codebook
         */
        HuffmanCodebook model;
        //build model then handle user error inputs
        try {
            model = codec.buildModel(sample);
        } catch (IllegalArgumentException error) {
            System.out.println("Error: " + error.getMessage());
            return;
        }

        /**
         * Display the character table
         */
        System.out.print("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        System.out.printf("%n%-10s %-21s %s%n", "SYMBOL", "CHARACTER_FREQUENCY", "CODE");
        for (char symbol : model.frequencies.keySet()) {
            System.out.printf("%-10s %-21d %s%n", TreePrinter.showSymbol(symbol),
                    model.frequencies.get(symbol), model.codes.get(symbol));
        }
        System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        // 1.Encode original sample , 2.Calculates the origin bit size , 3. Calculate the save storage\
        //    Savings = ((Original Bits - Encoded Bits / Original Bits )) x 100
        String encodedSample = codec.encode(sample, model);
        int originalBits = sample.getBytes(StandardCharsets.UTF_8).length * 8;
        double savings = (originalBits - encodedSample.length()) * 100.0 / originalBits;

        // Display the results of Original, Encoded then the saved storage
        System.out.printf("RESULTS:%nOriginal bits = %d  |  ", originalBits);
        System.out.printf("Encoded bits = %d%n", encodedSample.length());
        System.out.printf("Storage savings = %.2f%%%n", savings);
        System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

        // Display the HuffmanTree, the TreePrinter class will responsible for the visual display of the huffman tree
        System.out.println("HUFFMAN TREE:");
        TreePrinter.printTree(model.root);

        // Start the 'Interactive Menu' after the display of RESULTS an the BINARY TREE
        System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        while (true) {
            System.out.print("1. Encode text\n2. Decode bits\n0. Exit\nChoice: ");
            String choice = input.nextLine();

            if (choice.equals("0")) {
                break;
            }

            try {
                if (choice.equals("1")) {
                    System.out.print("TEXT to encode: ");
                    String text = input.nextLine();
                    System.out.println("Encoded BITS: " + codec.encode(text, model));
                } else if (choice.equals("2")) {
                    System.out.print("BITS to decode: ");
                    String bits = input.nextLine();
                    System.out.println("Decoded TEXT: " + codec.decode(bits, model));
                } else {
                    System.out.println("Choose 1, 2, or 0.");
                }
            } catch (IllegalArgumentException error) {
                System.out.println("Error :  " + error.getMessage());
            }
        }
    }
}