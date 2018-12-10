

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import parserPackage.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PositiveXmlReadTest {
    private String testXmlDir = "target\\test-classes\\xml\\";
    private static ByteArrayOutputStream buff = new ByteArrayOutputStream();


    @BeforeClass
    public static void setOut() {
        buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));
    }

    @Before
    public void resetBuff() {
        buff.reset();
    }

    @BeforeClass
    public static void removeOut(){
        System.setOut(System.out);
    }

    @Test
    public void testZero() {
        String args[] = {"-x"};

        Main.main(args);
        Assert.assertEquals(UsageMessage.message, buff.toString());
    }

    @Test
    public void testXml1() {
        String args[] = {"-x", testXmlDir + "positive1.xml"};

        Main.main(args);
        String expected = "A, B, C, D, F, M, T, Y\r\n";
        Assert.assertEquals(expected, buff.toString());
    }

    @Test
    public void testXml2() {
        String args[] = {"-x", testXmlDir + "positive2.xml"};

        Main.main(args);
        Assert.assertEquals("Aa2, Al, E, KE, Y2, Y3, Z\r\n", buff.toString());
    }

    @Test
    public void testXml3() {
        String args[] = {"-x", testXmlDir + "positive3.xml"};

        Main.main(args);
        Assert.assertEquals("A, B, C, D, E, F, L, M, T, Y\r\n", buff.toString());
    }

    @Test
    public void testXml4() {
        String args[] = {"-x", testXmlDir + "positive4.xml"};

        Main.main(args);
        Assert.assertEquals("A, B, C, D, E, O, R, U, V\r\n", buff.toString());
    }
}
