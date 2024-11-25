package org.sepp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public class App {

  public static void main(String[] args) {
    // this is the config we are goin to work with
    Config config = null;
    // get our Cli singleton
    Cli cli = Cli.getInstance();
    // flag to check if we need to save our config
    Boolean saveConfig = false;

    try {
      CommandLine line = cli.parse(args);
      // basic usage guide
      if (line.hasOption("help")) {
        cli.printHelp();
        return;
      }

      // attempt to get our config
      if (line.hasOption("load")) {
        File configFile = new File(line.getOptionValue("load"));
        try {
          config = Config.loadFromFile(configFile);
        } catch (Exception e) {
          System.err.println("Could not load config.\nError: " + e.getMessage());
          System.exit(1);
        }
      } else if (line.hasOption("create")) {
        config = new Config();
        saveConfig = true;
      } else {
        // we need a configuration, otherwise nothing can be done
        System.err.println("No config provided, see --help");
        System.exit(1);
      }

      // adding tasks
      if (line.hasOption("add-task")) {
        // FIXME: Only picks up first argument
        String[] taskstr = line.getOptionValues("add-task");

        // task needs 3 arguments
        if (taskstr.length < 3) {
          System.err.println("Invalid task, see --help");
          System.out.println(
              "argument length: " + taskstr.length + "\nArguments: " + Arrays.toString(taskstr));
          System.exit(1);
        }

        // Verify that file exists and is a file, this is scoped to ensure that shfile isn't
        // accessed anywhere else
        {
          File shfile = new File(taskstr[2]);
          if (!shfile.isFile()) {
            System.err.println("Invalid shell file");
            System.exit(1);
          }
        }

        // try to read contents of file at path
        List<String> sh;
        try {
          Path path = Paths.get(taskstr[2]);
          sh = Files.readAllLines(path);
        } catch (Exception e) {
          System.err.println("Failed to read " + taskstr[1] + " \nError: " + e.getMessage());
          System.exit(1);
          return; // this line is here because java compiler assumes sh may be unitiliazed
        }

        // determine type, will default to custom
        Task.TaskType type = Task.parseType(taskstr[1]);

        config.addTask(new Task(taskstr[0], type, sh));

        // ensure config is updated
        saveConfig = true;
      }

      // allow changing titles
      if (line.hasOption("title")) {
        String title = line.getOptionValue("title");
        config.name = title;
        saveConfig = true;
      }

    } catch (ParseException e) {
      System.err.println("Parsing failed.  Reason: " + e.getMessage());
      System.exit(1);
    }

    if (saveConfig) {
      try {
        config.storeConfig("path");
      } catch (IOException e) {
        System.err.println("Failed to save config.\nError: " + e.getMessage());
      }
    }
  }
}