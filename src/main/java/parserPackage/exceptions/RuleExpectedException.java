package parserPackage.exceptions;

public class RuleExpectedException extends Exception{
    private int line;

    public RuleExpectedException(int line) {
        this.line = line;
    }
    @Override
    public String getMessage() {
        return "Line " + line + " is incorrect. Expected rule.";
    }
}
