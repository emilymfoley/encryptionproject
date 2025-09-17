/*+----------------------------------------------------------------------
 ||
 ||  Class Message
 ||
 ||         Author:  Emily Foley
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

/*---------------------------------------------------------------------
 |  Methods SET_MESSAGE, GET_CLEANED_MESSAGE, GET_NUMBERS
 |
 |  Purpose:  
 |      SETMESSAGE -- Initialize the Message object by reading a message
 |                      file, cleaning its contents (removing non-letter
 |                      characters and padding to multiples of 5), and
 |                      converting the cleaned text into numeric
 |                      representation.
 |      GETCLEANEDMESSAGE -- Retrieve the list of cleaned message strings.
 |      GETNUMBERS -- Retrieve the list of numeric arrays corresponding
 |                      to the cleaned messages.
 |
 |  Pre-condition:  
 |      SETMESSAGE -- messageFilePath points to a valid file containing
 |                      at least one line of text with letters.
 |      GETCLEANEDMESSAGE / GETNUMBERS -- The Message object has been
 |                      initialized by calling setMessage().
 |
 |  Post-condition:  
 |      SETMESSAGE -- The cleanedMessage and numericMessage fields of
 |                      the Message object are populated based on the
 |                      input file. 
 |      GETCLEANEDMESSAGE / GETNUMBERS -- No changes are made; data is
 |                      returned as-is.
 |
 |  Parameters:
 |      SETMESSAGE: messageFilePath -- path to the input message file.
 |      GETCLEANEDMESSAGE / GETNUMBERS: None
 |
 |  Returns:
 |      SETMESSAGE: None (void)
 |      GETCLEANEDMESSAGE: List<String> of cleaned message strings
 |      GETNUMBERS: List<int[]> of numeric message representations
 *-------------------------------------------------------------------*/
    void setMessage(String messageFilePath){
        List<String> rawMessage = readMsg(messageFilePath);
        this.cleanedMessage = cleanMessage(rawMessage);
        this.numericMessage = messageToNumbers(this.cleanedMessage);
    }

    List<String> getCleanedMessage() {
        return cleanedMessage;
    }

    List<int[]> getNumbers() {
        return numericMessage;
    }
/*---------------------------------------------------------------------
 |  Method READMSG
 |
 |  Purpose:  Read a text file containing messages and return a list of
 |            lines that contain at least one alphabetical character.
 |            Lines without letters are ignored. This method serves as
 |            the initial step in preparing messages for encryption or
 |            decryption by filtering out invalid content.
 |
 |  Pre-condition: messageFilePath points to a valid file location. The
 |                 file may contain multiple lines of text, some of
 |                 which may be empty or contain only non-letter
 |                 characters.
 |
 |  Post-condition: A list of valid message lines containing letters is
 |                  returned. The original file remains unchanged.
 |                  If the file does not exist, is empty, or contains
 |                  no letters, appropriate error messages are printed.
 |
 |  Parameters:
 |      messageFilePath -- the path to the text file containing messages
 |
 |  Returns:  A List<String> containing all lines from the file that
 |            include at least one letter. Returns null if the file
 |            does not exist or is empty.
 *-------------------------------------------------------------------*/
    private List<String> readMsg(String messageFilePath) {   
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
                if (lineHasLetters == false) {
                    continue;
                }
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
/*---------------------------------------------------------------------
 |  Method CLEANMESSAGE
 |
 |  Purpose:  Process a list of raw message strings by removing all
 |            non-alphabetical characters, converting lowercase letters
 |            to uppercase, and padding each message with 'X' characters
 |            so that its length is a multiple of 5. This prepares the
 |            messages for encryption.
 |
 |  Pre-condition: rawMessages is a non-null List of strings, which may
 |                 contain letters, digits, spaces, and other symbols.
 |
 |  Post-condition: Returns a List of cleaned message strings where
 |                  every string contains only uppercase letters and
 |                  its length is a multiple of 5. The input list
 |                  rawMessages remains unchanged.
 |
 |  Parameters:
 |      rawMessages -- String ArrayList containing the original, unprocessed
 |                     message lines.
 |
 |  Returns:  String ArrayList containing the cleaned and padded messages.
 *-------------------------------------------------------------------*/
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
/*---------------------------------------------------------------------
 |  Method MESSAGETONUMBERS
 |
 |  Purpose:  Convert a list of cleaned message strings into their
 |            numeric representations, where 'A' = 1, 'B' = 2, ..., 'Z' = 26.
 |            This prepares messages for the encryption process, which
 |            operates on numeric values.
 |
 |  Pre-condition: cleanedMessages is a non-null List of strings containing
 |                 only uppercase letters, with each string length being
 |                 a multiple of 5.
 |
 |  Post-condition: Returns a List of integer arrays representing the
 |                  numeric values of each character in the messages.
 |                  The input list cleanedMessages remains unchanged.
 |
 |  Parameters:
 |      cleanedMessages -- List<String> containing cleaned message strings.
 |
 |  Returns:  List<int[]> where each array corresponds to a message and
 |            contains numeric values of its letters.
 *-------------------------------------------------------------------*/
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
/*---------------------------------------------------------------------
 |  Method NUMBERTOLETTER
 |
 |  Purpose:  Convert a numeric value in the range 1–26 into its
 |            corresponding uppercase letter ('A' = 1, 'B' = 2, ..., 'Z' = 26).
 |            This is used during encryption and decryption to map numeric
 |            results back to letters.
 |
 |  Pre-condition: n is an integer between 1 and 26 inclusive.
 |
 |  Post-condition: Returns the corresponding uppercase letter as a string.
 |
 |  Parameters:
 |      n -- integer value representing a letter (1–26)
 |
 |  Returns:  String containing the uppercase letter corresponding to n.
 *-------------------------------------------------------------------*/
    String numberToLetter(int n) {
        return String.valueOf((char) ('A' + n - 1));
    }
}