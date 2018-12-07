import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import parserPackage.parser.TxtParser;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class FactPatternTest {

    private static Pattern factPattern;

    @BeforeAll
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
        Assertions.assertEquals(false, factPattern.matcher("_1").matches());
        Assertions.assertEquals(false, factPattern.matcher("_123").matches());
        Assertions.assertEquals(false, factPattern.matcher("___1").matches());
        Assertions.assertEquals(false, factPattern.matcher("_1__1").matches());
        Assertions.assertEquals(false, factPattern.matcher("_1DF").matches());
    }

    @Test
    public void trueTest(){
        Assertions.assertEquals(true, factPattern.matcher("_a").matches());
        Assertions.assertEquals(true, factPattern.matcher("a_1").matches());
        Assertions.assertEquals(true, factPattern.matcher("___a").matches());
        Assertions.assertEquals(true, factPattern.matcher("asd1_das_12asd").matches());
    }
}
