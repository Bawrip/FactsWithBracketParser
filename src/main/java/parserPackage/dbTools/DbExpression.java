package parserPackage.dbTools;

import parserPackage.ExpressionTypes;

public class DbExpression {
    private int expId;
    private ExpressionTypes type;
    private String fact;

    public int getExpId() {
        return expId;
    }

    public ExpressionTypes getType() {
        return type;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

}
