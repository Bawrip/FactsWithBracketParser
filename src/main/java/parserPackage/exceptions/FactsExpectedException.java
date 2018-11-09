package parserPackage.exceptions;

public class FactsExpectedException extends Exception {
    private int line;

    public FactsExpectedException(int line) {
        this.line = line;
    }
    @Override
    public String getMessage() {
        return "Line " + line + " is incorrect. Expected facts.";
    }
}
