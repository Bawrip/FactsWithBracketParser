public class UsageMessage {

    public static final String message = "usage: App [-d <propertyPath> | -t <textPath> | -td <textPath propertyPath> | -tx <textPath xmlPath> | -x <xmlPath>]\r\n"+
            " -d,--database <propertyPath>   Argument is a path of property file, which contains url, login, password for connecting\r\n"+
            "                                to the database.Reads the database. Reveals new facts from the rules and deduce them.\r\n"+
            " -t,--text <textPath>           Argument is a path of text file that contains rules and facts. Reveals new facts from\r\n"+
            "                                the rules and deduce them.\r\n"+
            " -td <textPath propertyPath>    First argument is a path of text file, second is a path of property file. Store text\r\n"+
            "                                into a database.\r\n"+
            " -tx <textPath xmlPath>         First argument is a path of text file, second is a path of xml file. Store text into a\r\n"+
            "                                xml file.\r\n"+
            " -x,--xml <xmlPath>             Argument is a path of xml file, which contains rules and facts. Reveals new facts from\r\n"+
            "                                the rules and deduce them.\r\n";
}
