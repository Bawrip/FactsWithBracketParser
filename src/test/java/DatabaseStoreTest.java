
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.*;
import parserPackage.Main;
import parserPackage.dbTools.mapper.DatabaseTruncateMapper;
import parserPackage.factTools.Model;
import parserPackage.parser.DbParser;
import parserPackage.parser.TxtParser;


import java.io.*;
import java.util.*;

public class DatabaseStoreTest {
    private String testTxtDir = "target\\test-classes\\txt\\";
    private String testPropDir = "target\\test-classes\\properties\\";

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
        String dbPath = testPropDir + "dbInsertTest.properties";

        String iArgs[] = {"-td", txtPath, dbPath};
        Main.main(iArgs);

        Assertions.assertEquals("Facts and Rules stored successfully.\r\n", buff.toString());
        TxtParser txtParser = new TxtParser();
        DbParser dbParser = new DbParser();

        Model txtModel;
        Model dbModel;

        try {
            txtModel = txtParser.parse(txtPath);
            txtModel.processRules();

            dbModel = dbParser.parse(dbPath);
            dbModel.processRules();


            Assertions.assertEquals(txtModel.getFacts(), dbModel.getFacts());
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

        Main.main(new String[] {"-td", testTxtDir + "positive1.txt", dbPath});
        Main.main(new String[] {"-td", testTxtDir + "positive2.txt", dbPath});
        Main.main(new String[] {"-td", testTxtDir + "positive3.txt", dbPath});

        TxtParser txtParser = new TxtParser();
        DbParser dbParser = new DbParser();

        Model txtModel;
        Model dbModel;

        try {
            txtModel = txtParser.parse(testTxtDir + "positive3.txt");
            txtModel.processRules();

            dbModel = dbParser.parse(dbPath);
            dbModel.processRules();


            Assertions.assertEquals(txtModel.getFacts(), dbModel.getFacts());
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
        String args[] = {"-td", "sometext"};

        Main.main(args);

        Assertions.assertEquals(UsageMessage.message, buff.toString());
    }

    @Test
    public void testInsertDb3() {
        String args[] = {"-td"};

        Main.main(args);

        Assertions.assertEquals(UsageMessage.message, buff.toString());
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
