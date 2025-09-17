import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

class Encrypt {
/*---------------------------------------------------------------------
 |  Method MAIN
 |
 |  Purpose:  Main method for the encryption program. This method reads
 |            command-line arguments specifying the deck file and the
 |            plaintext message file, initializes the Deck and Message
 |            objects, generates the keystream using the Deck, and
 |            invokes the encryption process to transform messages into
 |            encrypted form.
 |
 |  Pre-condition: The program is executed with exactly two arguments:
 |                 1) the path to a valid deck file, and
 |                 2) the path to a valid plaintext message file.
 |
 |  Post-condition: The deck and message objects are initialized, the
 |                  keystream is generated, and the messages are
 |                  encrypted. The encrypted output is prepared for
 |                  further processing or storage.
 |
 |  Parameters:
 |      args -- an array of Strings from the command line:
 |              args[0] = path to the deck file
 |              args[1] = path to the plaintext message file
 |
 |  Returns:  None (void)
 *-------------------------------------------------------------------*/

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

        encryptMessages(messages, keystream);

    }
/*---------------------------------------------------------------------
 |  Method ENCRYPT_MESSAGES
 |
 |  Purpose:  Encrypt a list of numeric messages using a Keystream
 |            generated from a Deck. Each numeric message is combined
 |            with the corresponding keystream values using modular
 |            arithmetic (mod 26) to produce encrypted letters. The
 |            resulting encrypted messages are printed to the console
 |            and written to a file named "encryptedMessage.txt".
 |
 |  Pre-condition: The Message object contains one or more plaintext
 |                 messages represented as numeric arrays. The Keystream
 |                 object has been initialized and contains keystream
 |                 values matching the length of each message.
 |
 |  Post-condition: The messages are encrypted and output to both
 |                  standard output and the file "encryptedMessage.txt".
 |                  No original message data is modified.
 |
 |  Parameters:
 |      message   -- a Message object containing numeric representations
 |                   of plaintext messages.
 |      keystream -- a Keystream object containing the keystream values
 |                   for encryption.
 |
 |  Returns:  None (void)
 *-------------------------------------------------------------------*/
    static void encryptMessages(Message message, Keystream keystream) {
        List<int[]> msgNumbers = message.getNumbers();
        String[][] encryptedMessage = new String[msgNumbers.size()][];

        for (int i = 0; i < msgNumbers.size(); i++) {
            int[] numbers = msgNumbers.get(i); 
            encryptedMessage[i] = new String[numbers.length];

            for (int j = 0; j < numbers.length; j++) {
                int encryptedNumbers = numbers[j] + keystream.getKeystream()[i][j];
                int modSum = encryptedNumbers % 26;
                if (modSum == 0) modSum = 26;
                encryptedMessage[i][j] = message.numberToLetter(modSum);
            }
        }

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
            System.out.println("Error writing to file");
        }

  
    }
}