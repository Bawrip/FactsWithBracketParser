package parserPackage.exceptions.db;

public class IncorrectRightPartOfRuleException extends Exception {
    private int rule;

    public IncorrectRightPartOfRuleException(int rule) {
        this.rule = rule;
    }

    @Override
    public String getMessage() {
        return "Invalid fact in rule " + rule + ".";
    }
}
