
class Utils {

    static String numberToLetterString(int n) {
        if (n < 1 || n > 26) {
            throw new IllegalArgumentException("Number must be between 1 and 26");
        }
        return String.valueOf((char) ('A' + n - 1));
    }

    static int letterToNumber(char c) {
        c = Character.toUpperCase(c);
        if (c < 'A' || c > 'Z') {
            throw new IllegalArgumentException("Character must be A-Z");
        }
        return c - 'A' + 1;
    }
}
