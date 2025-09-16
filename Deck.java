import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;


class Deck {
    private int[] deck;     // stores the 28 cards
    private File deckFile;  // store deck file reference

    // Constructor
    Deck(String deckFilePath) {
        this.deck = new int[28];
        this.deckFile = new File(deckFilePath);
        readDeckFile();
    }

    // Reads deck file and populates the deck array
    void readDeckFile() {
        if (!deckFile.exists()) {
            System.out.println("Error: deck file not found: " + deckFile.getName());
            return;
        }

        try (Scanner deckScanner = new Scanner(deckFile)) {
            int i = 0;
            while (deckScanner.hasNext() && i < 28) {
                String card = deckScanner.next();
                deck[i] = cardToNumber(card);  // assuming you wrote this method
                i++;
            }

            if (i < 28) {
                System.out.println("Error: deck file does not contain 28 cards");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error reading deck file: " + deckFile.getName());
        }
    }

    
    void swap(int i, int j) {
        int temp = deck[i];
        deck[i] = deck[j];
        deck[j] = temp;
    }

    int findIndex(int value){ //indexof
        for (int i = 0; i < deck.length; i++) {
            if (deck[i] == value) {
                return i;  
            }
        }
        return -1;
    }

    void swapSlice(int i, int j, int k, int l) {
        int n = 28;
        int[] newDeck = new int[n];
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

        if (l + 1 < n) {
            for (int m = l + 1; m < n; m++) {
                newDeck[index++] = deck[m];
            }
        }

        deck = newDeck;
    }


    void setDeck(int[] newDeck) {
        if (newDeck == null || newDeck.length != 28) {
            System.out.println("Deck must have exactly 28 cards.");
        }
        this.deck = newDeck;
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
                throw new IllegalArgumentException("Unknown card: " + card);
        }
    }

    int[] getDeck() {
        return deck;
    }

}