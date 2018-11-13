package parserPackage.dbTools;

public class Expression {
    private int id;
    private String exp;
    private String fact;
    private Integer unitId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getMessage() {
        return id + " " + exp + " " + fact + " " + unitId;
    }
}
