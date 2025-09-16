import java.util.List;


class Keystream {

    int[][] keystreamValues;  //initializing a ragged array representing keystream values

    Keystream(List<int[]> numericMessages, Deck deck) {
    keystreamValues = new int[numericMessages.size()][];  // store the keystream values

    for (int i = 0; i < numericMessages.size(); i++) { 
        int[] msgNumbers = numericMessages.get(i);       // numeric representation of the i-th message
        keystreamValues[i] = new int[msgNumbers.length]; // initialize the row

        for (int j = 0; j < msgNumbers.length; j++) {
            while (true) {
                // --- Step 1: Move Joker A (27) ---
                int jokerIndexA = deck.findIndex(27);
                if (jokerIndexA == deck.getDeck().length - 1) {
                    deck.swap(jokerIndexA, 0);
                } else {
                    deck.swap(jokerIndexA, jokerIndexA + 1);
                }

                // --- Step 2: Move Joker B (28) down 2 ---
                for (int step = 0; step < 2; step++) {
                    int jokerIndexB = deck.findIndex(28);
                    if (jokerIndexB == deck.getDeck().length - 1) {
                        deck.swap(jokerIndexB, 0);
                    } else {
                        deck.swap(jokerIndexB, jokerIndexB + 1);
                    }
                }

                // --- Step 3: Triple cut ---
                int index27 = deck.findIndex(27);
                int index28 = deck.findIndex(28);
                int firstJoker = Math.min(index27, index28);
                int secondJoker = Math.max(index27, index28);
                deck.swapSlice(0, firstJoker - 1, secondJoker + 1, deck.getDeck().length - 1);

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