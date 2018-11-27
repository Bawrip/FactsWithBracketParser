package parserPackage;

import java.util.Iterator;
import java.util.Set;

public class ConsoleUserInteraction implements UserInteraction{
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showFacts(Set<String> facts) {
        Iterator<String> it = facts.iterator();

        System.out.print(it.next()); //facts не может быть пустым, проверка не нужна
        while (it.hasNext()) {
            System.out.print(", " + it.next());
        }
        System.out.println();
    }

    @Override
    public void reportError(String exceptionMessage) {
        System.err.println(exceptionMessage);
    }
}