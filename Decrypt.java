import java.util.List;

class Decrypt {
/*---------------------------------------------------------------------
 |  Method MAIN
 |
 |  Purpose:  Main method for the Decryption program. This method reads
 |            command-line arguments specifying the deck file and the
 |            encrypted message file, initializes the Deck and Message
 |            objects, and runs the decryption method to output
 |            decrypted messages to the console.
 |
 |  Pre-condition: The program requires exactly two command line arguments:
 |                 1) the path to a valid deck file, and
 |                 2) the path to a valid encrypted message file.
 |
 |  Post-condition: The deck and message objects are initialized,
 |                  and the messages are decrypted and printed to
 |                  standard output.
 |
 |  Parameters:
 |      args -- an array of Strings from the command line:
 |              args[0] = path to the deck file
 |              args[1] = path to the encrypted message file
 |
 |  Returns:  None (void)
 *-------------------------------------------------------------------*/

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Decrypt <deckFile> <encryptedMessageFile>");
            return;
        }

        String deckPath= args[0];
        String encryptedMsgPath = args[1];

        Deck deck = new Deck();
        deck.setDeck(deckPath);

        Message encryptedMsg = new Message();
        encryptedMsg.setMessage(encryptedMsgPath);

        decryptMessages(deck, encryptedMsg);
    }
/*---------------------------------------------------------------------
 |  Method DECRYPT_MESSAGES
 |
 |  Purpose:  Decrypt a list of numeric messages using a Deck and the
 |            Solitaire keystream algorithm. This method generates a
 |            keystream matching the length of each message, subtracts
 |            the keystream values from the encrypted numbers, adjusts
 |            for modulo 26 arithmetic, and prints the resulting
 |            decrypted messages to the console.
 |
 |  Pre-condition: The Deck object has been initialized and populated
 |                 from a valid deck file. The Message object contains
 |                 one or more encrypted messages represented as numeric
 |                 arrays.
 |
 |  Post-condition: The encrypted messages are transformed back into
 |                  letters and printed to standard output. No class
 |                  fields or external data are modified.
 |
 |  Parameters:
 |      deck -- a Deck object containing the card ordering to generate
 |              the keystream.
 |      msg  -- a Message object containing the numeric representation
 |              of the encrypted messages.
 |
 |  Returns:  None (void)
 *-------------------------------------------------------------------*/
    static void decryptMessages(Deck deck, Message msg) {

        Keystream keystream = new Keystream();
        keystream.setKeyStream(msg.getNumbers(), deck);
        int[][] keystreamValues = keystream.getKeystream(); 
        int[][] encryptedNumbers = new int[msg.getNumbers().size()][];
        List<int[]> messagesNumbers = msg.getNumbers(); 
        encryptedNumbers = new int[messagesNumbers.size()][];

        for (int i = 0; i < messagesNumbers.size(); i++) {
            int[] numbersArray = messagesNumbers.get(i);         
            encryptedNumbers[i] = new int[numbersArray.length]; 

            for (int j = 0; j < numbersArray.length; j++) {
                encryptedNumbers[i][j] = numbersArray[j];        
            }
        }

        System.out.println("Decrypted Messages:");
        System.out.println("===================");
        for (int i = 0; i < encryptedNumbers.length; i++) {
            for (int j = 0; j < encryptedNumbers[i].length; j++) {
                int decryptedValue = encryptedNumbers[i][j] - keystreamValues[i][j];
                if (decryptedValue <= 0) decryptedValue += 26; 
                System.out.print(msg.numberToLetter(decryptedValue));
            }
            System.out.println();
        }
    }

}