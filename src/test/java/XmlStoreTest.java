import org.junit.jupiter.api.*;
import parserPackage.Main;
import parserPackage.factTools.*;
import parserPackage.parser.TxtParser;
import parserPackage.parser.XmlParser;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class XmlStoreTest {
    private String testTxtDir = "target\\test-classes\\txt\\";
    private String testXmlDir = "target\\test-classes\\xml\\";

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
    public void testOneStore() {
        String txtPath = testTxtDir + "positive1.txt";
        String xmlPath = testXmlDir + "testXml1.xml";

        String iArgs[] = {"-tx", txtPath, xmlPath};
        Main.main(iArgs);

        Assertions.assertEquals("Facts and Rules stored successfully.\r\n", buff.toString());
        TxtParser txtParser = new TxtParser();
        XmlParser xmlParser = new XmlParser();

        Model txtModel;
        Model xmlModel;

        try {
            txtModel = txtParser.parse(txtPath);
            txtModel.processRules();

            xmlModel = xmlParser.parse(xmlPath);
            xmlModel.processRules();


            Assertions.assertEquals(txtModel.getFacts(), xmlModel.getFacts());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void testMultiStore() {
        String xmlPath = testXmlDir + "testXml.xml";

        Main.main(new String[] {"-tx", testTxtDir + "positive1.txt", xmlPath});
        Main.main(new String[] {"-tx", testTxtDir + "positive2.txt", xmlPath});
        Main.main(new String[] {"-tx", testTxtDir + "positive3.txt", xmlPath});

        TxtParser txtParser = new TxtParser();
        XmlParser xmlParser = new XmlParser();

        Model txtModel;
        Model xmlModel;

        try {
            txtModel = txtParser.parse(testTxtDir + "positive3.txt");
            txtModel.processRules();

            xmlModel = xmlParser.parse(xmlPath);
            xmlModel.processRules();


            Assertions.assertEquals(txtModel.getFacts(), xmlModel.getFacts());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void testStore2() {
        String args[] = {"-tx", "sometext"};

        Main.main(args);

        Assertions.assertEquals(UsageMessage.message, buff.toString());
    }

    @Test
    public void testStore3() {
        String args[] = {"-tx"};

        Main.main(args);

        Assertions.assertEquals(UsageMessage.message, buff.toString());
    }
}
