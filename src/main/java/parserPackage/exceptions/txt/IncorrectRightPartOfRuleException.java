package parserPackage.exceptions.txt;

public class IncorrectRightPartOfRuleException extends Exception {
    private int line;

    public IncorrectRightPartOfRuleException(int line) {
        this.line = line;
    }

    @Override
    public String getMessage() {
        return "Line " + line + " is incorrect. " + "Invalid fact in rule.";
    }
}
