package parserPackage.exceptions.txt;

public class EmptyFileException extends Exception {
    public EmptyFileException() {

    }

    @Override
    public String getMessage() {
        return "File is empty.";
    }
}
