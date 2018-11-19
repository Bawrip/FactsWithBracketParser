package parserPackage.exceptions.db;

public class IncorrectLeftPartOfRuleException extends Exception {
    private int rule;

    public IncorrectLeftPartOfRuleException(int rule) {
        this.rule = rule;
    }

    @Override
    public String getMessage() {
        return "Invalid logical expression in rule " + rule + ".";
    }
}
