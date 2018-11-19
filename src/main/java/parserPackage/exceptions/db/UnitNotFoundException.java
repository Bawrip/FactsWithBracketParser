package parserPackage.exceptions.db;

public class UnitNotFoundException extends Exception {
    public UnitNotFoundException() {

    }

    @Override
    public String getMessage() {
        return "Unit not found.";
    }
}
