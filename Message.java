import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;


class Message {
    private int[] numericMessage;   // Field declaration of numeric representation of a message
    private String cleanedMessage;  // Field declaration of the message after non-alphabetical characters are removed

    Message(String messageFilePath) { //messageFilePath: the file path of the message file
        this.cleanedMessage = cleanMessage(messageFilePath);
        this.numericMessage = messageToNumbers(this.cleanedMessage);
    }

    static List<Message> readMsg(String messageFilePath) {   
        File file = new File(messageFilePath); //object representing the file containing the messages
        List<Message> messages = new ArrayList<>(); //Array list of messages read in from input file, before cleaning

        boolean fileExists = file.exists();
        if (fileExists == false) { 
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
<<<<<<< HEAD
                if (lineHasLetters == false) continue;

=======
                if (lineHasLetters == false) {
                    continue; 
                }

                
>>>>>>> 0e8151f055be8788a9ab98f463273d5e005536a8
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

    private String cleanMessage(String inputMessage) {

        if (inputMessage == null) return "";

        StringBuilder cleanedMsg = new StringBuilder(); //initialize a stringBuilder object to be filled with cleaned messages

        // Keep only A–Z letters, normalize to uppercase
        for (int i = 0; i < inputMessage.length(); i++) {
            char c = inputMessage.charAt(i);

            // Convert lowercase → uppercase
            if (c >= 'a' && c <= 'z') {
                c = (char)(c - 'a' + 'A');
            }

            // Append only uppercase letters
            if (c >= 'A' && c <= 'Z') {
                cleanedMsg.append(c);
            }
        }

        // Pad to multiple of 5
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
