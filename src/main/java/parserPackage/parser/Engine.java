package parserPackage.parser;

import parserPackage.DbInserter;
import parserPackage.UserInteraction;
import parserPackage.factTools.Model;

public class Engine {
    private String txtPath;
    private String databasePath;
    private UserInteraction userInteraction;
    private Mode mode;

    public enum Mode {
        textProcessing,
        databaseProcessing,
        databaseInsert
    }

    public Engine(UserInteraction userInteraction, Mode mode, String txtPath) {
        if (mode == Mode.textProcessing) {
            this.txtPath = txtPath;
            this.databasePath = null;
        }
        if (mode == Mode.databaseProcessing) {
            this.databasePath = txtPath;
            this.txtPath = null;
        }
        this.userInteraction = userInteraction;
        this.mode = mode;
    }

    public Engine(UserInteraction userInteraction, Mode mode, String txtPath, String dbPropertyPath) {
        this.userInteraction = userInteraction;
        this.txtPath = txtPath;
        this.databasePath = dbPropertyPath;
        this.mode = mode;
    }

    public void execute() {
        Parser parser;
        Model model;

        switch (mode) {
            case textProcessing:
                Deduce(new TxtParser(), txtPath);
                break;
            case databaseProcessing:
                Deduce(new DbParser(), databasePath);
                break;
            case databaseInsert:
                parser = new TxtParser();
                try {
                    model = parser.parse(txtPath);

                    DbInserter dbInserter = new DbInserter();
                    dbInserter.insert(model, databasePath);
                } catch (Exception ex) {
                    userInteraction.reportError(ex.getMessage());
                    break;
                }

                userInteraction.showMessage("Facts and Rules inserted into database successfully.");
                break;
        }
    }

    private void Deduce(Parser parser, String path) {
        Model model;
        try {
            model = parser.parse(path);
        } catch (Exception ex) {
            userInteraction.reportError(ex.getMessage());
            return;
        }
        model.processRules();

        userInteraction.showFacts(model.getFacts());
    }
}
