package parserPackage.exceptions;

public class IncorrectRightPartOfRuleException extends Exception {
    private int line;

    public IncorrectRightPartOfRuleException(int line) {
        this.line = line;
    }

    public String getMessage() {
        return "Line " + line + ". Invalid fact in rule.";
    }
}
