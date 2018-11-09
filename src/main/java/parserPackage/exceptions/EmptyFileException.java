package parserPackage.exceptions;

public class EmptyFileException extends Exception {
    @Override
    public String getMessage() {
        return "File is empty.";
    }
}
