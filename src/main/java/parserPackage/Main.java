package parserPackage;

import org.apache.commons.cli.*;
import parserPackage.exceptions.EngineException;
import parserPackage.parser.Engine;


public class Main {
    public static void main(String args[]) {
        Engine engine = null;
        UserInteraction userInteraction = new ConsoleUserInteraction();

        Options options = createOptions();
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(120);

        CommandLineParser commandLineParser = new DefaultParser();
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
            if (commandLine.getOptions().length < 1) {
                formatter.printHelp("App", options, true);
                return;
            }
            if (commandLine.hasOption("t")) {
                engine = new Engine(userInteraction, Engine.Mode.textProcessing, commandLine.getOptionValue("t"));
            } else if (commandLine.hasOption("d")) {
                engine = new Engine(userInteraction, Engine.Mode.databaseProcessing, commandLine.getOptionValue("d"));
            } else if (commandLine.hasOption("td")) {
                String[] values = commandLine.getOptionValues("td");
                engine = new Engine(userInteraction, Engine.Mode.databaseStore, values[0], values[1]);
            } else if (commandLine.hasOption("x")) {
                engine = new Engine(userInteraction, Engine.Mode.xmlProcessing, commandLine.getOptionValue("x"));
            } else if (commandLine.hasOption("tx")) {
                String[] values = commandLine.getOptionValues("tx");
                engine = new Engine(userInteraction, Engine.Mode.xmlStore, values[0], values[1]);
            }
        } catch (ParseException ex) {
            formatter.printHelp("App", options, true);
            return;
        } catch (EngineException ex) {
            System.err.println(ex.getMessage());
        }


        engine.execute();
    }

    private static Options createOptions() {
        Options options = new Options();

        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(Option.builder("t")
                .longOpt("text")
                .argName("textPath")
                .hasArg()
                .desc("Argument is a path of text file that contains rules and facts. Reveals new facts from the rules and deduce them.")
                .build());

        optionGroup.addOption(Option.builder("d")
                .longOpt("database")
                .argName("propertyPath")
                .hasArg()
                .desc("Argument is a path of property file, which contains url, login, password for connecting to the database." +
                "Reads the database. Reveals new facts from the rules and deduce them.")
                .build());

        optionGroup.addOption(Option.builder("td")
                .hasArgs()
                .argName("textPath propertyPath")
                .numberOfArgs(2)
                .desc("First argument is a path of text file, second is a path of property file. " +
                        "Store text into a database.")
                .build());

        optionGroup.addOption(Option.builder("x")
                .longOpt("xml")
                .argName("xmlPath")
                .hasArg()
                .desc("Argument is a path of xml file, which contains rules and facts. Reveals new facts from the rules and deduce them.")
                .build());

        optionGroup.addOption(Option.builder("tx")
                .hasArgs()
                .argName("textPath xmlPath")
                .numberOfArgs(2)
                .desc("First argument is a path of text file, second is a path of xml file. " +
                        "Store text into a xml file.")
                .build());

        options.addOptionGroup(optionGroup);

        return options;
    }
}
