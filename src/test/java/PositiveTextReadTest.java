import org.junit.jupiter.api.*;
import parserPackage.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PositiveTextReadTest {
    private String testTxtDir = "target\\test-classes\\txt\\";

    private static ByteArrayOutputStream buff;

    @BeforeAll
    public static void setOut() {
        buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));
    }

    @BeforeEach
    public void resetBuff() {
        buff.reset();
    }

    @AfterAll
    public static void removeOut(){
        System.setOut(System.out);
    }

    @Test
    public void noFilenameTest() {
        String args[] = {};

        Main.main(args);
        Assertions.assertEquals(UsageMessage.message, buff.toString());
    }

    @Test
    public void positive1() {
        String args[] = {"-t", testTxtDir + "positive1.txt"};

        Main.main(args);
        Assertions.assertEquals("A, B, C, D, F, M, T, Y\r\n", buff.toString());
    }


    @Test
    public void positive2() {
        String args[] = {"-t", testTxtDir + "positive2.txt"};

        Main.main(args);
        Assertions.assertEquals("Aa2, Al, E, KE, Y2, Y3, Z\r\n", buff.toString());
    }

    @Test
    public void positive3() {
        String args[] = {"-t", testTxtDir + "positive3.txt"};

        Main.main(args);
        Assertions.assertEquals("A, B, C, D, E, F, L, M, T, Y\r\n", buff.toString());
    }

    @Test
    public void positive4() {
        String args[] = {"-t", testTxtDir + "positive4.txt"};

        Main.main(args);
        Assertions.assertEquals("A, B, C, D, E, O, R, U, V\r\n", buff.toString());
    }
}
