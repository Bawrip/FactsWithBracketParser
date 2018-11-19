package parserPackage.exceptions.txt;

public class ExpectedEndOfFileException extends Exception {
    private int line;

    public ExpectedEndOfFileException(int line) {
        this.line = line;
    }
    @Override
    public String getMessage() {
        return "Line " + line + " is incorrect. Expected end of file.";
    }
}
