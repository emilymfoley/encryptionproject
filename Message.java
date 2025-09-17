/*+----------------------------------------------------------------------
 ||
 ||  Class Message
 ||
 ||         Author:  Emily Margaret Foley
 ||
 ||        Purpose:  This class represents messages to be encrypted or
 ||                   decrypted. It is responsible for reading raw input
 ||                   from a file, cleaning the message by removing
 ||                   non-alphabetical characters and padding with 'X'
 ||                   as necessary, and converting letters to numeric
 ||                   representations suitable for processing by the
 ||                   Solitaire encryption algorithm.
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
 ||   Constructors:  Message() – Default constructor that initializes
 ||                                empty data structures for storing
 ||                                cleaned and numeric messages.
 ||
 ||  Class Methods:  static List<String> readMsg(String messageFilePath)
 ||                        – Reads raw text messages from the specified
 ||                          file. Returns them as a list of strings,
 ||                          preserving line separation.
 ||
 ||  Inst. Methods:  void setMessage(String messageFilePath)
 ||                        – Reads, cleans, and converts the message from
 ||                          the file into numeric representation.
 ||
 ||                   private List<String> cleanMessage(List<String> rawMessages)
 ||                        – Helper method, removes all non-alphabetical characters,
 ||                          converts lowercase to uppercase, and pads
 ||                          the message to a multiple of 5 letters.
 ||
 ||                   private List<int[]> messageToNumbers(List<String> cleanedMessages)
 ||                        – Helper method, converts cleaned string messages into numeric
 ||                          arrays (A=1, B=2, …, Z=26).
 ||
 ||                   String numberToLetter(int n)
 ||                        – Converts a numeric value back to its
 ||                          corresponding letter.
 ||
 ||                   List<String> getCleanedMessage()
 ||                        – Returns the cleaned messages as strings.
 ||
 ||                   List<int[]> getNumbers()
 ||                        – Returns the numeric representations of the
 ||                          cleaned messages.
 ||
 ++-----------------------------------------------------------------------*/
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

class Message {
    private List<int[]> numericMessage;   // Field declaration of numeric representation of a message
    private List<String> cleanedMessage;  // Field declaration of the message after non-alphabetical characters are removed

    Message() {
        this.numericMessage = new ArrayList<>();
        this.cleanedMessage = new ArrayList<>();
    }

    void setMessage(String messageFilePath){
        List<String> rawMessage = readMsg(messageFilePath);
        this.cleanedMessage = cleanMessage(rawMessage);
        this.numericMessage = messageToNumbers(this.cleanedMessage);
    }

    static List<String> readMsg(String messageFilePath) {   
        File file = new File(messageFilePath); //object representing the file containing the messages
        List<String> messages = new ArrayList<>(); //Array list of messages read in from input file, before cleaning

        if (!file.exists()) { 
            System.out.println("Error: message file not found: " + messageFilePath);
            return null;
        }

        if (file.length() == 0) {
            System.out.println("Error: message file is empty: " + messageFilePath);
            return null;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            boolean fileHasLetters = false; //boolean to check if the message file contains any letters

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine(); //object representing a single line in the message file

                
                boolean lineHasLetters = false; //boolean to check if a single line has letters
                for (char c : line.toCharArray()) { 
                    if (Character.isLetter(c)) {
                        lineHasLetters = true; 
                        break;
                    }
                }
                if (lineHasLetters == false) continue;

                fileHasLetters = true;
                messages.add(new String(line));
            }

            if (!fileHasLetters) { 
                System.out.println("Error: message file does not contain any letters: " + file.getName());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error reading file: " + file.getName());
        }

        return messages;
    }

    private List<String> cleanMessage(List<String> rawMessages) {
        List<String> cleanedMessages = new ArrayList<>();

        for (String inputMessage : rawMessages) {
            if (inputMessage == null) continue;

            StringBuilder cleanedMsg = new StringBuilder();

            for (int i = 0; i < inputMessage.length(); i++) {
                char c = inputMessage.charAt(i);

                if (c >= 'a' && c <= 'z') {
                    c = (char) (c - 'a' + 'A');
                }

                if (c >= 'A' && c <= 'Z') {
                    cleanedMsg.append(c);
                }
            }

            int remainder = cleanedMsg.length() % 5;
            if (remainder != 0) {
                int padding = 5 - remainder;
                for (int i = 0; i < padding; i++) {
                    cleanedMsg.append('X');
                }
            }

            cleanedMessages.add(cleanedMsg.toString());
        }

        return cleanedMessages;
    }

    private List<int[]> messageToNumbers(List<String> cleanedMessages) {
        List<int[]> numericMessages = new ArrayList<>();

        for (String cleanedMessage : cleanedMessages) {
            int[] numericMsg = new int[cleanedMessage.length()];
            for (int i = 0; i < cleanedMessage.length(); i++) {
                numericMsg[i] = cleanedMessage.charAt(i) - 'A' + 1;
            }
            numericMessages.add(numericMsg);
        }

        return numericMessages;
    }

    String numberToLetter(int n) {
        return String.valueOf((char) ('A' + n - 1));
    }

    List<String> getCleanedMessage() {
        return cleanedMessage;
    }

    List<int[]> getNumbers() {
        return numericMessage;
    }
}