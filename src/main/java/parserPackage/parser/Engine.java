package parserPackage.parser;

import parserPackage.DbFiller;
import parserPackage.Filler;
import parserPackage.UserInteraction;
import parserPackage.XmlFiller;
import parserPackage.exceptions.EngineException;
import parserPackage.factTools.Model;

import java.io.FileNotFoundException;

public class Engine {
    private String txtPath;
    private String databasePath;
    private String xmlPath;
    private UserInteraction userInteraction;
    private Mode mode;

    public enum Mode {
        textProcessing,
        databaseProcessing,
        databaseStore,
        xmlProcessing,
        xmlStore
    }

    public Engine(UserInteraction userInteraction, Mode mode, String path) throws EngineException {
        switch (mode) {
            case textProcessing:
                this.txtPath = path;
                break;
            case databaseProcessing:
                this.databasePath = path;
                break;
            case xmlProcessing:
                this.xmlPath = path;
                break;
            default:
                throw new EngineException("The mode has an incorrect type for this version of constructor.");
        }
        this.userInteraction = userInteraction;
        this.mode = mode;
    }

    public Engine(UserInteraction userInteraction, Mode mode, String txtPath, String targetPath) throws EngineException {
        this.userInteraction = userInteraction;
        this.txtPath = txtPath;

        switch (mode) {
            case databaseStore:
                this.databasePath = targetPath;
                break;
            case xmlStore:
                this.xmlPath = targetPath;
                break;
            default:
                throw new EngineException("The mode has an incorrect type for this version of constructor.");
        }

        this.mode = mode;
    }

    public void execute() {
        switch (mode) {
            case textProcessing:
                deduce(new TxtParser(), txtPath);
                break;
            case databaseProcessing:
                deduce(new DbParser(), databasePath);
                break;
            case xmlProcessing:
                deduce(new XmlParser(), xmlPath);
                break;
            case databaseStore:
                store(new DbFiller(), databasePath);
                break;
            case xmlStore:
                store(new XmlFiller(), xmlPath);
                break;
        }
    }

    private void deduce(Parser parser, String path) {
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

    private void store(Filler filler, String path) {
        Parser parser = new TxtParser();
        try {

            Model model = parser.parse(txtPath);
            filler.store(model, path);
        } catch (FileNotFoundException ex) {
            String[] message = ex.getMessage().split(" ");
            String filename = message[0];
            userInteraction.reportError("File " + filename + " could not be found.");
        } catch (Exception ex) {
            userInteraction.reportError(ex.getMessage());
        }

        userInteraction.showMessage("Facts and Rules stored successfully.");
    }
}
