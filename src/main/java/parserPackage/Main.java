package parserPackage;

import parserPackage.factTools.Facts;
import parserPackage.parser.Parser;
import parserPackage.parser.TxtParser;

import java.util.Iterator;
import java.util.regex.Pattern;

public class Main {
    private static final String usage = " Usage: app [<filename to process>.<type of file>]\n" +
            "Types of file:\n" +
            "\ttxt \tфайл должен содержать перечисление правил, разделитель и список известных фактов.\n" +
            "\tproperties \tфайл должен содержать ссылку на бд, логин, пороль.";

    public static void main(String args[]) {
        /*if (args.length != 1 || args[0].equals("")) {
            System.out.println("No filename to process." + usage);
            return;
        }*/


        Facts facts;
        Parser parser = null;
        if (Pattern.compile(".+\\.txt$").matcher(args[0]).matches()) {
            parser = new TxtParser();
        } else if (Pattern.compile(".+\\.properties$").matcher(args[0]).matches()) {
            //parser = new DbParser();
        } else {
            System.out.println("Incorrect type of file." + usage);
            return;
        }

        try {
            facts = parser.parse(args[0]);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return;
        }

        Iterator<String> it = facts.getFacts().iterator();

        System.out.print(it.next()); //facts не может быть пустым, проверка не нужна
        while (it.hasNext()) {
            System.out.print(", " + it.next());
        }
        System.out.println();

        DbInserter inserter = new DbInserter();
        try {
            inserter.insert(facts, args[1]);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return;
        }
    }
}
