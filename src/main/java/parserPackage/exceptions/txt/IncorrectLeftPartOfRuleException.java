package parserPackage.exceptions.txt;

public class IncorrectLeftPartOfRuleException extends Exception {
    private int line;

    public IncorrectLeftPartOfRuleException(int line) {
        this.line = line;
    }

    @Override
    public String getMessage() {
        return "Line " + line + " is incorrect. Invalid logical expression in rule.";
    }
}
