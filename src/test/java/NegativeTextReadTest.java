
import org.junit.*;
import parserPackage.Main;

import java.io.*;

public class NegativeTextReadTest {
    private String testTxtDir = "target\\test-classes\\txt\\";

    private static ByteArrayOutputStream buff;

    @BeforeClass
    public static void setOut() {
        buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));
    }

    @Before
    public void resetBuff() {
        buff.reset();
    }

    @AfterClass
    public static void removeOut(){
        System.setErr(System.err);
    }

    @Test
    public void negative1() {
        String args[] = {"-t", testTxtDir + "negative1.txt"};

        Main.main(args);
        Assert.assertEquals("Incorrect symbol in line 1.\r\n", buff.toString());
    }

    @Test
    public void negative2() {
        String args[] = {"-t", testTxtDir + "negative2.txt"};

        Main.main(args);
        Assert.assertEquals("Incorrect rule in line 3.\r\n", buff.toString());
    }

    @Test
    public void negative3() {
        String args[] = {"-t", testTxtDir + "negative3.txt"};

        Main.main(args);
        Assert.assertEquals("Incorrect fact in line  6.\r\n", buff.toString());
    }

    @Test
    public void negative4() {
        String args[] = {"-t", testTxtDir + "negative4.txt"};

        Main.main(args);
        Assert.assertEquals("Expected end of file in line 8.\r\n", buff.toString());
    }

    @Test
    public void negative5() {
        String args[] = {"-t", testTxtDir + "negative5.txt"};

        Main.main(args);
        Assert.assertEquals("Incorrect fact in line 1.\r\n", buff.toString());
    }

    @Test
    public void negative6() {
        String args[] = {"-t", testTxtDir + "negative6.txt"};

        Main.main(args);
        Assert.assertEquals("Incorrect rule in line 1.\r\n", buff.toString());
    }

    @Test
    public void negative7() {
        String args[] = {"-t", testTxtDir + "negative7.txt"};

        Main.main(args);
        Assert.assertEquals("File is empty.\r\n", buff.toString());
    }

    @Test
    public void negative8() {
        String args[] = {"-t", testTxtDir + "negative8.txt"};

        Main.main(args);
        Assert.assertEquals("Incorrect rule in line 2.\r\n", buff.toString());
    }

    @Test
    public void negative9() {
        String args[] = {"-t", testTxtDir + "negative9.txt"};

        Main.main(args);
        Assert.assertEquals("Expected line of facts 4.\r\n", buff.toString());
    }

    @Test
    public void negative10() {
        String args[] = {"-t", testTxtDir + "negative10.txt"};

        Main.main(args);
        Assert.assertEquals("Incorrect expression in line 1.\r\n", buff.toString());
    }

    @Test
    public void negative11() {
        String args[] = {"-t", testTxtDir + "negative11.txt"};

        Main.main(args);
        Assert.assertEquals("Incorrect expression in line 2.\r\n", buff.toString());
    }

    @Test
    public void negative12() {
        String args[] = {"-t", testTxtDir + "negative12.txt"};

        Main.main(args);
        Assert.assertEquals("Incorrect symbol in line 2.\r\n", buff.toString());
    }
}
