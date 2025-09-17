import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;



class Deck {
    private int[] deck;     // stores the 28 cards
    private File deckFile;  // store deck file reference

    Deck(String deckFilePath) {
        this.deck = new int[28];
        this.deckFile = new File(deckFilePath);
        readDeckFile();
    }

    void readDeckFile() {
        if (!deckFile.exists()) {
            System.out.println("Error: deck file not found: " + deckFile.getName());
            return;
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
            System.out.println("Error reading deck file: " + deckFile.getName());
        }
    }

    
    void swap(int i, int j) {
        int temp = deck[i];
        deck[i] = deck[j];
        deck[j] = temp;
    }

    int findIndex(int value){ 
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
        int[] arr = getDeck();
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
            this.deck = newDeck;
        }
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
                return 0;
        }
    }

    int[] getDeck() {
        return deck;
    }

}