package parserPackage.factTools;

import java.util.*;

//класс объединяющий правила и факты
public class Facts {

    private LinkedList<Rule> rules;//коллекция правил
    private Set<String> facts;//коллекция фактов

    public Facts(LinkedList<Rule> rules, String[] facts) {
        this.rules = rules;
        this.facts = new TreeSet<>(Arrays.asList(facts));
    }

    public LinkedList<Rule> getRules() {
        return rules;
    }


    public Set<String> getFacts() {
        return facts;
    }

    //проверка выполнения условий правил
    public void processRules() {
        Rule rule;
        boolean rulesChanged;
        do {
            rulesChanged = false;
            Iterator<Rule> iterator = rules.iterator();
            while (iterator.hasNext()) {
                rule = iterator.next();
                if (rule.evaluateRule(facts)) {
                    facts.add(rule.getFact());
                    rulesChanged = true;
                    iterator.remove();
                }
            }
            //если после цикла размер коллекции правил изменился -> цикл заново проверяет оставшиеся правила
        } while (rulesChanged);
    }
}