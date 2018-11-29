
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import parserPackage.Main;
import parserPackage.dbTools.DbExpression;
import parserPackage.dbTools.mapper.DatabaseTruncateMapper;
import parserPackage.factTools.Model;
import parserPackage.parser.DbParser;
import parserPackage.parser.Engine;
import parserPackage.parser.TxtParser;


import java.io.*;
import java.sql.Connection;
import java.util.*;

public class DatabaseInsertTest {
    private String testTxtDir = "target\\test-classes\\Txt\\";
    private String testPropDir = "target\\test-classes\\properties\\";

    private String usageMessage = "usage: App [-d <propertyPath> | -i <textPath propertyPath> | -t <textPath>]\r\n" +
            " -d,--database <propertyPath>          Argument is a path of property file, which contains url, login, password for\r\n" +
            "                                       connecting to the database.Reads the database. Extracts the facts from the rules\r\n" +
            "                                       and deduce them.\r\n" +
            " -i,--insert <textPath propertyPath>   First argument is a path of text file, second is a path of property file. Reads\r\n" +
            "                                       text into a database. Reads the database\r\n" +
            " -t,--text <textPath>                  Argument is a path of text file, which contains rules and facts. Extracts the\r\n" +
            "                                       facts from the rules and deduce them.\r\n";

    @Test
    public void testOneStore() {
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        String txtPath = testTxtDir + "positive1.txt";
        String dbPath = testPropDir + "dbInsertTest.properties";

        String iArgs[] = {"-i", txtPath, dbPath};
        Main.main(iArgs);

        Assert.assertEquals("Facts and Rules inserted into database successfully.\r\n", buff.toString());
        TxtParser txtParser = new TxtParser();
        DbParser dbParser = new DbParser();

        Model txtModel;
        Model dbModel;

        try {
            txtModel = txtParser.parse(txtPath);
            txtModel.processRules();

            dbModel = dbParser.parse(dbPath);
            dbModel.processRules();


            Assert.assertEquals(txtModel.getFacts(), dbModel.getFacts());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        try {
            truncateDatabase(testPropDir + "dbInsertTest.properties");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void testMultiStore() {
        String dbPath = testPropDir + "dbInsertTest.properties";

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(new String[] {"-i", testTxtDir + "positive1.txt", dbPath});
        Main.main(new String[] {"-i", testTxtDir + "positive2.txt", dbPath});
        Main.main(new String[] {"-i", testTxtDir + "positive3.txt", dbPath});

        TxtParser txtParser = new TxtParser();
        DbParser dbParser = new DbParser();

        Model txtModel;
        Model dbModel;

        try {
            txtModel = txtParser.parse(testTxtDir + "positive3.txt");
            txtModel.processRules();

            dbModel = dbParser.parse(dbPath);
            dbModel.processRules();


            Assert.assertEquals(txtModel.getFacts(), dbModel.getFacts());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        try {
            truncateDatabase(testPropDir + "dbInsertTest.properties");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void testInsertDb2() {
        String args[] = {"-i", "sometext"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);

        Assert.assertEquals(usageMessage, buff.toString());
    }

    @Test
    public void testInsertDb3() {
        String args[] = {"-i"};

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buff));

        Main.main(args);

        Assert.assertEquals(usageMessage, buff.toString());
    }

    private void truncateDatabase(String config) throws IOException {
        Properties dbConfig = new Properties();

        dbConfig.load(new FileInputStream(config));

        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, dbConfig);
        SqlSession session = sqlSessionFactory.openSession();

        try {
            DatabaseTruncateMapper truncateMapper = session.getMapper(DatabaseTruncateMapper.class);
            truncateMapper.truncateTables();

            session.commit();
        } finally {
            reader.close();
            session.close();
        }
    }
}
