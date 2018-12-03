import org.junit.Assert;
import org.junit.Test;

import parserPackage.Main;

import java.io.*;

public class TextFileTest {
    private String testTxtDir = "target\\test-classes\\txt\\";

    @Test
    public void noFilenameTest() {
        String args[] = {};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals(UsageMessage.message, buff.toString());
    }

    @Test
    public void positive1() {
        String args[] = {"-t", testTxtDir + "positive1.txt"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("A, B, C, D, F, M, T, Y\r\n", buff.toString());
    }


    @Test
    public void positive2() {
        String args[] = {"-t", testTxtDir + "positive2.txt"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Aa2, Al, E, KE, Y2, Y3, Z\r\n", buff.toString());
    }

    @Test
    public void positive3() {
        String args[] = {"-t", testTxtDir + "positive3.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("A, B, C, D, E, F, L, M, T, Y\r\n", buff.toString());
    }

    @Test
    public void positive4() {
        String args[] = {"-t", testTxtDir + "positive4.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("A, B, C, D, E, O, R, U, V\r\n", buff.toString());
    }

    @Test
    public void negative1() {
        String args[] = {"-t", testTxtDir + "negative1.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect symbol in line 1.\r\n", buff.toString());
    }

    @Test
    public void negative2() {
        String args[] = {"-t", testTxtDir + "negative2.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect rule in line 3.\r\n", buff.toString());
    }

    @Test
    public void negative3() {
        String args[] = {"-t", testTxtDir + "negative3.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect fact in line  6.\r\n", buff.toString());
    }

    @Test
    public void negative4() {
        String args[] = {"-t", testTxtDir + "negative4.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Expected end of file in line 8.\r\n", buff.toString());
    }

    @Test
    public void negative5() {
        String args[] = {"-t", testTxtDir + "negative5.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect fact in line 1.\r\n", buff.toString());
    }

    @Test
    public void negative6() {
        String args[] = {"-t", testTxtDir + "negative6.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect rule in line 1.\r\n", buff.toString());
    }

    @Test
    public void negative7() {
        String args[] = {"-t", testTxtDir + "negative7.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("File is empty.\r\n", buff.toString());
    }

    @Test
    public void negative8() {
        String args[] = {"-t", testTxtDir + "negative8.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect rule in line 2.\r\n", buff.toString());
    }

    @Test
    public void negative9() {
        String args[] = {"-t", testTxtDir + "negative9.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Expected line of facts 4.\r\n", buff.toString());
    }

    @Test
    public void negative10() {
        String args[] = {"-t", testTxtDir + "negative10.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect expression in line 1.\r\n", buff.toString());
    }

    @Test
    public void negative11() {
        String args[] = {"-t", testTxtDir + "negative11.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect expression in line 2.\r\n", buff.toString());
    }

    @Test
    public void negative12() {
        String args[] = {"-t", testTxtDir + "negative12.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Incorrect symbol in line 2.\r\n", buff.toString());
    }
}
