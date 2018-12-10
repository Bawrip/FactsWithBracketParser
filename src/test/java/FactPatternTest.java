
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import parserPackage.parser.TxtParser;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class FactPatternTest {

    private static Pattern factPattern;

    @BeforeClass
    public static void getPattern() {
        try {
            TxtParser txtParser = new TxtParser();
            Field factPatternField = txtParser.getClass().getDeclaredField("FACT_PATTERN");
            factPatternField.setAccessible(true);
            factPattern = (Pattern) factPatternField.get(txtParser);


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void falseTest(){
        Assert.assertEquals(false, factPattern.matcher("_1").matches());
        Assert.assertEquals(false, factPattern.matcher("_123").matches());
        Assert.assertEquals(false, factPattern.matcher("___1").matches());
        Assert.assertEquals(false, factPattern.matcher("_1__1").matches());
        Assert.assertEquals(false, factPattern.matcher("_1DF").matches());
    }

    @Test
    public void trueTest(){
        Assert.assertEquals(true, factPattern.matcher("_a").matches());
        Assert.assertEquals(true, factPattern.matcher("a_1").matches());
        Assert.assertEquals(true, factPattern.matcher("___a").matches());
        Assert.assertEquals(true, factPattern.matcher("asd1_das_12asd").matches());
    }
}
