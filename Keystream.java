/*+----------------------------------------------------------------------
 ||
 ||  Class Keystream
 ||
 ||         Author:  Emily Foley
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

    Keystream() {

    }
/*---------------------------------------------------------------------
 |  Methods SETKEYSTREAM, GETKEYSTREAM
 |
 |  Purpose:  
 |      SETKEYSTREAM -- Initialize the Keystream object by generating
 |                        the keystream values from a list of numeric
 |                        messages and a given Deck object using the
 |                        encryption algorithm.
 |      GETKEYSTREAM -- Retrieve the 2D array of keystream values
 |                        previously generated.
 |
 |  Pre-condition:  
 |      SETKEYSTREAM -- numericMessages contains valid numeric message
 |                        representations; deck has been properly initialized.
 |      GETKEYSTREAM -- setKeyStream() has already been called to populate
 |                        the keystreamValues field.
 |
 |  Post-condition:  
 |      SETKEYSTREAM -- keystreamValues is populated with numeric values
 |                        corresponding to the encryption keystream.
 |      GETKEYSTREAM -- Returns a reference to the existing keystreamValues
 |                        array; no modification occurs.
 |
 |  Parameters:
 |      SETKEYSTREAM: numericMessages -- List<int[]> of numeric message arrays
 |                     deck -- Deck object representing the current deck state
 |      GETKEYSTREAM: None
 |
 |  Returns:
 |      SETKEYSTREAM: None (void)
 |      GETKEYSTREAM: int[][] representing the keystream values for messages
 *-------------------------------------------------------------------*/
    void setKeyStream(List<int[]> numericMessages, Deck deck){
        this.keystreamValues = encryptionAlgorithm(numericMessages, deck);
    }

    int[][] getKeystream() {
        return keystreamValues;
    }
/*---------------------------------------------------------------------
 |  Method ENCRYPTIONALGORITHM
 |
 |  Purpose:  Generate a 2D array of keystream values for a list of numeric
 |            messages using a given Deck object according to the Solitaire
 |            cipher algorithm. The method repeatedly manipulates the deck
 |            (joker moves, triple cut, count cut) to produce valid keystream
 |            numbers that will be used for message encryption.
 |
 |  Pre-condition:  
 |      numericMessages -- List<int[]> containing numeric representations of
 |                          messages (1–26 for letters).  
 |      deck -- Deck object properly initialized and representing a valid 28-card deck.
 |
 |  Post-condition:  
 |      Returns a 2D array of integers where each sub-array corresponds to
 |      the keystream values for a specific message. The keystreamValues field
 |      of the Keystream object is updated with these values.
 |
 |  Parameters:
 |      numericMessages -- List<int[]> of numeric message arrays to generate
 |                          keystream for.
 |      deck -- Deck object used to generate the keystream numbers.
 |
 |  Returns:  int[][] containing the keystream values for all messages.
 *-------------------------------------------------------------------*/

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
  
}