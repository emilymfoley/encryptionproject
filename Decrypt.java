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
        char letter = (char) ('A' + n - 1);
        return Character.toString(letter);  
    }

    static void decryptMessages(Deck deck, Message msg) {

        Keystream keystream = new Keystream();
        keystream.setKeyStream(msg.getNumbers(), deck);
        int[][] keystreamValues = keystream.getValues(); 
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
                System.out.print(numberToLetterString(decryptedValue));
            }
            System.out.println();
        }
    }

}