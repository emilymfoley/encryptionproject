import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

// Decrypt.java (same package/folder)
    // Converts 1-26 to corresponding letter
class Decrypt {

    // Converts 1-26 to corresponding letter
    static String numberToLetterString(int n) {
        if (n < 1 || n > 26) {
            System.out.println("Number must be between 1 and 26");
        }
        char letter = (char) ('A' + n - 1);
        return Character.toString(letter);  // Convert char to String
    }


    // New method to do the decryption
    static void decryptMessages(String deckPath, String messagePath) {
        // Load deck
        Deck deck = new Deck(deckPath);

        // Read messages
        List<Message> messages = Message.readMsg(messagePath);

        // Create keystream
        Keystream ks = new Keystream(messages, deck);
        int[][] keystreamValues = ks.getValues(); 
        int[][] encryptedNumbers = new int[messages.size()][];

        // Convert messages to numbers
        for (int i = 0; i < messages.size(); i++) {
            Message msg = messages.get(i);
            int[] numbers = msg.getNumbers();  
            encryptedNumbers[i] = new int[numbers.length];
            for (int j = 0; j < numbers.length; j++) {
                encryptedNumbers[i][j] = numbers[j];
            }
        }


        // Decrypt
        for (int i = 0; i < encryptedNumbers.length; i++) {
            for (int j = 0; j < encryptedNumbers[i].length; j++) {
                int decryptedValue = encryptedNumbers[i][j] - keystreamValues[i][j];
                if (decryptedValue <= 0) decryptedValue += 26; // wrap around A–Z
                System.out.print(numberToLetterString(decryptedValue));
            }
            System.out.println();
        }
    }

    // main just calls the method
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Decrypt <deckFile> <encryptedMessageFile>");
            return;
        }
        decryptMessages(args[0], args[1]);
    }
}
