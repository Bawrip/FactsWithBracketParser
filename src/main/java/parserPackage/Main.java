package parserPackage;

import org.apache.commons.cli.*;
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
            } else if (commandLine.hasOption("i")) {
                String[] values = commandLine.getOptionValues("i");
                engine = new Engine(userInteraction, Engine.Mode.databaseInsert, values[0], values[1]);
            }
        } catch (ParseException ex) {
            formatter.printHelp("App", options, true);
            return;
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
                .desc("Argument is a path of text file, which contains rules and facts. Extracts the facts from the rules and deduce them.")
                .build());

        optionGroup.addOption(Option.builder("d")
                .longOpt("database")
                .argName("propertyPath")
                .hasArg()
                .desc("Argument is a path of property file, which contains url, login, password for connecting to the database." +
                "Reads the database. Extracts the facts from the rules and deduce them.")
                .build());

        optionGroup.addOption(Option.builder("i")
                .longOpt("insert")
                .hasArgs()
                .argName("textPath propertyPath")
                .numberOfArgs(2)
                .desc("First argument is a path of text file, second is a path of property file. " +
                        "Reads text into a database. Reads the database")
                .build());

        options.addOptionGroup(optionGroup);

        return options;
    }
}
