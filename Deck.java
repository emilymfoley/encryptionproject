/*+----------------------------------------------------------------------
 ||
 ||  Class Deck
 ||
 ||         Author:  Emily Foley
 ||
 ||        Purpose:  Encapsulates a deck of 28 cards used in the Solitaire
 ||                  Cipher algorithm. Provides functionality to initialize
 ||                  the deck from a file and to perform all required
 ||                  manipulations: swapping, moving jokers, triple cutting,
 ||                  and count cutting.
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
 ||   Constructors:  
 ||      - Deck() : initializes a deck of 28 integers, unshuffled until
 ||                 populated by setDeck().
 ||
 ||  Class Methods:  
 ||      None
 ||
 ||  Inst. Methods:  
 ||      - void setDeck(String deckFilePath)
 ||           Reads a deck configuration from a file and initializes this
 ||           object’s deck accordingly.
 ||
 ||      - int[] getDeck()
 ||           Returns the current state of the deck as an array.
 ||
 ||      - int[] readDeckFile(String deckFilePath)
 ||           Reads and parses a deck file, returning the deck as an array
 ||           of integers.
 ||
 ||      - void swap(int i, int j)
 ||           Swaps the positions of two cards in the deck.
 ||
 ||      - int findIndex(int value)
 ||           Finds the index of a given card in the deck.
 ||
 ||      - void swapSlice(int i, int j, int k, int l)
 ||           Rearranges the deck by slicing and concatenating subarrays.
 ||
 ||      - void moveJoker27()
 ||           Moves joker A (27) down one position (wrapping if necessary).
 ||
 ||      - void moveJoker28()
 ||           Moves joker B (28) down two positions (wrapping if necessary).
 ||
 ||      - void tripleCut()
 ||           Performs a triple cut operation using the two jokers as
 ||           delimiters.
 ||
 ||      - void countCut()
 ||           Performs a count cut based on the value of the bottom card.
 ||
 ||      - int cardToNumber(String card)
 ||           Helper method that converts a string card label (e.g., "AC",
 ||           "10D", "JB") into its corresponding integer value.
 ||
 ++-----------------------------------------------------------------------*/
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Deck {
    private int[] deck;   // Field representing the deck of 28 cards

    /*---------------------------------------------------------------------
     |  Constructor Deck
     |
     |  Purpose: Initializes an empty deck of size 28. The contents are
     |           populated later via setDeck().
     |
     |  Pre-condition: None
     |  Post-condition: A new Deck object exists with a 28-element int array.
     |
     |  Parameters: None
     |
     |  Returns: None (constructor)
     *-------------------------------------------------------------------*/
    Deck() {
        this.deck = new int[28];
    }

    /*---------------------------------------------------------------------
    |  Methods GET_DECK and SET_DECK
    |
    |  Purpose:  Provide controlled access to the private instance variables
    |            of the Deck class. Getter methods allow other classes to
    |            retrieve values (like the deck array), while setter methods
    |            allow other classes to modify the internal state of the deck
    |            in a safe manner without exposing the private variables
    |            directly.
    |
    |  Pre-condition:  A Deck object has been properly constructed.
    |                  Input values for setters must be valid (e.g., a complete
    |                  deck array, or integers representing card values).
    |
    |  Post-condition: Getter methods return the requested values without
    |                  altering the Deck object. Setter methods update the
    |                  internal deck state according to the caller's input.
    |
    |  Parameters:
    |      Getters -- none (simply return the current value of a private field)
    |      Setters -- accept new values (array or integer) to update the private fields
    |
    |  Returns:  
    |      Getters -- the current value of the requested field
    |      Setters -- none (void)
    *-------------------------------------------------------------------*/

    void setDeck(String deckFilePath) {
         this.deck = readDeckFile(deckFilePath);
    }

    int[] getDeck() {
        return deck;
    }

/*---------------------------------------------------------------------
 |  Method READ_DECK_FILE
 |
 |  Purpose:  Helper method to read a deck configuration from a text file 
 |            located at deckFilePath and populate the Deck object's internal
 |            deck array with 28 integer values corresponding to cards.
 |            Each card in the file is converted to its numeric value
 |            using the cardToNumber helper method. The method checks
 |            for common errors such as missing file, empty file, or
 |            an incomplete deck.
 |
 |  Pre-condition: deckFilePath points to a file that exists on disk.
 |                 The file may contain up to 28 valid card identifiers
 |                 in a format recognized by cardToNumber.
 |
 |  Post-condition: The internal deck array is populated with the numeric
 |                  values of the cards read from the file, in order.
 |                  If an error occurs (file missing, empty, or incomplete),
 |                  appropriate error messages are printed and the deck
 |                  may remain partially populated or null.
 |
 |  Parameters:
 |      deckFilePath -- the path to the text file containing the deck.
 |                      Each token in the file should be a valid card
 |                      string (e.g., "AC", "2C", ..., "JB").
 |
 |  Returns:  An integer array of length 28 representing the deck, with
 |            cards converted to numeric values. Returns null if the
 |            file does not exist or is empty.
 *-------------------------------------------------------------------*/
    private int[] readDeckFile(String deckFilePath) {
        File deckFile = new File(deckFilePath);
        boolean deckExists = deckFile.exists();
        if (deckExists == false) {
            System.out.println("Error: deck file not found");
            return null;
        }

        if (deckFile.length() == 0) {
            System.out.println("Error: deck file is empty");
            return null;
        }

        try (Scanner deckScanner = new Scanner(deckFile)) {
            int i = 0;
            while (deckScanner.hasNext() && i < 28) {
                String card = deckScanner.next();
                deck[i] = cardToNumber(card); 
                i++;
            }

            if (i < 28) {
                System.out.println("Error: deck file does not contain 28 cards");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error reading deck file");
        }
        return deck;
    }

/*---------------------------------------------------------------------
 |  Method SWAP
 |
 |  Purpose:  Exchange the positions of two cards in the deck array.
 |            This is a simple helper method used internally by other
 |            deck-manipulation methods such as moveJoker27, moveJoker28,
 |            and tripleCut.
 |
 |  Pre-condition: The Deck object has a properly initialized deck array
 |                 of length 28. The indices i and j must be within
 |                 the range 0 to 27.
 |
 |  Post-condition: The values at positions i and j in the deck array
 |                  have been swapped. The rest of the deck remains
 |                  unchanged.
 |
 |  Parameters:
 |      i -- the index of the first card to be swapped.
 |      j -- the index of the second card to be swapped.
 |
 |  Returns:  None (void)
 *-------------------------------------------------------------------*/
    private void swap(int i, int j) {
        int first = deck[i];
        deck[i] = deck[j];
        deck[j] = first;
    }
/*---------------------------------------------------------------------
 |  Method FIND_INDEX
 |
 |  Purpose:  Locate the position of a specific card value in the deck
 |            array. This method is used internally by other deck
 |            manipulation methods to identify the locations of jokers
 |            or specific cards.
 |
 |  Pre-condition: The Deck object has a properly initialized deck array
 |                 of length 28. The input value corresponds to a valid
 |                 card number (1–28).
 |
 |  Post-condition: The deck array remains unchanged.
 |
 |  Parameters:
 |      value -- the numeric value of the card to locate in the deck.
 |
 |  Returns:  The index (0–27) of the first occurrence of the value in
 |            the deck array; returns -1 if the value is not found.
 *-------------------------------------------------------------------*/
    private int findIndex(int value){ 
        for (int i = 0; i < 28; i++) {
            if (deck[i] == value) {
                return i;  
            }
        }
        return -1;
    }
/*---------------------------------------------------------------------
 |  Method SWAP_SLICE
 |
 |  Purpose:  Rearrange multiple contiguous segments of the deck array
 |            by placing the segment from indices k to l at the front,
 |            followed by any intervening cards, then the segment from
 |            indices i to j, and finally any remaining cards at the end.
 |            The indices i,j,k, and l are inclusive. This method is used
 |            internally by the tripleCut operation.
 |
 |  Pre-condition: The Deck object has a properly initialized deck array
 |                 of length 28. Indices i, j, k, and l must satisfy
 |                 0 ≤ i ≤ j < k ≤ l < 28.
 |
 |  Post-condition: The deck array has been reordered according to the
 |                  specified slices. No card is lost or duplicated.
 |
 |  Parameters:
 |      i -- start index of the first segment to be moved after the
 |           k–l segment.
 |      j -- end index of the first segment to be moved.
 |      k -- start index of the segment to move to the front.
 |      l -- end index of the segment to move to the front.
 |
 |  Returns:  None (void)
 *-------------------------------------------------------------------*/
    private void swapSlice(int i, int j, int k, int l) {
        int[] newDeck = new int[28];
        int index = 0;

        for (int m = k; m <= l; m++) {
            newDeck[index++] = deck[m];
        }

        if (k > j + 1) {
            for (int m = j + 1; m < k; m++) {
                newDeck[index++] = deck[m];
            }
        }

        for (int m = i; m <= j; m++) {
            newDeck[index++] = deck[m];
        }

        if (l + 1 < 28) {
            for (int m = l + 1; m < 28; m++) {
                newDeck[index++] = deck[m];
            }
        }

        deck = newDeck;
    }

/*---------------------------------------------------------------------
 |  Method MOVE_JOKER27
 |
 |  Purpose:  Move the joker with value 27 (Joker A) one position down
 |            in the deck array. If Joker A is currently at the bottom
 |            of the deck (index 27), it wraps around to the top (index 0).
 |            This operation is part of the Solitaire encryption algorithm.
 |
 |  Pre-condition: The Deck object has a properly initialized deck array
 |                 of length 28 containing the value 27 exactly once.
 |
 |  Post-condition: Joker A has been moved one position down in the deck,
 |                  with all other cards remaining in their original order.
 |
 |  Parameters:  None
 |
 |  Returns:  None (void)
 *-------------------------------------------------------------------*/
    void moveJoker27(){
        int jokerIndexA = findIndex(27);
        if (jokerIndexA == 27) {
            swap(jokerIndexA, 0);
        } else {
            swap(jokerIndexA, jokerIndexA + 1);
        }
    }

/*---------------------------------------------------------------------
 |  Method MOVE_JOKER28
 |
 |  Purpose:  Move the joker with value 28 (Joker B) two positions down
 |            in the deck array. If Joker B reaches the bottom, it wraps
 |            around to the top of the deck. This operation is part of
 |            the Solitaire encryption algorithm.
 |
 |  Pre-condition: The Deck object has a properly initialized deck array
 |                 of length 28 containing the value 28 exactly once.
 |
 |  Post-condition: Joker B has been moved two positions down in the deck,
 |                  with all other cards remaining in their original order.
 |
 |  Parameters:  None
 |
 |  Returns:  None (void)
 *-------------------------------------------------------------------*/

    void moveJoker28(){
        for (int step = 0; step < 2; step++) {
            int jokerIndexB = findIndex(28);
            if (jokerIndexB == 27) {
                swap(jokerIndexB, 0);
            } else {
                swap(jokerIndexB, jokerIndexB + 1);
            }
        }
    }
/*---------------------------------------------------------------------
 |  Method TRIPLE_CUT
 |
 |  Purpose:  Perform the triple cut operation on the deck array as part
 |            of the Solitaire encryption algorithm. All cards above the
 |            first joker are moved to the bottom, and all cards below
 |            the second joker are moved to the top, leaving the jokers
 |            and the cards between them in their original order.
 |
 |  Pre-condition: The Deck object has a properly initialized deck array
 |                 of length 28 containing both jokers (27 and 28).
 |
 |  Post-condition: The deck array has been rearranged according to the
 |                  triple cut rules; no cards are lost or duplicated.
 |
 |  Parameters:  None
 |
 |  Returns:  None (void)
 *-------------------------------------------------------------------*/
    void tripleCut(){
        int index27 = findIndex(27);
        int index28 = findIndex(28);
        int firstJoker = Math.min(index27, index28);
        int secondJoker = Math.max(index27, index28);
        swapSlice(0, firstJoker - 1, secondJoker + 1, 27);
    }
/*---------------------------------------------------------------------
 |  Method COUNT_CUT
 |
 |  Purpose:  Perform the count cut operation on the deck array as part
 |            of the Solitaire encryption algorithm. The value of the
 |            bottom card (unless it is a joker) determines how many
 |            cards from the top of the deck are moved just above the
 |            bottom card, leaving the bottom card in place.
 |
 |  Pre-condition: The Deck object has a properly initialized deck array
 |                 of length 28. The bottom card contains a valid card
 |                 value (1–28).
 |
 |  Post-condition: The deck array has been rearranged according to the
 |                  count cut rules; no cards are lost or duplicated.
 |
 |  Parameters:  None
 |
 |  Returns:  None (void)
 *-------------------------------------------------------------------*/
    void countCut(){
        int[] oldDeck = getDeck();
        int bottomCard = oldDeck[27];
        if (bottomCard != 27 && bottomCard != 28) {
            int[] newDeck = new int[28];
            int index = 0;

            for (int ii = bottomCard; ii < 27; ii++) {
                newDeck[index++] = oldDeck[ii];
            }

            for (int ii = 0; ii < bottomCard; ii++) {
                newDeck[index++] = oldDeck[ii];
            }

            newDeck[27] = oldDeck[27];
            this.deck = newDeck;
        }
    }

    /*---------------------------------------------------------------------
     |  Method cardToNumber
     |
     |  Purpose: Converts a card label (string form) into its integer value
     |           (1–26 for regular cards, 27 and 28 for jokers).
     |
     |  Pre-condition: Input must be a valid card label.
     |  Post-condition: An integer corresponding to the card is returned.
     |
     |  Parameters:
     |      card -- the string identifier of a card (e.g., "AC", "10D", "JA").
     |
     |  Returns: int -- the numeric representation of the card.
     *-------------------------------------------------------------------*/
    private int cardToNumber(String card) {
        switch (card.toUpperCase()) {
            case "AC": return 1;
            case "2C": return 2;
            case "3C": return 3;
            case "4C": return 4;
            case "5C": return 5;
            case "6C": return 6;
            case "7C": return 7;
            case "8C": return 8;
            case "9C": return 9;
            case "10C": return 10;
            case "JC": return 11;
            case "QC": return 12;
            case "KC": return 13;

            case "AD": return 14;
            case "2D": return 15;
            case "3D": return 16;
            case "4D": return 17;
            case "5D": return 18;
            case "6D": return 19;
            case "7D": return 20;
            case "8D": return 21;
            case "9D": return 22;
            case "10D": return 23;
            case "JD": return 24;
            case "QD": return 25;
            case "KD": return 26;

            case "JA": return 27; 
            case "JB": return 28;

            default:
                return 0;
        }
    }

}
