package parserPackage.exceptions;

public class IncorrectLeftPartOfRuleException extends Exception {
    private int line;

    public IncorrectLeftPartOfRuleException(int line) {
        this.line = line;
    }

    public String getMessage() {
        return "Line " + line + " is incorrect. Invalid logical expression in rule.";
    }
}
