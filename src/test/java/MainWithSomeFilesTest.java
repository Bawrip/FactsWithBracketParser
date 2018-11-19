import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import parserPackage.Main;

import java.io.*;

public class MainWithSomeFilesTest {
    private String testResDir = "target\\test-classes\\UFText\\";


    @Test
    public void noFilenameTest() {
        String args[] = {};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("No filename to process. Usage: app <filename to process>.\r\n", buff.toString());
    }

    @Test
    public void emptyStringFilenameTest() {
        String args[] = {""};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("No filename to process. Usage: app <filename to process>.\r\n", buff.toString());
    }

    @Test
    public void positive1() {
        String args[] = {testResDir + "positive1.txt"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("A, B, C, D, F, M, T, Y\r\n", buff.toString());
    }


    @Test
    public void positive2() {
        String args[] = {testResDir + "positive2.txt"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("A\r\n", buff.toString());
    }

    @Test
    public void positive3() {
        String args[] = {testResDir + "positive3.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("A, B, C, D, E, F, L, M, T, Y\r\n", buff.toString());
    }

    @Test
    public void positive4() {
        String args[] = {testResDir + "positive4.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("R\r\n", buff.toString());
    }

    @Test
    public void negative1() {
        String args[] = {testResDir + "negative1.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Line 1 is incorrect. Invalid logical expression in rule.\r\n", buff.toString());
    }

    @Test
    public void negative2() {
        String args[] = {testResDir + "negative2.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Line 3 is incorrect. Expected rule.\r\n", buff.toString());
    }

    @Test
    public void negative3() {
        String args[] = {testResDir + "negative3.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Line 6 is incorrect. Expected facts.\r\n", buff.toString());
    }

    @Test
    public void negative4() {
        String args[] = {testResDir + "negative4.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Line 8 is incorrect. Expected end of file.\r\n", buff.toString());
    }

    @Test
    public void negative5() {
        String args[] = {testResDir + "negative5.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Line 1 is incorrect. Invalid fact in rule.\r\n", buff.toString());
    }

    @Test
    public void negative6() {
        String args[] = {testResDir + "negative6.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Line 1 is incorrect. Expected rule.\r\n", buff.toString());
    }

    @Test
    public void negative7() {
        String args[] = {testResDir + "negative7.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("File is empty.\r\n", buff.toString());
    }

    @Test
    public void negative8() {
        String args[] = {testResDir + "negative8.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Line 2 is incorrect. Expected rule.\r\n", buff.toString());
    }

    @Test
    public void negative9() {
        String args[] = {testResDir + "negative9.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Line 4 is incorrect. Expected facts.\r\n", buff.toString());
    }

    @Test
    public void negative10() {
        String args[] = {testResDir + "negative10.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Line 1 is incorrect. Invalid logical expression in rule.\r\n", buff.toString());
    }

    @Test
    public void negative11() {
        String args[] = {testResDir + "negative11.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Line 2 is incorrect. Invalid logical expression in rule.\r\n", buff.toString());
    }

    @Test
    public void negative12() {
        String args[] = {testResDir + "negative12.txt"};
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Line 2 is incorrect. Invalid logical expression in rule.\r\n", buff.toString());
    }
}
