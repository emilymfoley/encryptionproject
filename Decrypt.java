import java.util.List;

<<<<<<< HEAD
// Decrypt.java (same package/folder)
    // Converts 1-26 to corresponding letter
class Decrypt {

    // Converts 1-26 to corresponding letter
    static String numberToLetterString(int n) {
        if (n < 1 || n > 26) {
            throw new IllegalArgumentException("Number must be between 1 and 26");
        }
        return String.valueOf((char) ('A' + n - 1));
    }

    // New method to do the decryption
    static void decryptMessages(String deckPath, String messagePath) {
        // Load deck
=======
class Decrypt {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Decrypt <deckFile> <encryptedMessageFile>");
            return;
        }

        String deckPath = args[0];
        String messagePath = args[1];

>>>>>>> 0e8151f055be8788a9ab98f463273d5e005536a8
        Deck deck = new Deck(deckPath);
        List<Message> messages = Message.readMsg(messagePath);
        Keystream keystream = new Keystream(messages, deck);

<<<<<<< HEAD
        // Create keystream
        Keystream ks = new Keystream(messages, deck);
        int[][] keystreamValues = ks.getValues(); 
=======
        decryptMessages(keystream, messages);
    }

    static void decryptMessages(Keystream keystream, List<Message> messages) {
 
        int[][] keystreamValues = keystream.getValues(); 
>>>>>>> 0e8151f055be8788a9ab98f463273d5e005536a8
        int[][] encryptedNumbers = new int[messages.size()][];

        // Convert messages to numbers
        for (int i = 0; i < messages.size(); i++) {
            Message msg = messages.get(i);
            int[] numbers = msg.getNumbers(); 
            encryptedNumbers[i] = new int[numbers.length]; 
            System.arraycopy(numbers, 0, encryptedNumbers[i], 0, numbers.length);
        }

        // Decrypt
        for (int i = 0; i < encryptedNumbers.length; i++) {
            for (int j = 0; j < encryptedNumbers[i].length; j++) {
                int decryptedValue = encryptedNumbers[i][j] - keystreamValues[i][j];
                if (decryptedValue <= 0) decryptedValue += 26;
                System.out.print(Utils.numberToLetterString(decryptedValue));
            }
            System.out.println();
        }
<<<<<<< HEAD
    }

    // main just calls the method
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Decrypt <deckFile> <encryptedMessageFile>");
            return;
        }
        decryptMessages(args[0], args[1]);
=======
>>>>>>> 0e8151f055be8788a9ab98f463273d5e005536a8
    }
}
