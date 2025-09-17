/*+----------------------------------------------------------------------
 ||
 ||  Class Deck
 ||
 ||         Author:  Emily Margaret Foley
 ||
 ||        Purpose:  This class represents and manipulates a deck of 28
 ||                   cards (52 standard cards reduced into 26 values +
 ||                   2 jokers) as used in the Solitaire cipher. It
 ||                   provides operations such as joker movements, triple
 ||                   cut, and count cut, which are core to generating
 ||                   the keystream values. It also supports initializing
 ||                   the deck from a file and converting string card
 ||                   representations into numeric form.
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
 ||   Constructors:  Deck() – Default constructor, initializes an empty
 ||                              28-card deck.
 ||
 ||  Class Methods:  None
 ||
 ||  Inst. Methods:  void setDeck(String deckFilePath)
 ||                        – Reads and sets up the deck from the given
 ||                          file.
 ||
 ||                   int[] getDeck()
 ||                        – Returns the current deck as an array of ints.
 ||
 ||                   private int[] readDeckFile(String deckFilePath)
 ||                        – Helper method, reads deck contents from file 
 ||                          and converts card codes (e.g., "AC", "10D") into
 ||                          numeric values. Returns the initialized deck.
 ||
 ||                   private void swap(int i, int j)
 ||                        – Helper method that swaps two cards in the 
 ||                          deck at positions i and j.
 ||
 ||                   private int findIndex(int value)
 ||                        – Helper method that finds and returns the index 
 ||                          of a card with the given value in the deck.
 ||
 ||                   private void swapSlice(int i, int j, int k, int l)
 ||                        – Helper method that earranges deck sections 
 ||                          as part of the triple cut process.
 ||
 ||                   void moveJoker27()
 ||                        – Moves Joker A (value 27) down one position
 ||                          in the deck.
 ||
 ||                   void moveJoker28()
 ||                        – Moves Joker B (value 28) down two positions
 ||                          in the deck.
 ||
 ||                   void tripleCut()
 ||                        – Performs a triple cut around the two jokers.
 ||
 ||                   void countCut()
 ||                        – Performs the count cut using the value of
 ||                          the bottom card.
 ||
 ||                   private int cardToNumber(String card)
 ||                        – Helper method that maps a string card code
 ||                          into its corresponding numeric value.
 ||
 ++-----------------------------------------------------------------------*/
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Deck {
    private int[] deck;     

    Deck() {
        this.deck = new int[28];
    }

    void setDeck(String deckFilePath){
         this.deck= readDeckFile(deckFilePath);
    }

    int[] getDeck() {
        return deck;
    }

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

    private void swap(int i, int j) {
        int first = deck[i];
        deck[i] = deck[j];
        deck[j] = first;
    }

    private int findIndex(int value){ 
        for (int i = 0; i < 28; i++) {
            if (deck[i] == value) {
                return i;  
            }
        }
        return -1;
    }

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

    void moveJoker27(){
        int jokerIndexA = findIndex(27);
        if (jokerIndexA == 27) {
            swap(jokerIndexA, 0);
        } else {
            swap(jokerIndexA, jokerIndexA + 1);
        }
    }

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

    void tripleCut(){
        int index27 = findIndex(27);
        int index28 = findIndex(28);
        int firstJoker = Math.min(index27, index28);
        int secondJoker = Math.max(index27, index28);
        swapSlice(0, firstJoker - 1, secondJoker + 1, 27);
    }

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