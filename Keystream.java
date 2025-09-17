import java.util.List;


class Keystream {

    int[][] keystreamValues;  //initializing a ragged array representing keystream values

    Keystream(List<Message> messages, Deck deck) {
        keystreamValues = new int[messages.size()][];  // store the keystream values

        for (int i = 0; i < messages.size(); i++) { 
            Message msg = messages.get(i);
            int[] numbers = msg.getNumbers();  // numeric representation
            keystreamValues[i] = new int[numbers.length];


            for (int j = 0; j < numbers.length; j++) {
                    while(true){
                        deck.moveJoker27();
                        deck.moveJoker28();
                        deck.tripleCut();
                        deck.countCut();

                        int topCardValue = deck.getDeck()[0];
                        int count = (topCardValue >= 27) ? 27 : topCardValue;
                        int next = deck.getDeck()[count];

                        if (next != 27 && next != 28) {
                            keystreamValues[i][j] = next;  // store keystream value
                            break;
                        }
                    }
                }
        }
    }

  
    int[][] getValues() {
        return keystreamValues;
    }
}