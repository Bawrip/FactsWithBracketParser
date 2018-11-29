package parserPackage.parser;

import parserPackage.DbFiller;
import parserPackage.UserInteraction;
import parserPackage.exceptions.EngineException;
import parserPackage.factTools.Model;

import java.io.FileNotFoundException;

public class Engine {
    private String txtPath;
    private String databasePath;
    private UserInteraction userInteraction;
    private Mode mode;

    public enum Mode {
        textProcessing,
        databaseProcessing,
        databaseStore
    }

    public Engine(UserInteraction userInteraction, Mode mode, String txtPath) throws EngineException {
        switch (mode) {
            case textProcessing:
                this.txtPath = txtPath;
                this.databasePath = null;
                break;
            case databaseProcessing:
                this.databasePath = txtPath;
                this.txtPath = null;
                break;
            default:
                throw new EngineException("The mode has an incorrect type for this version of constructor.");
        }
        this.userInteraction = userInteraction;
        this.mode = mode;
    }

    public Engine(UserInteraction userInteraction, Mode mode, String txtPath, String dbPropertyPath) throws EngineException {
        this.userInteraction = userInteraction;
        this.txtPath = txtPath;
        this.databasePath = dbPropertyPath;

        if (mode != Mode.databaseStore) {
            throw new EngineException("The mode has an incorrect type for this version of constructor.");
        }
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
            case databaseStore:
                parser = new TxtParser();
                try {
                    model = parser.parse(txtPath);

                    DbFiller dbFiller = new DbFiller();
                    dbFiller.store(model, databasePath);
                } catch (FileNotFoundException ex) {
                    String[] message = ex.getMessage().split(" ");
                    String filename = message[0];
                    userInteraction.reportError("File " + filename + " could not be found.");
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
