import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/*=====================================================================
 |  Class Message
 |
 |  Author:  Emily Foley
 |
 |  Dependencies:  
 |      - java.util.Scanner
 |      - java.util.Arrays
 |      - java.io.File
 |      - java.io.FileNotFoundException
 |      - java.util.List, java.util.ArrayList
 |      - java.io.FileWriter, java.io.PrintWriter, java.io.IOException
 |
 |  Packages: Member of "encryption" package
 |
 |  Inheritance:  None 
 |
 |  Purpose:  
 |      The Message class represents individual messages to be
 |      encrypted or decrypted by the Solitaire/Keystream algorithm.
 |      Each message is stored both in a "cleaned" string format
 |      (uppercase, letters only, padded to multiple of 5) and as a
 |      numeric array representation (A=1, ..., Z=26). 
 |      This class provides methods to read messages from
 |      an input file, remove non alphabetic characters, and convert 
 |      them into numeric form.
 |
 |  Private Variables:
 |      - String cleanedMessage
 |            The processed message string containing only uppercase A–Z
 |            letters, padded with 'X' so its length is a multiple of 5.
 |
 |      - int[] numericMessage
 |            The numeric representation of cleanedMessage, where
 |            A=1, ..., Z=26.
 |
 |  Constructors:
 |      - Message(String messageFilePath)
 |            Private constructor that initializes a Message object by
 |            cleaning the input string and converting it into a numeric
 |            array.
 |
 |  Package-Private Class Methods:
 |      - static List<Message> readMsg(String messageFilePath)
 |            Reads a file of raw messages, and returns
 |            a list of Message objects (one per valid line).
 |
 |  Package-Private Instance Methods:
 |      - String getCleanedMessage()
 |            Returns the cleaned, uppercase, padded message string.
 |
 |      - int[] getNumbers()
 |            Returns the numeric representation of the cleaned message,
 |            where A=1, ..., Z=26.
 |
 |  Private Instance Methods:
 |      - String cleanMessage(String inputMessage)
 |            Removes non-letters, converts to uppercase, and pads to
 |            multiple of 5 with 'X'.
 |
 |      - int[] messageToNumbers(String cleanedMessage)
 |            Converts the cleaned string into its numeric equivalent
 |            representation.
 |
 *====================================================================*/

class Message {
    private int[] numericMessage;   // Field declaration of numeric representation of a message
    private String cleanedMessage;  // Field declaration of the message after non-alphabetical characters are removed

    Message(String messageFilePath) { //messageFilePath: the file path of the message file
        this.cleanedMessage = cleanMessage(messageFilePath);
        this.numericMessage = messageToNumbers(this.cleanedMessage);
    }

