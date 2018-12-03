import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import parserPackage.Main;
import parserPackage.factTools.*;
import parserPackage.parser.DbParser;
import parserPackage.parser.TxtParser;
import parserPackage.parser.XmlParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

public class XmlStoreTest {
    private String testTxtDir = "target\\test-classes\\txt\\";
    private String testXmlDir = "target\\test-classes\\xml\\";

    @Test
    public void testOneStore() {
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        String txtPath = testTxtDir + "positive1.txt";
        String xmlPath = testXmlDir + "testXml1.xml";

        String iArgs[] = {"-tx", txtPath, xmlPath};
        Main.main(iArgs);

        Assert.assertEquals("Facts and Rules stored successfully.\r\n", buff.toString());
        TxtParser txtParser = new TxtParser();
        XmlParser xmlParser = new XmlParser();

        Model txtModel;
        Model xmlModel;

        try {
            txtModel = txtParser.parse(txtPath);
            txtModel.processRules();

            xmlModel = xmlParser.parse(xmlPath);
            xmlModel.processRules();


            Assert.assertEquals(txtModel.getFacts(), xmlModel.getFacts());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void testMultiStore() {
        String xmlPath = testXmlDir + "testXml.xml";

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

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


            Assert.assertEquals(txtModel.getFacts(), xmlModel.getFacts());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void testStore2() {
        String args[] = {"-tx", "sometext"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);

        Assert.assertEquals(UsageMessage.message, buff.toString());
    }

    @Test
    public void testStore3() {
        String args[] = {"-tx"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);

        Assert.assertEquals(UsageMessage.message, buff.toString());
    }

    @Ignore
    @Test
    public void jaxBStoreTest() {
        /*List<XmlExpression> expressions = new ArrayList<>();

        List<XmlExpression> andExp1 = new ArrayList<>();
        andExp1.add(new XmlFactExpression("and1fact1"));
        andExp1.add(new XmlFactExpression("and1fact2"));

        List<XmlExpression> andExp2 = new ArrayList<>();
        andExp2.add(new XmlFactExpression("and1fact1"));
        andExp2.add(new XmlAndExpression(andExp1));
        XmlAndExpression and = new XmlAndExpression(andExp2);

        List<XmlExpression> orExpressions = new ArrayList<>();
        orExpressions.add(new XmlFactExpression("fact1"));
        orExpressions.add(new XmlFactExpression("fact2"));
        orExpressions.add(and);

        expressions.add(new XmlOrExpression(orExpressions));

        XmlRule rule = new XmlRule(expressions, "afct");*/


        try {
            TxtParser parser = new TxtParser();
            Model model = parser.parse(testTxtDir + "positive1.txt");

            File file = new File("src\\test\\resources\\xml\\textXml.xml");

            JAXBContext context = JAXBContext.newInstance(Model.class, Rule.class, OrExpression.class, AndExpression.class, FactExpression.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(model, file);
            marshaller.marshal(model, System.out);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Ignore
    @Test
    public void jaxBReadTest() {
        try {
            TxtParser parser = new TxtParser();
            Model model = parser.parse(testTxtDir + "positive1.txt");

            File file = new File("src\\test\\resources\\xml\\textXml.xml");

            JAXBContext context = JAXBContext.newInstance(Model.class, Rule.class, OrExpression.class, AndExpression.class, FactExpression.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Model xmlModel = (Model) unmarshaller.unmarshal(file);

            Iterator<String> it = xmlModel.getFacts().iterator();

            System.out.print(it.next()); //facts не может быть пустым, проверка не нужна
            while (it.hasNext()) {
                System.out.print(", " + it.next());
            }
            System.out.println();

            xmlModel.processRules();

            it = xmlModel.getFacts().iterator();

            System.out.print(it.next()); //facts не может быть пустым, проверка не нужна
            while (it.hasNext()) {
                System.out.print(", " + it.next());
            }
            System.out.println();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
