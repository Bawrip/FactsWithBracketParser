package parserPackage.exceptions;

public class ParserException extends Exception {
    private String message;

    public ParserException(String message) {
        this.message = message + ".";
    }

    public ParserException(String message, int ruleId) {
        this.message = message + " " + ruleId + ".";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
