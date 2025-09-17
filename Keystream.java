/*+----------------------------------------------------------------------
 ||
 ||  Class Keystream
 ||
 ||         Author:  Emily Margaret Foley
 ||
 ||        Purpose:  This class generates keystream values used in the
 ||                   Solitaire cipher encryption and decryption process.
 ||                   It manipulates a deck of cards using defined rules
 ||                   (joker moves, triple cut, count cut) to produce a
 ||                   pseudo-random sequence of numbers. These numbers are
 ||                   then used to transform the plaintext messages into
 ||                   ciphertext, or ciphertext back into plaintext.
 ||
 ||  Inherits From:  None
 ||
 ||     Interfaces:  None
 ||
 |+-----------------------------------------------------------------------
 ||
 ||      Constants:  None
 ||
 |+-----------------------------------------------------------------------
 ||
 ||   Constructors:  Keystream() – Default constructor, initializes an
 ||                                empty keystream structure.
 ||
 ||  Class Methods:  None
 ||
 ||  Inst. Methods:  void setKeyStream(List<int[]> numericMessages, Deck deck)
 ||                        – Generates the keystream for each message
 ||                          using the provided deck.
 ||
 ||                   int[][] encryptionAlgorithm(List<int[]> numericMessages, Deck deck)
 ||                        – Core implementation of the Solitaire cipher
 ||                          keystream algorithm; returns a ragged array
 ||                          of keystream values corresponding to each
 ||                          character of each message.
 ||
 ||                   int[][] getValues()
 ||                        – Returns the generated keystream values.
 ||
 ++-----------------------------------------------------------------------*/
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
                        int count;
                        if (topCardValue >= 27) {
                            count = 27;
                        } else {
                            count = topCardValue;
                        }
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