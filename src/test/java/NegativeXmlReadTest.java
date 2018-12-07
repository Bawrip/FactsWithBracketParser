import org.junit.jupiter.api.*;
import parserPackage.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class NegativeXmlReadTest {

    private String testXmlDir = "target\\test-classes\\xml\\";
    private static ByteArrayOutputStream buff = new ByteArrayOutputStream();


    @BeforeAll
    public static void setOut() {
        buff = new ByteArrayOutputStream();
        System.setErr(new PrintStream(buff));
    }

    @BeforeEach
    public void resetBuff() {
        buff.reset();
    }

    @AfterAll
    public static void removeOut(){
        System.setErr(System.out);
    }

    @Test
    public void negativeXml1() {
        String args[] = {"-x", testXmlDir + "negative1.xml"};

        Main.main(args);
        Assertions.assertEquals("cvc-complex-type.2.4.a: Invalid content was found starting with element 'KnownFacts'. One of '{\"http://www.amazanov.com/model/XMLschema\":Rules}' is expected.\r\n", buff.toString());
    }

    @Test
    public void negativeXml2() {
        String args[] = {"-x", testXmlDir + "negative2.xml"};

        Main.main(args);
        Assertions.assertEquals("cvc-complex-type.2.4.b: The content of element 'Rules' is not complete. One of '{\"http://www.amazanov.com/model/XMLschema\":Rule}' is expected.\r\n", buff.toString());
    }

    @Test
    public void negativeXml3() {
        String args[] = {"-x", testXmlDir + "negative3.xml"};

        Main.main(args);
        Assertions.assertEquals("cvc-complex-type.2.4.b: The content of element 'Rule' is not complete. One of '{\"http://www.amazanov.com/model/XMLschema\":ResultFact}' is expected.\r\n", buff.toString());
    }

    @Test
    public void negativeXml4() {
        String args[] = {"-x", testXmlDir + "negative4.xml"};

        Main.main(args);
        Assertions.assertEquals("cvc-complex-type.2.4.b: The content of element 'Or' is not complete. One of '{\"http://www.amazanov.com/model/XMLschema\":Fact, \"http://www.amazanov.com/model/XMLschema\":And, \"http://www.amazanov.com/model/XMLschema\":Or}' is expected.\r\n", buff.toString());
    }

    @Test
    public void negativeXml5() {
        String args[] = {"-x", testXmlDir + "negative5.xml"};

        Main.main(args);
        Assertions.assertEquals("cvc-complex-type.2.4.b: The content of element 'Model' is not complete. One of '{\"http://www.amazanov.com/model/XMLschema\":KnownFacts}' is expected.\r\n", buff.toString());
    }

    @Test
    public void negativeXml6() {
        String args[] = {"-x", testXmlDir + "negative6.xml"};

        Main.main(args);
        Assertions.assertEquals("cvc-complex-type.2.4.b: The content of element 'KnownFacts' is not complete. One of '{\"http://www.amazanov.com/model/XMLschema\":Fact}' is expected.\r\n", buff.toString());
    }

    @Test
    public void negativeXml7() {
        String args[] = {"-x", testXmlDir + "negative7.xml"};

        Main.main(args);
        Assertions.assertEquals("cvc-pattern-valid: Value '' is not facet-valid with respect to pattern '_*[a-zA-Z]+[a-zA-Z0-9_]*' for type 'FactPattern'.\r\n", buff.toString());
    }

    @Test
    public void negativeXml8() {
        String args[] = {"-x", testXmlDir + "negative8.xml"};

        Main.main(args);
        Assertions.assertEquals("cvc-complex-type.2.4.d: Invalid content was found starting with element 'And'. No child element is expected at this point.\r\n", buff.toString());
    }

    @Test
    public void negativeXml9() {
        String args[] = {"-x", testXmlDir + "negative9.xml"};

        Main.main(args);
        Assertions.assertEquals("cvc-complex-type.2.4.b: The content of element 'And' is not complete. One of '{\"http://www.amazanov.com/model/XMLschema\":Fact, \"http://www.amazanov.com/model/XMLschema\":And, \"http://www.amazanov.com/model/XMLschema\":Or}' is expected.\r\n", buff.toString());
    }
}
