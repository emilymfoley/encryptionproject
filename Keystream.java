import java.util.List;


class Keystream {

    int[][] keystreamValues;  //initializing a ragged array representing keystream values

    void setKeyStream(List<int[]> numericMessages, Deck deck){
        this.keystreamValues = encryptionAlgorithm(numericMessages, deck);
    }

    Keystream() {
    }

    int[][] encryptionAlgorithm(List<int[]> numericMessages, Deck deck){
        keystreamValues = new int[numericMessages.size()][];
        for (int i = 0; i < numericMessages.size(); i++) { 
            int[] msgNumbers = numericMessages.get(i);       // numeric representation of the i-th message
            keystreamValues[i] = new int[msgNumbers.length];
        
            for (int j = 0; j < msgNumbers.length; j++) {
                    while(true){
                        deck.moveJoker27();
                        deck.moveJoker28();
                        deck.tripleCut();
                        deck.countCut();

                        int topCardValue = deck.getDeck()[0];
                        int count = (topCardValue >= 27) ? 27 : topCardValue;
                        int next = deck.getDeck()[count];

                        if (next != 27 && next != 28) {
                            keystreamValues[i][j] = next;  
                            break;
                        }
                    }
                }
        }

        return keystreamValues;
    }
  
    int[][] getValues() {
        return keystreamValues;
    }
}