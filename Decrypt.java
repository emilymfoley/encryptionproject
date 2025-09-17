import java.util.List;


class Decrypt {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Decrypt <deckFile> <encryptedMessageFile>");
            return;
        }

        String deckPath= args[0];
        String encryptedMsgPath = args[1];

        Deck deck = new Deck();
        deck.setDeck(deckPath);

        Message msg = new Message();
        msg.setMessage(encryptedMsgPath);

        decryptMessages(deck, msg);
    }

    static String numberToLetterString(int n) {
        if (n < 1 || n > 26) {
            System.out.println("Number must be between 1 and 26");
        }
        char letter = (char) ('A' + n - 1);
        return Character.toString(letter);  
    }

    static void decryptMessages(Deck deck, Message msg) {

        Keystream keystream = new Keystream();
        keystream.setKeyStream(msg.getNumbers(), deck);
        int[][] keystreamValues = keystream.getValues(); 
        int[][] encryptedNumbers = new int[msg.getNumbers().size()][];
        List<int[]> messagesNumbers = msg.getNumbers();  // get the List<int[]> for this message
        encryptedNumbers = new int[messagesNumbers.size()][];

        for (int i = 0; i < messagesNumbers.size(); i++) {
            int[] numbersArray = messagesNumbers.get(i);         // get the int[] at index i
            encryptedNumbers[i] = new int[numbersArray.length];  // initialize inner array

            for (int j = 0; j < numbersArray.length; j++) {
                encryptedNumbers[i][j] = numbersArray[j];        
            }
        }

        for (int i = 0; i < encryptedNumbers.length; i++) {
            for (int j = 0; j < encryptedNumbers[i].length; j++) {
                int decryptedValue = encryptedNumbers[i][j] - keystreamValues[i][j];
                if (decryptedValue <= 0) decryptedValue += 26; // wrap around A–Z
                System.out.print(numberToLetterString(decryptedValue));
            }
            System.out.println();
        }
    }

}