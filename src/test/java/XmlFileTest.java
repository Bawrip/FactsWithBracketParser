import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import parserPackage.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class XmlFileTest {
    private String testXmlDir = "target\\test-classes\\xml\\";

    @Test
    public void testZero() {
        String args[] = {"-x"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals(UsageMessage.message, buff.toString());
    }

    @Test
    public void testXml1() {
        String args[] = {"-x", testXmlDir + "testXml1.xml"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        //byte[] expected = {'A', ',', ' ', 'B', ',', ' ', 'C', ',', ' ', 'D', ',', ' ', 'F', ',', ' ', 'M', ',', ' ', 'T', ',', ' ', 'Y', '\r', '\n'};
        String expected = "A, B, C, D, F, M, T, Y\r\n";
        Assert.assertEquals(expected, buff.toString());
    }

    @Test
    public void testXml2() {
        String args[] = {"-x", testXmlDir + "testXml2.xml"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Aa2, Al, E, KE, Y2, Y3, Z\r\n", buff.toString());
    }

    @Test
    public void testXml3() {
        String args[] = {"-x", testXmlDir + "testXml3.xml"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("A, B, C, D, E, F, L, M, T, Y\r\n", buff.toString());
    }

    @Test
    public void testXml4() {
        String args[] = {"-x", testXmlDir + "testXml4.xml"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("A, B, C, D, E, O, R, U, V\r\n", buff.toString());
    }
}
