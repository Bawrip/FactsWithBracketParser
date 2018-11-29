package parserPackage.dbTools;

public class DbRule {
    private int id;
    private DbExpression dbExpression;
    private String fact;

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public int getId() {
        return id;
    }

    public DbExpression getDbExpression() {
        return dbExpression;
    }

}
