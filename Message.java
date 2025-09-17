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

    int[] messageToNumbers(String cleanedMessage) {
        int[] numericMsg = new int[cleanedMessage.length()]; //initializing ragged array to contain numeric representation of messages
        for (int i = 0; i < cleanedMessage.length(); i++) {
            numericMsg[i] = cleanedMessage.charAt(i) - 'A' + 1;
        }
        return numericMsg;
    }

    String getCleanedMessage() {
        return cleanedMessage;
    }

    int[] getNumbers() {
        return numericMessage;
    }
}