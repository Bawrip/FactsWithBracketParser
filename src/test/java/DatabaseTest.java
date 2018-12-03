import org.junit.Assert;
import org.junit.Test;
import parserPackage.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class DatabaseTest {
    private String testPropDir = "target\\test-classes\\properties\\";

    @Test
    public void testZero() {
        String args[] = {"-d"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals(UsageMessage.message, buff.toString());
    }

    @Test
    public void testDb1() {
        String args[] = {"-d", testPropDir + "dbTest1.properties"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("A, B, C, D, F, M, T, Y\r\n", buff.toString());
    }

    @Test
    public void testDb2() {
        String args[] = {"-d", testPropDir + "dbTest2.properties"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("Aa2, Al, E, KE, Y2, Y3, Z\r\n", buff.toString());
    }

    @Test
    public void testDb3() {
        String args[] = {"-d", testPropDir + "dbTest3.properties"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("A, B, C, D, E, F, L, M, T, Y\r\n", buff.toString());
    }

    @Test
    public void testDb4() {
        String args[] = {"-d", testPropDir + "dbTest4.properties"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);
        Assert.assertEquals("A, B, C, D, E, O, R, U, V\r\n", buff.toString());
    }
}
