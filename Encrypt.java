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

        Deck deck = new Deck();
        deck.setDeck(deckPath);

        Message messages = new Message();
        messages.setMessage(messagePath);


        Keystream keystream = new Keystream();
        keystream.encryptionAlgorithm(messages.getNumbers(), deck);

        String[][] encryptedMessage = encryptMessages(messages, keystream);

        try (PrintWriter writer = new PrintWriter(new FileWriter("encryptedMessage.txt"))) {
            System.out.println("Encrypted Messages:");
            System.out.println("===================");
            for (int i = 0; i < encryptedMessage.length; i++) {
                for (int j = 0; j < encryptedMessage[i].length; j++) {
                    writer.print(encryptedMessage[i][j]);
                }
                System.out.println(String.join("", encryptedMessage[i]));
                writer.println();
            }
            System.out.println("Encrypted message written to encryptedMessage.txt");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    static String[][] encryptMessages(Message message, Keystream keystream) {
        List<int[]> numbersList = message.getNumbers();
        String[][] encryptedMessage = new String[numbersList.size()][];

        for (int i = 0; i < numbersList.size(); i++) {
            int[] numbers = numbersList.get(i); 
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