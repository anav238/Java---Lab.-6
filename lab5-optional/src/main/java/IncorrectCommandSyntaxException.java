public class IncorrectCommandSyntaxException extends Exception {
    public IncorrectCommandSyntaxException(String syntax) {
        super("Incorrect command syntax. Correct syntax: " + syntax);
    }
}
