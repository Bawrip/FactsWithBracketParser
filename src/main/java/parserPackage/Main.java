package parserPackage;

import parserPackage.parser.Facts;
import parserPackage.parser.FactsParser;
import java.util.Iterator;

public class Main {
    public static void main(String args[]) {
        if (args.length != 1 || args[0].equals("")) {
            System.out.println("No filename to process. Usage: app <filename to process>.");
            return;
        }

        Facts facts;
        FactsParser factsParser = new FactsParser();

        try {
            facts = factsParser.parse(args[0]);
            facts.processRules();
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
    }
}
