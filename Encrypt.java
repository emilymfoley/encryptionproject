import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

class Encrypt {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Encrypt <deckFile> <messageFile>");
            return;
        }

        String deckPath = args[0];
        String messagePath = args[1];

        Deck deck = new Deck(deckPath);
        List<Message> messages = Message.readMsg(messagePath);

        // Initialize keystream and generate values
        Keystream keystream = new Keystream(messages,deck);

        // Encrypt messages
        String[][] encryptedMessage = encryptMessages(messages, keystream);

        // Write encrypted messages to file
        try (PrintWriter writer = new PrintWriter(new FileWriter("encryptedMessage.txt"))) {
            for (int i = 0; i < encryptedMessage.length; i++) {
                for (int j = 0; j < encryptedMessage[i].length; j++) {
                    writer.print(encryptedMessage[i][j]);
                }
                writer.println();
            }
            System.out.println("Encrypted message written to encryptedMessage.txt");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    static String[][] encryptMessages(List<Message> messages, Keystream keystream) {
        String[][] encryptedMessage = new String[messages.size()][];

        for (int i = 0; i < messages.size(); i++) {
            int[] numbers = messages.get(i).getNumbers();
            encryptedMessage[i] = new String[numbers.length];

            for (int j = 0; j < numbers.length; j++) {
                int encryptedNumbers = numbers[j] + keystream.getValues()[i][j];
                int modSum = encryptedNumbers % 26;
                if (modSum == 0) modSum = 26;
                encryptedMessage[i][j] = Utils.numberToLetter(modSum);
            }
        }

        return encryptedMessage;
    }
}