    /*---------------------------------------------------------------------
     |  Method READ_MSG
     |
     |  Purpose:  Read a text file containing one or more messages, line by
     |            line, and create a list of Message objects from those
     |            lines. Only lines containing at least one alphabetic
     |            character are considered valid messages. The method
     |            ensures the file exists, is not empty, and contains at
     |            least one valid message.
     |
     |  Pre-condition:  The parameter messageFilePath must be a valid path
     |                  string (absolute or relative). The file should be a
     |                  plain text file potentially containing alphabetic
     |                  characters.
     |
     |  Post-condition: If the file exists and contains valid lines with
     |                  letters, a list of Message objects corresponding
     |                  to those lines will be created and returned. If
     |                  the file does not exist, is empty, or contains no
     |                  letters, error messages will be displayed and an
     |                  empty list will be returned.
     |
     |  Parameters:
     |      messageFilePath -- a string representing the path to the text
     |                         file containing messages.
     |
     |  Returns:  A List<Message> object. Each element in the list is a
     |            Message created from a line in the file containing at
     |            least one letter. The list may be empty if no valid lines
     |            are found or if the file is invalid.
     *-------------------------------------------------------------------*/
    static List<Message> readMsg(String messageFilePath) {   
        File file = new File(messageFilePath); //object representing the file containing the messages
        List<Message> messages = new ArrayList<>(); //Array list of messages read in from input file, before cleaning

        if (!file.exists()) { 
            System.out.println("Error: message file not found: " + messageFilePath);
            return messages;
        }

        if (file.length() == 0) {
            System.out.println("Error: message file is empty: " + messageFilePath);
            return messages;
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
                messages.add(new Message(line));
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
     |  Method CLEAN_MESSAGE
     |
     |  Purpose:  Normalize an input message string by stripping out all
     |            non-alphabetic characters, converting any lowercase
     |            letters to uppercase, and padding the result with 'X'
     |            characters so that the total length is a multiple of 5.
     |            This formatting ensures a consistent, cleaned message
     |            ready for encryption.
     |
     |  Pre-condition:  inputMessage may be null or a string containing
     |                  arbitrary characters. It may contain lowercase
     |                  letters, uppercase letters, spaces, punctuation,
     |                  or other non-letter symbols.
     |
     |  Post-condition: The returned string contains only uppercase A–Z
     |                  letters. All lowercase letters have been converted
     |                  to uppercase, all non-letters have been discarded,
     |                  and the message has been padded with 'X' characters
     |                  (if necessary) so that its length is divisible by 5.
     |                  The original inputMessage is unchanged.
     |
     |  Parameters:
     |      inputMessage -- the raw input string list potentially containing
     |                      letters and non-letter characters.
     |
     |  Returns:  A string containing the cleaned and padded message,
     |            consisting only of uppercase letters with a length that
     |            is a multiple of 5.
     *-------------------------------------------------------------------*/
    private String cleanMessage(String inputMessage) {

        if (inputMessage == null) return "";

        StringBuilder cleanedMsg = new StringBuilder(); //initialize a stringBuilder object to be filled with cleaned messages

        for (int i = 0; i < inputMessage.length(); i++) {
            char c = inputMessage.charAt(i);

            if (c >= 'a' && c <= 'z') {
                c = (char)(c - 'a' + 'A');
            }

            if (c >= 'A' && c <= 'Z') {
                cleanedMsg.append(c);
            }
        }

        int remainder = cleanedMsg.length() % 5; //number of X's required for the message length to be a multiple of 5
        if (remainder != 0) {
            int padding = 5 - remainder;
            for (int i = 0; i < padding; i++) {
                cleanedMsg.append('X');
            }
        }

        return cleanedMsg.toString();
    }

    /*---------------------------------------------------------------------
     |  Method MESSAGE_TO_NUMBERS
     |
     |  Purpose:  Convert a cleaned message string (containing only
     |            uppercase letters A–Z) into an integer array where
     |            each letter is mapped to its alphabetical position:
     |            A = 1, B = 2, ..., Z = 26.
     |
     |  Pre-condition:  cleanedMessage is a non-null string that has already
     |                  been normalized to contain only uppercase A–Z
     |                  letters (e.g., produced by cleanMessage()).
     |
     |  Post-condition: The original cleanedMessage string is unchanged.
     |                  A new integer array has been created and populated
     |                  with numeric equivalents of each character.
     |
     |  Parameters:
     |      cleanedMessage -- the normalized input string consisting only
     |                         of uppercase A–Z letters.
     |
     |  Returns:  An integer array of the same length as cleanedMessage,
     |            where each entry corresponds to the alphabetical value
     |            of the letter at the same position in cleanedMessage.
     *-------------------------------------------------------------------*/
    int[] messageToNumbers(String cleanedMessage) {
        int[] numericMsg = new int[cleanedMessage.length()]; //initializing ragged array to contain numeric representation of messages
        for (int i = 0; i < cleanedMessage.length(); i++) {
            numericMsg[i] = cleanedMessage.charAt(i) - 'A' + 1;
        }
        return numericMsg;
    }

    /*---------------------------------------------------------------------
     |  Methods GET_CLEANED_MESSAGE and GET_NUMBERS
     |
     |  Purpose:  Provide external access to the private fields of the
     |            Message class. GET_CLEANED_MESSAGE returns the processed
     |            version of the original input message (only uppercase
     |            A–Z characters, padded to a multiple of 5). 
     |            GET_NUMBERS returns the numeric array representation of
     |            that cleaned message, where A = 1, ..., Z = 26.
     |
     |  Pre-condition: The Message object has already been constructed and
     |                 its fields (cleanedMessage and numericMessage) have
     |                 been initialized by earlier processing.
     |
     |  Post-condition: The state of the Message object remains unchanged.
     |                  Only read-only access to the internal data is
     |                  provided.
     |
     |  Parameters: None
     |
     |  Returns:
     |      GET_CLEANED_MESSAGE -- the cleaned and padded uppercase string
     |                             representation of the original message.
     |      GET_NUMBERS         -- an integer array mapping the cleaned
     |                             message to values in the range 1–26.
     *-------------------------------------------------------------------*/
    String getCleanedMessage() {
        return cleanedMessage;
    }

    int[] getNumbers() {
        return numericMessage;
    }
}
