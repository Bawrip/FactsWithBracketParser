import org.junit.Assert;
import org.junit.Test;

import parserPackage.Main;

import java.io.*;

public class TextFileTest {
    private String testResDir = "target\\test-classes\\Txt\\";
    private String usageMessage = "usage: App [-d <propertyPath> | -i <textPath propertyPath> | -t <textPath>]\r\n" +
            " -d,--database <propertyPath>          Argument is a path of property file, which contains url, login, password for\r\n" +
            "                                       connecting to the database.Reads the database. Extracts the facts from the rules\r\n" +
            "                                       and deduce them.\r\n" +
            " -i,--insert <textPath propertyPath>   First argument is a path of text file, second is a path of property file. Reads\r\n" +
            "                                       text into a database. Reads the database\r\n" +
            " -t,--text <textPath>                  Argument is a path of text file, which contains rules and facts. Extracts the\r\n" +
            "                                       facts from the rules and deduce them.\r\n";

    @Test
    public void noFilenameTest() {
        String args[] = {};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals(usageMessage, buff.toString());
    }

    @Test
    public void positive1() {
        String args[] = {"-t", testResDir + "positive1.txt"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("A, B, C, D, F, M, T, Y\r\n", buff.toString());
    }


    @Test
    public void positive2() {
        String args[] = {"-t", testResDir + "positive2.txt"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Aa2, Al, E, KE, Y2, Y3, Z\r\n", buff.toString());
    }

    @Test
    public void positive3() {
        String args[] = {"-t", testResDir + "positive3.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("A, B, C, D, E, F, L, M, T, Y\r\n", buff.toString());
    }

    @Test
    public void positive4() {
        String args[] = {"-t", testResDir + "positive4.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("A, B, C, D, E, O, R, U, V\r\n", buff.toString());
    }

    @Test
    public void negative1() {
        String args[] = {"-t", testResDir + "negative1.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect symbol in line 1.\r\n", buff.toString());
    }

    @Test
    public void negative2() {
        String args[] = {"-t", testResDir + "negative2.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect rule in line 3.\r\n", buff.toString());
    }

    @Test
    public void negative3() {
        String args[] = {"-t", testResDir + "negative3.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect fact in line  6.\r\n", buff.toString());
    }

    @Test
    public void negative4() {
        String args[] = {"-t", testResDir + "negative4.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Expected end of file in line 8.\r\n", buff.toString());
    }

    @Test
    public void negative5() {
        String args[] = {"-t", testResDir + "negative5.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect fact in line 1.\r\n", buff.toString());
    }

    @Test
    public void negative6() {
        String args[] = {"-t", testResDir + "negative6.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect rule in line 1.\r\n", buff.toString());
    }

    @Test
    public void negative7() {
        String args[] = {"-t", testResDir + "negative7.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("File is empty.\r\n", buff.toString());
    }

    @Test
    public void negative8() {
        String args[] = {"-t", testResDir + "negative8.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect rule in line 2.\r\n", buff.toString());
    }

    @Test
    public void negative9() {
        String args[] = {"-t", testResDir + "negative9.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Expected line of facts 4.\r\n", buff.toString());
    }

    @Test
    public void negative10() {
        String args[] = {"-t", testResDir + "negative10.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect expression in line 1.\r\n", buff.toString());
    }

    @Test
    public void negative11() {
        String args[] = {"-t", testResDir + "negative11.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect expression in line 2.\r\n", buff.toString());
    }

    @Test
    public void negative12() {
        String args[] = {"-t", testResDir + "negative12.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect symbol in line 2.\r\n", buff.toString());
    }
}
