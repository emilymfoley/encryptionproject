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

                        // --- Step 4: Count cut ---
                        int[] arr = deck.getDeck();
                        int n = arr.length;
                        int bottomCard = arr[n - 1];
                        if (bottomCard != 27 && bottomCard != 28) {
                            int[] newDeck = new int[n];
                            int index = 0;

                            for (int ii = bottomCard; ii < n - 1; ii++) {
                                newDeck[index++] = arr[ii];
                            }

                            for (int ii = 0; ii < bottomCard; ii++) {
                                newDeck[index++] = arr[ii];
                            }

                            newDeck[n - 1] = arr[n - 1];
                            deck.setDeck(newDeck);
                        }


                        // --- Step 5: Output keystream value ---
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